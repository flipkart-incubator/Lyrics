package com.flipkart.lyrics.implementations;

import com.flipkart.lyrics.Modifier;
import com.flipkart.lyrics.interfaces.AnnotationSpec;
import com.flipkart.lyrics.interfaces.FieldSpec;
import com.flipkart.lyrics.interfaces.model.TypeName;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class JavaFieldSpec extends FieldSpec {
    com.squareup.javapoet.FieldSpec.Builder builder;

    public JavaFieldSpec(Builder builder) {
        this.builder = com.squareup.javapoet.FieldSpec.builder(builder.typeName, builder.name, builder.modifier);
    }

    @Override
    public Object getFieldSpec() {
        return builder.build();
    }

    public static final class Builder extends FieldSpec.Builder {
        public Builder(TypeName typeName, String name, Modifier... modifiers) {
            super(typeName, name, modifiers);
        }

        @Override
        public FieldSpec.Builder initializer(String format, Object... args) {
            return null;
        }

        @Override
        public FieldSpec.Builder addAnnotation(Class<?> clazz) {
            return null;
        }

        @Override
        public FieldSpec.Builder addAnnotation(AnnotationSpec annotationSpec) {
            return null;
        }

        @Override
        public FieldSpec.Builder addModifiers(Modifier... modifiers) {
            return null;
        }

        @Override
        public FieldSpec build() {
            return null;
        }
    }
}
