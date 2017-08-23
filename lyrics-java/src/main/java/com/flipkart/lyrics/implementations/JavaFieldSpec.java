package com.flipkart.lyrics.implementations;

import com.flipkart.lyrics.interfaces.AnnotationSpec;
import com.flipkart.lyrics.interfaces.FieldSpec;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

import static com.flipkart.lyrics.helper.JavaHelper.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class JavaFieldSpec extends FieldSpec {
    public final com.squareup.javapoet.FieldSpec.Builder builder;

    JavaFieldSpec(Builder builder) {
        super(builder);

        if (this.clazz == null) {
            this.builder = com.squareup.javapoet.FieldSpec.builder(getJavaTypeName(this.type), this.name, getJavaModifiers(this.modifier));
        } else {
            this.builder = com.squareup.javapoet.FieldSpec.builder(this.clazz, this.name, getJavaModifiers(this.modifier));
        }

        if (this.initializer != null)
            this.builder.initializer(this.initializer.getFormat(), this.initializer.getArgs());
        for (ClassName className : this.classNames) {
            this.builder.addAnnotation(getJavaClassName(className));
        }
        for (Class clazz : this.annotationClasses) {
            this.builder.addAnnotation(clazz);
        }
        for (AnnotationSpec annotationSpec : this.annotationSpecs) {
            this.builder.addAnnotation((com.squareup.javapoet.AnnotationSpec) annotationSpec.getAnnotationSpec());
        }
        for (Modifier modifier : this.modifiers) {
            this.builder.addModifiers(getJavaModifier(modifier));
        }
    }

    @Override
    public Object getFieldSpec() {
        return builder.build();
    }

    public static final class Builder extends FieldSpec.Builder {
        public Builder(TypeName typeName, String name, Modifier... modifiers) {
            super(typeName, name, modifiers);
        }

        public Builder(Class<?> clazz, String name, Modifier... modifiers) {
            super(clazz, name, modifiers);
        }

        @Override
        public FieldSpec build() {
            return new JavaFieldSpec(this);
        }
    }
}
