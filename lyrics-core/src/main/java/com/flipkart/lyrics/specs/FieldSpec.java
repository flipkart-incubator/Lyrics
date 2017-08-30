package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.helper.Util;

import java.io.IOException;
import java.util.*;

import static com.flipkart.lyrics.helper.Util.checkArgument;
import static com.flipkart.lyrics.helper.Util.checkNotNull;
import static com.flipkart.lyrics.helper.Util.checkState;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class FieldSpec {
    public final TypeName type;
    public final String name;
    public final CodeBlock doc;
    public final List<AnnotationSpec> annotations;
    public final Set<Modifier> modifiers;
    public final CodeBlock initializer;

    private FieldSpec(Builder builder) {
        this.type = checkNotNull(builder.type, "type == null");
        this.name = checkNotNull(builder.name, "name == null");
        this.doc = builder.doc.build();
        this.annotations = Util.immutableList(builder.annotations);
        this.modifiers = Util.immutableSet(builder.modifiers);
        this.initializer = (builder.initializer == null)
                ? CodeBlock.builder().build()
                : builder.initializer;
    }

    public boolean hasModifier(Modifier modifier) {
        return modifiers.contains(modifier);
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return new Builder(typeName, name, modifiers);
    }

    public static Builder builder(Class<?> clazz, String name, Modifier... modifiers) {
        return new Builder(clazz, name, modifiers);
    }

    public Builder toBuilder() {
        Builder builder = new Builder(type, name);
        builder.doc.add(doc);
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
        private final TypeName type;
        private final String name;
        private final CodeBlock.Builder doc = CodeBlock.builder();
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private final Set<Modifier> modifiers = new HashSet<>();
        private CodeBlock initializer = null;

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

        public Builder addDoc(String format, Object... args) {
            doc.add(format, args);
            return this;
        }

        public Builder addDoc(CodeBlock block) {
            doc.add(block);
            return this;
        }

        public Builder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
            checkArgument(annotationSpecs != null, "annotationSpecs == null");
            for (AnnotationSpec annotationSpec : annotationSpecs) {
                this.annotations.add(annotationSpec);
            }
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotations.add(annotationSpec);
            return this;
        }

        public Builder addAnnotation(ClassName annotation) {
            this.annotations.add(AnnotationSpec.builder(annotation).build());
            return this;
        }

        public Builder addAnnotation(Class<?> annotation) {
            return addAnnotation(ClassName.get(annotation));
        }

        public Builder addModifiers(Modifier... modifiers) {
            Collections.addAll(this.modifiers, modifiers);
            return this;
        }

        public Builder initializer(String format, Object... args) {
            return initializer(CodeBlock.of(format, args));
        }

        public Builder initializer(CodeBlock codeBlock) {
            checkState(this.initializer == null, "initializer was already set");
            this.initializer = checkNotNull(codeBlock, "codeBlock == null");
            return this;
        }

        public FieldSpec build() {
            return new FieldSpec(this);
        }
    }
}