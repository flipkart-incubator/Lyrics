package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.model.FormatArgs;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class FieldSpec {
    public final Class<?> clazz;
    public final String name;
    public final TypeName type;
    public final Modifier[] modifier;
    public final FormatArgs initializer;
    public final List<Modifier> modifiers = new ArrayList<>();
    public final List<ClassName> classNames = new ArrayList<>();
    public final List<Class<?>> annotationClasses = new ArrayList<>();
    public final List<AnnotationSpec> annotationSpecs = new ArrayList<>();


    public FieldSpec(Builder builder) {
        this.clazz = builder.clazz;
        this.name = builder.name;
        this.type = builder.type;
        this.modifier = builder.modifier;
        this.initializer = builder.initializer;
        this.modifiers.addAll(builder.modifiers);
        this.classNames.addAll(builder.classNames);
        this.annotationClasses.addAll(builder.annotationClasses);
        this.annotationSpecs.addAll(builder.annotationSpecs);
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return Song.factory.createFieldBuilder(typeName, name, modifiers);
    }

    public static Builder builder(Class<?> clazz, String name, Modifier... modifiers) {
        return Song.factory.createFieldBuilder(clazz, name, modifiers);
    }

    public Object getFieldSpec() {
        return null;
    }

    public static abstract class Builder {
        private final Class<?> clazz;
        private final String name;
        private final TypeName type;
        private final Modifier[] modifier;
        private FormatArgs initializer;
        private final List<Modifier> modifiers = new ArrayList<>();
        private final List<ClassName> classNames = new ArrayList<>();
        private final List<Class<?>> annotationClasses = new ArrayList<>();
        private final List<AnnotationSpec> annotationSpecs = new ArrayList<>();

        public Builder(TypeName type, String name, Modifier... modifiers) {
            this.type = type;
            this.name = name;
            this.clazz = null;
            this.modifier = modifiers;
        }

        public Builder(Class<?> clazz, String name, Modifier... modifiers) {
            this.type = null;
            this.name = name;
            this.clazz = clazz;
            this.modifier = modifiers;
        }

        public FieldSpec.Builder initializer(String format, Object... args) {
            this.initializer = new FormatArgs(format, args);
            return this;
        }

        public FieldSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotationClasses.add(clazz);
            return this;
        }

        public FieldSpec.Builder addAnnotation(ClassName className) {
            this.classNames.add(className);
            return this;
        }

        public FieldSpec.Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotationSpecs.add(annotationSpec);
            return this;
        }

        public FieldSpec.Builder addModifiers(Modifier... modifiers) {
            this.modifiers.addAll(Arrays.asList(modifiers));
            return this;
        }

        public abstract FieldSpec build();
    }
}
