package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.Song;

import java.io.IOException;
import java.util.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class ParameterSpec {
    public final String name;
    public final TypeName type;
    public final Set<Modifier> modifiers = new HashSet<>();
    public final List<AnnotationSpec> annotations = new ArrayList<>();

    public ParameterSpec(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.modifiers.addAll(builder.modifiers);
        this.annotations.addAll(builder.annotations);
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return Song.factory.createParameterBuilder(typeName, name, modifiers);
    }

    public static Builder builder(Class<?> clazz, String name, Modifier... modifiers) {
        return Song.factory.createParameterBuilder(TypeName.get(clazz), name, modifiers);
    }

    public Object getParameterSpec() {
        return null;
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
        private final String name;
        private final TypeName type;
        private final List<Modifier> modifiers = new ArrayList<>();
        private final List<AnnotationSpec> annotations = new ArrayList<>();

        protected Builder(TypeName type, String name, Modifier... modifiers) {
            this.name = name;
            this.type = type;
            Collections.addAll(this.modifiers, modifiers);
        }

        protected Builder(Class<?> clazz, String name, Modifier... modifiers) {
            this.name = name;
            this.type = TypeName.get(clazz);
            Collections.addAll(this.modifiers, modifiers);
        }

        public ParameterSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotations.add(AnnotationSpec.builder(clazz).build());
            return this;
        }

        public ParameterSpec.Builder addAnnotation(ClassName className) {
            this.annotations.add(AnnotationSpec.builder(className).build());
            return this;
        }

        public ParameterSpec build() {
            return new ParameterSpec(this);
        }
    }
}
