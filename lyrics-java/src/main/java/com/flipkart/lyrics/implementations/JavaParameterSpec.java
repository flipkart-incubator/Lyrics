package com.flipkart.lyrics.implementations;

import com.flipkart.lyrics.interfaces.ParameterSpec;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

import java.util.ArrayList;
import java.util.List;

import static com.flipkart.lyrics.helper.JavaHelper.*;

/**
 * @author kushal.sharma on 11/08/17.
 */
public class JavaParameterSpec extends ParameterSpec {
    private com.squareup.javapoet.ParameterSpec.Builder builder;

    public JavaParameterSpec(Builder builder) {
        if (builder.typeName != null) {
            this.builder = com.squareup.javapoet.ParameterSpec.builder(getJavaTypeName(builder.typeName), builder.name, getJavaModifiers(builder.modifiers));
        } else {
            this.builder = com.squareup.javapoet.ParameterSpec.builder(builder.clazz, builder.name, getJavaModifiers(builder.modifiers));
        }
        for (Class<?> clazz : builder.classes) {
            this.builder.addAnnotation(clazz);
        }
        for (ClassName className : builder.classNames) {
            this.builder.addAnnotation(getJavaClassName(className));
        }
    }

    @Override
    public Object getParameterSpec() {
        return builder.build();
    }

    public static final class Builder extends ParameterSpec.Builder {
        private Class<?> clazz;
        private List<Class<?>> classes = new ArrayList<>();
        private List<ClassName> classNames = new ArrayList<>();

        public Builder(TypeName typeName, String name, Modifier... modifiers) {
            super(typeName, name, modifiers);
        }

        public Builder(Class<?> clazz, String name, Modifier... modifiers) {
            super(null, name, modifiers);
            this.clazz = clazz;
        }

        @Override
        public ParameterSpec.Builder addAnnotation(Class<?> clazz) {
            this.classes.add(clazz);
            return this;
        }

        @Override
        public ParameterSpec.Builder addAnnotation(ClassName className) {
            this.classNames.add(className);
            return this;
        }

        @Override
        public ParameterSpec build() {
            return new JavaParameterSpec(this);
        }
    }
}
