package com.flipkart.lyrics.specs;

import java.util.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class AnnotationSpec {
    public final TypeName type;
    public final Map<String, List<CodeBlock>> members = new HashMap<>();

    private AnnotationSpec(Builder builder) {
        this.type = builder.type;
        this.members.putAll(builder.members);
    }

    public static Builder builder(Class<?> clazz) {
        return new AnnotationSpec.Builder(ClassName.get(clazz));
    }

    public static Builder builder(ClassName className) {
        return new AnnotationSpec.Builder(className);
    }

    public Builder toBuilder() {
        Builder builder = new Builder(type);
        for (Map.Entry<String, List<CodeBlock>> entry : members.entrySet()) {
            builder.members.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return builder;
    }

    public static final class Builder {
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
