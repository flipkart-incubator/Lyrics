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

import java.util.ArrayList;
import java.util.List;

public final class CodeBlock {
    final public List<String> formats = new ArrayList<>();
    final public List<Object> arguments = new ArrayList<>();

    private CodeBlock(Builder builder) {
        this.formats.addAll(builder.formats);
        for (Object arg : builder.arguments) {
            this.arguments.add(arg);
        }
    }

    public static CodeBlock of(String format, Object... args) {
        return new Builder().add(format, args).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        return toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public static final class Builder {
        public List<String> formats = new ArrayList<>();
        public List<Object> arguments = new ArrayList<>();

        private Builder() {
        }

        public Builder add(String format, Object... args) {
            this.formats.add(format);
            for (Object object : args) {
                this.arguments.add(object);
            }
            return this;
        }

        /**
         * @param controlFlow the control flow construct and its code, such as "if (foo == 5)".
         *                    Shouldn't contain braces or newline characters.
         */
        public Builder beginControlFlow(String controlFlow, Object... args) {
            add(controlFlow + " {\n", args);
            indent();
            return this;
        }

        /**
         * @param controlFlow the control flow construct and its code, such as "else if (foo == 10)".
         *                    Shouldn't contain braces or newline characters.
         */
        public Builder nextControlFlow(String controlFlow, Object... args) {
            unindent();
            add("} " + controlFlow + " {\n", args);
            indent();
            return this;
        }

        public Builder endControlFlow() {
            unindent();
            add("}\n");
            return this;
        }

        /**
         * @param controlFlow the optional control flow construct and its code, such as
         *                    "while(foo == 20)". Only used for "do/while" control flows.
         */
        public Builder endControlFlow(String controlFlow, Object... args) {
            unindent();
            add("} " + controlFlow + ";\n", args);
            return this;
        }

        public Builder addStatement(String format, Object... args) {
            add("$[");
            add(format, args);
            add(";\n$]");
            return this;
        }

        public Builder addStatement(CodeBlock codeBlock) {
            return addStatement("$L", codeBlock);
        }

        public Builder add(CodeBlock codeBlock) {
            formats.addAll(codeBlock.formats);
            for (Object arg : codeBlock.arguments) {
                arguments.add(arg);
            }
            return this;
        }

        public Builder indent() {
            this.formats.add("$>");
            return this;
        }

        public Builder unindent() {
            this.formats.add("$<");
            return this;
        }

        public CodeBlock build() {
            return new CodeBlock(this);
        }
    }
}