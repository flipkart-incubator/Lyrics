package com.flipkart.lyrics.implementations;

import com.flipkart.lyrics.Modifier;
import com.flipkart.lyrics.interfaces.MethodSpec;
import com.flipkart.lyrics.interfaces.ParameterSpec;
import com.flipkart.lyrics.interfaces.model.ClassName;
import com.flipkart.lyrics.interfaces.model.FormatArgs;
import com.flipkart.lyrics.model.Type;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kushal.sharma on 09/08/17.
 */
public class JavaMethodSpec extends MethodSpec {
    private com.squareup.javapoet.MethodSpec.Builder builder;

    public JavaMethodSpec(Builder builder) {
        this.builder = com.squareup.javapoet.MethodSpec.methodBuilder(builder.name);
        for (Modifier modifier : builder.modifiers) {
            this.builder.addModifiers(getJavaModifier(modifier));
        }
        if (builder.returnsTypeName != null) this.builder.returns(getJavaTypeName(builder.returnsTypeName));
        for (FormatArgs formatArgs : builder.statementFormatArgs) {
            this.builder.addStatement(formatArgs.getFormat(), formatArgs.getArgs());
        }
    }

    @Override
    public Object getMethodSpec() {
        return builder.build();
    }

    public static final class Builder extends MethodSpec.Builder {
        private List<Modifier> modifiers = new ArrayList<>();
        private List<FormatArgs> statementFormatArgs = new ArrayList<>();
        private com.flipkart.lyrics.interfaces.model.TypeName returnsTypeName;

        public Builder() {
            super("<init>");
        }

        public Builder(String name) {
            super(name);
        }

        @Override
        public MethodSpec.Builder addModifiers(Modifier... modifiers) {
            this.modifiers.addAll(Arrays.asList(modifiers));
            return this;
        }

        @Override
        public MethodSpec.Builder returns(com.flipkart.lyrics.interfaces.model.TypeName typeName) {
            this.returnsTypeName = typeName;
            return this;
        }

        @Override
        public MethodSpec.Builder returns(java.lang.reflect.Type type) {
            return null;
        }

        @Override
        public MethodSpec.Builder addStatement(String format, Object... args) {
            this.statementFormatArgs.add(new FormatArgs(format, args));
            return this;
        }

        @Override
        public MethodSpec.Builder addParameter(ParameterSpec parameterSpec) {
            return null;
        }

        @Override
        public MethodSpec.Builder addAnnotation(Class<?> clazz) {
            return null;
        }

        @Override
        public MethodSpec.Builder addAnnotation(ClassName className) {
            return null;
        }

        @Override
        public MethodSpec.Builder addCode(String format, Object... args) {
            return null;
        }

        @Override
        public MethodSpec.Builder addComment(String format, Object... args) {
            return null;
        }

        @Override
        public MethodSpec.Builder addParameter(com.flipkart.lyrics.interfaces.model.TypeName typeName, String args, Modifier... modifiers) {
            return null;
        }

        @Override
        public MethodSpec.Builder addParameter(java.lang.reflect.Type type, String name, Modifier... modifiers) {
            return null;
        }

        @Override
        public MethodSpec.Builder addParameter(java.lang.reflect.Type type, String name) {
            return null;
        }

        @Override
        public MethodSpec.Builder defaultValue(String format, Object... args) {
            return null;
        }

        public MethodSpec build() {
            return new JavaMethodSpec(this);
        }
    }
}
