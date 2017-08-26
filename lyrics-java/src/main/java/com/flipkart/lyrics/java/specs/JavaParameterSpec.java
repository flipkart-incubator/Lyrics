package com.flipkart.lyrics.java.specs;

import static com.flipkart.lyrics.java.helper.JavaHelper.getJavaModifiers;
import static com.flipkart.lyrics.java.helper.JavaHelper.getJavaTypeName;

/**
 * @author kushal.sharma on 11/08/17.
 */
public class JavaParameterSpec extends ParameterSpec {
    public final com.squareup.javapoet.ParameterSpec.Builder builder;

    JavaParameterSpec(Builder builder) {
        super(builder);

        this.builder = com.squareup.javapoet.ParameterSpec.builder(getJavaTypeName(this.type), this.name, getJavaModifiers(this.modifiers));

        for (AnnotationSpec annotation : this.annotations) {
            this.builder.addAnnotation((com.squareup.javapoet.AnnotationSpec) annotation.getAnnotationSpec());
        }
    }

    @Override
    public Object getParameterSpec() {
        return builder.build();
    }

    public static final class Builder extends ParameterSpec.Builder {
        public Builder(TypeName type, String name, Modifier... modifiers) {
            super(type, name, modifiers);
        }

        public Builder(Class<?> clazz, String name, Modifier... modifiers) {
            super(clazz, name, modifiers);
        }

        @Override
        public ParameterSpec build() {
            return new JavaParameterSpec(this);
        }
    }
}
