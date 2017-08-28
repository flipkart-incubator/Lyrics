package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.Song;

import java.io.IOException;
import java.util.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class AnnotationSpec {
    public final TypeName type;
    public final Map<String, List<CodeBlock>> members = new HashMap<>();

    public AnnotationSpec(Builder builder) {
        this.type = builder.type;
        this.members.putAll(builder.members);
    }

    public static Builder builder(Class<?> clazz) {
        return Song.factory.createAnnotationBuilder(ClassName.get(clazz));
    }

    public static Builder builder(ClassName className) {
        return Song.factory.createAnnotationBuilder(className);
    }

    public Object getAnnotationSpec() {
        return null;
    }

    public Builder toBuilder() {
        Builder builder = new Builder(type);
        for (Map.Entry<String, List<CodeBlock>> entry : members.entrySet()) {
            builder.members.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return builder;
    }

    void emit(CodeWriter codeWriter, boolean inline) throws IOException {
        String whitespace = inline ? "" : "\n";
        String memberSeparator = inline ? ", " : ",\n";
        if (members.isEmpty()) {
            // @Singleton
            codeWriter.emit("@$T", type);
        } else if (members.size() == 1 && members.containsKey("value")) {
            // @Named("foo")
            codeWriter.emit("@$T(", type);
            emitAnnotationValues(codeWriter, whitespace, memberSeparator, members.get("value"));
            codeWriter.emit(")");
        } else {
            // Inline:
            //   @Column(name = "updated_at", nullable = false)
            //
            // Not inline:
            //   @Column(
            //       name = "updated_at",
            //       nullable = false
            //   )
            codeWriter.emit("@$T(" + whitespace, type);
            codeWriter.indent(2);
            for (Iterator<Map.Entry<String, List<CodeBlock>>> i
                 = members.entrySet().iterator(); i.hasNext(); ) {
                Map.Entry<String, List<CodeBlock>> entry = i.next();
                codeWriter.emit("$L = ", entry.getKey());
                emitAnnotationValues(codeWriter, whitespace, memberSeparator, entry.getValue());
                if (i.hasNext()) codeWriter.emit(memberSeparator);
            }
            codeWriter.unindent(2);
            codeWriter.emit(whitespace + ")");
        }
    }

    private void emitAnnotationValues(CodeWriter codeWriter, String whitespace,
                                      String memberSeparator, List<CodeBlock> values) throws IOException {
        if (values.size() == 1) {
            codeWriter.indent(2);
            codeWriter.emit(values.get(0));
            codeWriter.unindent(2);
            return;
        }

        codeWriter.emit("{" + whitespace);
        codeWriter.indent(2);
        boolean first = true;
        for (CodeBlock codeBlock : values) {
            if (!first) codeWriter.emit(memberSeparator);
            codeWriter.emit(codeBlock);
            first = false;
        }
        codeWriter.unindent(2);
        codeWriter.emit(whitespace + "}");
    }

    public static class Builder {
        private TypeName type;
        private final Map<String, List<CodeBlock>> members = new HashMap<>();

        protected Builder(TypeName typeName) {
            this.type = typeName;
        }

        public AnnotationSpec.Builder addMember(String name, String format, Object... args) {
            List<CodeBlock> values = this.members.computeIfAbsent(name, k -> new ArrayList<>());
            values.add(CodeBlock.of(format, args));
            return this;
        }

        public AnnotationSpec build() {
            return new AnnotationSpec(this);
        }
    }
}
