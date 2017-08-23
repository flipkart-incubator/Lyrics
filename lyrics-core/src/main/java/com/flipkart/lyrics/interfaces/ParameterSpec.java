package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class ParameterSpec {
    public Class<?> clazz;
    public final String name;
    public final TypeName typeName;
    public final Modifier[] modifiers;
    public final List<Class<?>> classes = new ArrayList<>();
    public final List<ClassName> classNames = new ArrayList<>();

    public ParameterSpec(Builder builder) {
        this.name = builder.name;
        this.clazz = builder.clazz;
        this.typeName = builder.typeName;
        this.modifiers = builder.modifiers;
        this.classes.addAll(builder.classes);
        this.classNames.addAll(builder.classNames);
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return Song.factory.createParameterBuilder(typeName, name, modifiers);
    }

    public static Builder builder(Class<?> clazz, String name, Modifier... modifiers) {
        return Song.factory.createParameterBuilder(clazz, name, modifiers);
    }

    public Object getParameterSpec() {
        return null;
    }

    public static abstract class Builder {
        private Class<?> clazz;
        private final String name;
        private final TypeName typeName;
        private final Modifier[] modifiers;
        private final List<Class<?>> classes = new ArrayList<>();
        private final List<ClassName> classNames = new ArrayList<>();

        public Builder(TypeName typeName, String name, Modifier... modifiers) {
            this.name = name;
            this.typeName = typeName;
            this.modifiers = modifiers;
        }

        public Builder(Class<?> clazz, String name, Modifier... modifiers) {
            this.clazz = clazz;
            this.name = name;
            this.typeName = null;
            this.modifiers = modifiers;
        }

        public ParameterSpec.Builder addAnnotation(Class<?> clazz) {
            this.classes.add(clazz);
            return this;
        }

        public ParameterSpec.Builder addAnnotation(ClassName className) {
            this.classNames.add(className);
            return this;
        }

        public abstract ParameterSpec build();
    }
}
