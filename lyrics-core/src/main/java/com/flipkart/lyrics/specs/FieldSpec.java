package com.flipkart.lyrics.specs;

import java.io.IOException;
import java.util.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class FieldSpec {
    public final String name;
    public final TypeName type;
    public final CodeBlock initializer;
    public final Set<Modifier> modifiers = new HashSet<>();
    public final List<AnnotationSpec> annotations = new ArrayList<>();

    public FieldSpec(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.initializer = builder.initializer;
        this.modifiers.addAll(builder.modifiers);
        this.annotations.addAll(builder.annotations);
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return new Builder(typeName, name, modifiers);
    }

    public static Builder builder(Class<?> clazz, String name, Modifier... modifiers) {
        return new Builder(clazz, name, modifiers);
    }

    public Builder toBuilder() {
        Builder builder = new Builder(type, name);
        builder.annotations.addAll(annotations);
        builder.modifiers.addAll(modifiers);
        builder.initializer = initializer.isEmpty() ? null : initializer;
        return builder;
    }

    void emit(CodeWriter codeWriter, Set<Modifier> implicitModifiers) throws IOException {
        codeWriter.emitAnnotations(annotations, false);
        codeWriter.emitModifiers(modifiers, implicitModifiers);
        codeWriter.emit("$T $L", type, name);
        if (!initializer.isEmpty()) {
            codeWriter.emit(" = ");
            codeWriter.emit(initializer);
        }
        codeWriter.emit(";\n");
    }

    public static final class Builder {
        private final String name;
        private final TypeName type;
        private final Set<Modifier> modifiers = new HashSet<>();
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private CodeBlock initializer;

        protected Builder(TypeName type, String name, Modifier... modifiers) {
            this.type = type;
            this.name = name;
            this.modifiers.addAll(Arrays.asList(modifiers));
        }

        protected Builder(Class<?> clazz, String name, Modifier... modifiers) {
            this.type = TypeName.get(clazz);
            this.name = name;
            this.modifiers.addAll(Arrays.asList(modifiers));
        }

        public FieldSpec.Builder initializer(String format, Object... args) {
            this.initializer = CodeBlock.of(format, args);
            return this;
        }

        public FieldSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotations.add(AnnotationSpec.builder(clazz).build());
            return this;
        }

        public FieldSpec.Builder addAnnotation(ClassName className) {
            this.annotations.add(AnnotationSpec.builder(className).build());
            return this;
        }

        public FieldSpec.Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotations.add(annotationSpec);
            return this;
        }

        public FieldSpec.Builder addModifiers(Modifier... modifiers) {
            this.modifiers.addAll(Arrays.asList(modifiers));
            return this;
        }

        public FieldSpec build() {
            return new FieldSpec(this);
        }
    }
}
