package com.flipkart.lyrics.implementations;

import com.flipkart.lyrics.interfaces.AnnotationSpec;
import com.flipkart.lyrics.interfaces.FieldSpec;
import com.flipkart.lyrics.interfaces.model.FormatArgs;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.flipkart.lyrics.helper.JavaHelper.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class JavaFieldSpec extends FieldSpec {
    private com.squareup.javapoet.FieldSpec.Builder builder;

    public JavaFieldSpec(Builder builder) {
        super(builder);
        this.builder = com.squareup.javapoet.FieldSpec.builder(getJavaTypeName(builder.type), builder.name, getJavaModifiers(builder.modifier));
        if (builder.initializer != null)
            this.builder.initializer(builder.initializer.getFormat(), builder.initializer.getArgs());
        for (ClassName className : builder.classNames) {
            this.builder.addAnnotation(getJavaClassName(className));
        }
        for (Class clazz : builder.annotationClasses) {
            this.builder.addAnnotation(clazz);
        }
        for (AnnotationSpec annotationSpec : builder.annotationSpecs) {
            this.builder.addAnnotation((com.squareup.javapoet.AnnotationSpec) annotationSpec.getAnnotationSpec());
        }
        for (Modifier modifier : builder.modifiers) {
            this.builder.addModifiers(getJavaModifier(modifier));
        }
    }

    @Override
    public Object getFieldSpec() {
        return builder.build();
    }

    public static final class Builder extends FieldSpec.Builder {
        private FormatArgs initializer;
        private List<ClassName> classNames = new ArrayList<>();
        private List<Class<?>> annotationClasses = new ArrayList<>();
        private List<AnnotationSpec> annotationSpecs = new ArrayList<>();
        private List<Modifier> modifiers = new ArrayList<>();

        public Builder(TypeName typeName, String name, Modifier... modifiers) {
            super(typeName, name, modifiers);
        }

        @Override
        public FieldSpec.Builder initializer(String format, Object... args) {
            this.initializer = new FormatArgs(format, args);
            return this;
        }

        @Override
        public FieldSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotationClasses.add(clazz);
            return this;
        }

        @Override
        public FieldSpec.Builder addAnnotation(ClassName className) {
            this.classNames.add(className);
            return this;
        }

        @Override
        public FieldSpec.Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotationSpecs.add(annotationSpec);
            return this;
        }

        @Override
        public FieldSpec.Builder addModifiers(Modifier... modifiers) {
            this.modifiers.addAll(Arrays.asList(modifiers));
            return this;
        }

        @Override
        public FieldSpec build() {
            return new JavaFieldSpec(this);
        }
    }
}
