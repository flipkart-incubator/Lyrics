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
    public final com.squareup.javapoet.ParameterSpec.Builder builder;

    JavaParameterSpec(Builder builder) {
        super(builder);

        if (this.typeName != null) {
            this.builder = com.squareup.javapoet.ParameterSpec.builder(getJavaTypeName(this.typeName), this.name, getJavaModifiers(this.modifiers));
        } else {
            this.builder = com.squareup.javapoet.ParameterSpec.builder(this.clazz, this.name, getJavaModifiers(this.modifiers));
        }

        for (Class<?> clazz : this.classes) {
            this.builder.addAnnotation(clazz);
        }
        for (ClassName className : this.classNames) {
            this.builder.addAnnotation(getJavaClassName(className));
        }
    }

    @Override
    public Object getParameterSpec() {
        return builder.build();
    }

    public static final class Builder extends ParameterSpec.Builder {
        public Builder(TypeName typeName, String name, Modifier... modifiers) {
            super(typeName, name, modifiers);
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
