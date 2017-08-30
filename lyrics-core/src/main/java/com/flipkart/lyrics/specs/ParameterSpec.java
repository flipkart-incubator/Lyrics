package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.helper.Util;

import java.io.IOException;
import java.util.*;

import static com.flipkart.lyrics.helper.Util.checkArgument;
import static com.flipkart.lyrics.helper.Util.checkNotNull;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class ParameterSpec {
    public final String name;
    public final List<AnnotationSpec> annotations;
    public final Set<Modifier> modifiers;
    public final TypeName type;

    public ParameterSpec(Builder builder) {
        this.name = checkNotNull(builder.name, "name == null");
        this.annotations = Util.immutableList(builder.annotations);
        this.modifiers = Util.immutableSet(builder.modifiers);
        this.type = checkNotNull(builder.type, "type == null");
    }

    public boolean hasModifier(Modifier modifier) {
        return modifiers.contains(modifier);
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return new Builder(typeName, name, modifiers);
    }

    public static Builder builder(Class<?> clazz, String name, Modifier... modifiers) {
        return new Builder(TypeName.get(clazz), name, modifiers);
    }

    public Builder toBuilder() {
        return toBuilder(type, name);
    }

    Builder toBuilder(TypeName type, String name) {
        Builder builder = new Builder(type, name);
        builder.annotations.addAll(annotations);
        builder.modifiers.addAll(modifiers);
        return builder;
    }

    void emit(CodeWriter codeWriter, boolean varargs) throws IOException {
        codeWriter.emitAnnotations(annotations, true);
        codeWriter.emitModifiers(modifiers);
        if (varargs) {
            codeWriter.emit("$T... $L", TypeName.arrayComponent(type), name);
        } else {
            codeWriter.emit("$T $L", type, name);
        }
    }

    public static class Builder {
        private final TypeName type;
        private final String name;
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private final List<Modifier> modifiers = new ArrayList<>();

        private Builder(TypeName type, String name, Modifier... modifiers) {
            this.name = name;
            this.type = type;
            Collections.addAll(this.modifiers, modifiers);
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

        public Builder addModifiers(Iterable<Modifier> modifiers) {
            checkNotNull(modifiers, "modifiers == null");
            for (Modifier modifier : modifiers) {
                this.modifiers.add(modifier);
            }
            return this;
        }

        public ParameterSpec build() {
            return new ParameterSpec(this);
        }
    }
}