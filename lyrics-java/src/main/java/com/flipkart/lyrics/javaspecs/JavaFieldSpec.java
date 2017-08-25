package com.flipkart.lyrics.javaspecs;

import com.flipkart.lyrics.specs.AnnotationSpec;
import com.flipkart.lyrics.specs.FieldSpec;
import com.flipkart.lyrics.specs.Modifier;
import com.flipkart.lyrics.specs.TypeName;

import static com.flipkart.lyrics.helper.JavaHelper.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class JavaFieldSpec extends FieldSpec {
    public final com.squareup.javapoet.FieldSpec.Builder builder;

    JavaFieldSpec(Builder builder) {
        super(builder);

        this.builder = com.squareup.javapoet.FieldSpec.builder(getJavaTypeName(this.type), this.name, getJavaModifiers(this.modifiers.toArray(new Modifier[this.modifiers.size()])));

        if (this.initializer != null) {
            this.builder.initializer(this.initializer.format, this.initializer.arguments);
        }
        for (AnnotationSpec annotationSpec : this.annotations) {
            this.builder.addAnnotation((com.squareup.javapoet.AnnotationSpec) annotationSpec.getAnnotationSpec());
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
