/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.helper.Util;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CodeBlock {
    private static final Pattern NAMED_ARGUMENT = Pattern.compile("\\$(?<argumentName>[\\w_]+):(?<typeChar>[\\w]).*", 32);
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]+[\\w_]*");
    final public String format;
    final public Object[] arguments;
    final List<String> formatParts;
    final List<Object> args;

    private CodeBlock(CodeBlock.Builder builder) {
        this.formatParts = Util.immutableList(builder.formatParts);
        this.args = Util.immutableList(builder.args);
        this.format = builder.format;
        this.arguments = builder.arguments;
    }

    public static CodeBlock of(String format, Object... args) {
        return (new CodeBlock.Builder()).add(format, args).build();
    }

    public static CodeBlock.Builder builder() {
        return new CodeBlock.Builder();
    }

    public boolean isEmpty() {
        return this.formatParts.isEmpty();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else {
            return this.getClass() != o.getClass() ? false : this.toString().equals(o.toString());
        }
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public String toString() {
        StringWriter out = new StringWriter();
        try {
            new CodeWriter(out).emit(this);
            return out.toString();
        } catch (IOException e) {
            throw new AssertionError();
        }
    }

    public CodeBlock.Builder toBuilder() {
        CodeBlock.Builder builder = new CodeBlock.Builder();
        builder.formatParts.addAll(this.formatParts);
        builder.args.addAll(this.args);
        return builder;
    }

    public static final class Builder {
        final List<String> formatParts;
        final List<Object> args;
        public String format;
        public Object[] arguments;

        private Builder() {
            this.formatParts = new ArrayList();
            this.args = new ArrayList();
        }

        public CodeBlock.Builder addNamed(String format, Map<String, ?> arguments) {
            int p = 0;
            Iterator var4 = arguments.keySet().iterator();

            while (var4.hasNext()) {
                String argument = (String) var4.next();
                Util.checkArgument(CodeBlock.LOWERCASE.matcher(argument).matches(), "argument '%s' must start with a lowercase character", new Object[]{argument});
            }

            while (p < format.length()) {
                int nextP = format.indexOf("$", p);
                if (nextP == -1) {
                    this.formatParts.add(format.substring(p, format.length()));
                    break;
                }

                if (p != nextP) {
                    this.formatParts.add(format.substring(p, nextP));
                    p = nextP;
                }

                Matcher matcher = CodeBlock.NAMED_ARGUMENT.matcher(format.subSequence(p, format.length()));
                if (matcher.matches()) {
                    String argumentName = matcher.group("argumentName");
                    Util.checkArgument(arguments.containsKey(argumentName), "Missing named argument for $%s", new Object[]{argumentName});
                    char formatChar = matcher.group("typeChar").charAt(0);
                    this.addArgument(format, formatChar, arguments.get(argumentName));
                    this.formatParts.add("$" + formatChar);
                    p += matcher.regionStart() + argumentName.length() + 3;
                } else {
                    Util.checkArgument(p < format.length() - 1, "dangling $ at end", new Object[0]);
                    Util.checkArgument(this.isNoArgPlaceholder(format.charAt(p + 1)), "unknown format $%s at %s in '%s'", new Object[]{format.charAt(p + 1), p + 1, format});
                    this.formatParts.add(format.substring(p, p + 2));
                    p += 2;
                }
            }

            return this;
        }

        public CodeBlock.Builder add(String format, Object... args) {
            this.format = format;
            this.arguments = args;
            boolean hasRelative = false;
            boolean hasIndexed = false;
            int relativeParameterCount = 0;
            int[] indexedParameterCount = new int[args.length];
            int p = 0;

            while (true) {
                int indexStart;
                while (p < format.length()) {
                    if (format.charAt(p) != '$') {
                        indexStart = format.indexOf(36, p + 1);
                        if (indexStart == -1) {
                            indexStart = format.length();
                        }

                        this.formatParts.add(format.substring(p, indexStart));
                        p = indexStart;
                    } else {
                        ++p;
                        indexStart = p;

                        char c;
                        do {
                            Util.checkArgument(p < format.length(), "dangling format characters in '%s'", new Object[]{format});
                            c = format.charAt(p++);
                        } while (c >= '0' && c <= '9');

                        int indexEnd = p - 1;
                        if (this.isNoArgPlaceholder(c)) {
                            Util.checkArgument(indexStart == indexEnd, "$$, $>, $<, $[, $], and $W may not have an index", new Object[0]);
                            this.formatParts.add("$" + c);
                        } else {
                            int index;
                            if (indexStart < indexEnd) {
                                index = Integer.parseInt(format.substring(indexStart, indexEnd)) - 1;
                                hasIndexed = true;
                                ++indexedParameterCount[index % args.length];
                            } else {
                                index = relativeParameterCount;
                                hasRelative = true;
                                ++relativeParameterCount;
                            }

                            Util.checkArgument(index >= 0 && index < args.length, "index %d for '%s' not in range (received %s arguments)", new Object[]{index + 1, format.substring(indexStart - 1, indexEnd + 1), args.length});
                            Util.checkArgument(!hasIndexed || !hasRelative, "cannot mix indexed and positional parameters", new Object[0]);
                            this.addArgument(format, c, args[index]);
                            this.formatParts.add("$" + c);
                        }
                    }
                }

                if (hasRelative) {
                    Util.checkArgument(relativeParameterCount >= args.length, "unused arguments: expected %s, received %s", new Object[]{relativeParameterCount, args.length});
                }

                if (hasIndexed) {
                    List<String> unused = new ArrayList();

                    for (indexStart = 0; indexStart < args.length; ++indexStart) {
                        if (indexedParameterCount[indexStart] == 0) {
                            unused.add("$" + (indexStart + 1));
                        }
                    }

                    String s = unused.size() == 1 ? "" : "s";
                    Util.checkArgument(unused.isEmpty(), "unused argument%s: %s", new Object[]{s, Util.join(", ", unused)});
                }

                return this;
            }
        }

        private boolean isNoArgPlaceholder(char c) {
            return c == '$' || c == '>' || c == '<' || c == '[' || c == ']' || c == 'W';
        }

        private void addArgument(String format, char c, Object arg) {
            switch (c) {
                case 'L':
                    this.args.add(this.argToLiteral(arg));
                    break;
                case 'M':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                default:
                    throw new IllegalArgumentException(String.format("invalid format string: '%s'", format));
                case 'N':
                    this.args.add(this.argToName(arg));
                    break;
                case 'S':
                    this.args.add(this.argToString(arg));
                    break;
                case 'T':
                    this.args.add(this.argToType(arg));
            }

        }

        private String argToName(Object o) {
            if (o instanceof CharSequence) {
                return o.toString();
            } else if (o instanceof ParameterSpec) {
                return ((ParameterSpec) o).name;
            } else if (o instanceof FieldSpec) {
                return ((FieldSpec) o).name;
            } else if (o instanceof MethodSpec) {
                return ((MethodSpec) o).name;
            } else if (o instanceof TypeSpec) {
                return ((TypeSpec) o).name;
            } else {
                throw new IllegalArgumentException("expected name but was " + o);
            }
        }

        private Object argToLiteral(Object o) {
            return o;
        }

        private String argToString(Object o) {
            return o != null ? String.valueOf(o) : null;
        }

        private TypeName argToType(Object o) {
            if (o instanceof TypeName) {
                return (TypeName) o;
            } else if (o instanceof TypeMirror) {
                return TypeName.get((TypeMirror) o);
            } else if (o instanceof Element) {
                return TypeName.get(((Element) o).asType());
            } else if (o instanceof Type) {
                return TypeName.get((Type) o);
            } else {
                throw new IllegalArgumentException("expected type but was " + o);
            }
        }

        public CodeBlock.Builder beginControlFlow(String controlFlow, Object... args) {
            this.add(controlFlow + " {\n", args);
            this.indent();
            return this;
        }

        public CodeBlock.Builder nextControlFlow(String controlFlow, Object... args) {
            this.unindent();
            this.add("} " + controlFlow + " {\n", args);
            this.indent();
            return this;
        }

        public CodeBlock.Builder endControlFlow() {
            this.unindent();
            this.add("}\n");
            return this;
        }

        public CodeBlock.Builder endControlFlow(String controlFlow, Object... args) {
            this.unindent();
            this.add("} " + controlFlow + ";\n", args);
            return this;
        }

        public CodeBlock.Builder addStatement(String format, Object... args) {
            this.add("$[");
            this.add(format, args);
            this.add(";\n$]");
            return this;
        }

        public CodeBlock.Builder add(CodeBlock codeBlock) {
            this.formatParts.addAll(codeBlock.formatParts);
            this.args.addAll(codeBlock.args);
            return this;
        }

        public CodeBlock.Builder indent() {
            this.formatParts.add("$>");
            return this;
        }

        public CodeBlock.Builder unindent() {
            this.formatParts.add("$<");
            return this;
        }

        public CodeBlock build() {
            return new CodeBlock(this);
        }
    }
}
