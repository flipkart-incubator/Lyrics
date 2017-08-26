package com.flipkart.lyrics.java.specs;

import com.flipkart.lyrics.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class ParameterSpec {
    public final String name;
    public final TypeName type;
    public final Modifier[] modifiers;
    public final List<AnnotationSpec> annotations = new ArrayList<>();

    public ParameterSpec(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.modifiers = builder.modifiers;
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

    void emit(CodeWriter codeWriter, boolean varargs) throws IOException {
        codeWriter.emitAnnotations(annotations, true);
        codeWriter.emitModifiers(new HashSet<>(Arrays.asList(modifiers)));
        if (varargs) {
            codeWriter.emit("$T... $L", TypeName.arrayComponent(type), name);
        } else {
            codeWriter.emit("$T $L", type, name);
        }
    }

    public static abstract class Builder {
        private final String name;
        private final TypeName type;
        private final Modifier[] modifiers;
        private final List<AnnotationSpec> annotations = new ArrayList<>();

        public Builder(TypeName type, String name, Modifier... modifiers) {
            this.name = name;
            this.type = type;
            this.modifiers = modifiers;
        }

        public Builder(Class<?> clazz, String name, Modifier... modifiers) {
            this.name = name;
            this.type = TypeName.get(clazz);
            this.modifiers = modifiers;
        }

        public ParameterSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotations.add(AnnotationSpec.builder(clazz).build());
            return this;
        }

        public ParameterSpec.Builder addAnnotation(ClassName className) {
            this.annotations.add(AnnotationSpec.builder(className).build());
            return this;
        }

        public abstract ParameterSpec build();
    }
}
