package com.flipkart.lyrics.implementations;

import com.flipkart.lyrics.interfaces.MethodSpec;
import com.flipkart.lyrics.interfaces.ParameterSpec;
import com.flipkart.lyrics.interfaces.model.FormatArgs;
import com.flipkart.lyrics.interfaces.model.TypeNameModifiers;
import com.flipkart.lyrics.interfaces.model.TypeNameNameModifiers;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.flipkart.lyrics.helper.JavaHelper.*;

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
        if (builder.returnsTypeName != null) {
            this.builder.returns(getJavaTypeName(builder.returnsTypeName));
        }
        for (FormatArgs formatArgs : builder.statementFormatArgs) {
            this.builder.addStatement(formatArgs.getFormat(), formatArgs.getArgs());
        }
        if (builder.returnType != null) {
            this.builder.returns(builder.returnType);
        }
        for (ParameterSpec parameterSpec : builder.parameterSpecs) {
            this.builder.addParameter((com.squareup.javapoet.ParameterSpec) parameterSpec.getParameterSpec());
        }
        for (Class<?> clazz : builder.annotationClasses) {
            this.builder.addAnnotation(clazz);
        }
        for (ClassName className : builder.annotationClassNames) {
            this.builder.addAnnotation(getJavaClassName(className));
        }
        for (FormatArgs code : builder.codeFormatArgs) {
            this.builder.addCode(code.getFormat(), code.getArgs());
        }
        for (FormatArgs comment : builder.commentFormatArgs) {
            this.builder.addComment(comment.getFormat(), comment.getArgs());
        }
        for (TypeNameNameModifiers parameter : builder.parameterTypeNameNameModifiers) {
            this.builder.addParameter(getJavaTypeName(parameter.getTypeName()), parameter.getName(), getJavaModifiers(parameter.getModifiers()));
        }
        for (TypeNameModifiers parameter : builder.parameterTypeNameModifiers) {
            this.builder.addParameter(parameter.getType(), parameter.getName(), getJavaModifiers(parameter.getModifiers()));
        }
        for (TypeNameModifiers parameter : builder.parameterTypeName) {
            this.builder.addParameter(parameter.getType(), parameter.getName());
        }
        if (builder.defaultValue != null) {
            this.builder.defaultValue(builder.defaultValue.getFormat(), builder.defaultValue.getArgs());
        }
    }

    @Override
    public Object getMethodSpec() {
        return builder.build();
    }

    public static final class Builder extends MethodSpec.Builder {
        private List<Modifier> modifiers = new ArrayList<>();
        private List<FormatArgs> statementFormatArgs = new ArrayList<>();
        private TypeName returnsTypeName;
        private Type returnType;
        private List<ParameterSpec> parameterSpecs = new ArrayList<>();
        private List<Class<?>> annotationClasses = new ArrayList<>();
        private List<ClassName> annotationClassNames = new ArrayList<>();
        private List<FormatArgs> codeFormatArgs = new ArrayList<>();
        private List<FormatArgs> commentFormatArgs = new ArrayList<>();
        private List<TypeNameNameModifiers> parameterTypeNameNameModifiers = new ArrayList<>();
        private List<TypeNameModifiers> parameterTypeNameModifiers = new ArrayList<>();
        private List<TypeNameModifiers> parameterTypeName = new ArrayList<>();
        private FormatArgs defaultValue;

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
        public MethodSpec.Builder returns(TypeName typeName) {
            this.returnsTypeName = typeName;
            return this;
        }

        @Override
        public MethodSpec.Builder returns(Type type) {
            this.returnType = type;
            return this;
        }

        @Override
        public MethodSpec.Builder addStatement(String format, Object... args) {
            this.statementFormatArgs.add(new FormatArgs(format, args));
            return this;
        }

        @Override
        public MethodSpec.Builder addParameter(ParameterSpec parameterSpec) {
            this.parameterSpecs.add(parameterSpec);
            return this;
        }

        @Override
        public MethodSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotationClasses.add(clazz);
            return this;
        }

        @Override
        public MethodSpec.Builder addAnnotation(ClassName className) {
            this.annotationClassNames.add(className);
            return this;
        }

        @Override
        public MethodSpec.Builder addCode(String format, Object... args) {
            this.codeFormatArgs.add(new FormatArgs(format, args));
            return this;
        }

        @Override
        public MethodSpec.Builder addComment(String format, Object... args) {
            this.commentFormatArgs.add(new FormatArgs(format, args));
            return this;
        }

        @Override
        public MethodSpec.Builder addParameter(TypeName typeName, String name, Modifier... modifiers) {
            this.parameterTypeNameNameModifiers.add(new TypeNameNameModifiers(typeName, name, modifiers));
            return this;
        }

        @Override
        public MethodSpec.Builder addParameter(Type type, String name, Modifier... modifiers) {
            this.parameterTypeNameModifiers.add(new TypeNameModifiers(type, name, modifiers));
            return this;
        }

        @Override
        public MethodSpec.Builder addParameter(Type type, String name) {
            this.parameterTypeName.add(new TypeNameModifiers(type, name, null));
            return this;
        }

        @Override
        public MethodSpec.Builder defaultValue(String format, Object... args) {
            this.defaultValue = new FormatArgs(format, args);
            return this;
        }

        public MethodSpec build() {
            return new JavaMethodSpec(this);
        }
    }
}
