package com.flipkart.lyrics.implementations;

import com.flipkart.lyrics.interfaces.MethodSpec;
import com.flipkart.lyrics.interfaces.ParameterSpec;
import com.flipkart.lyrics.interfaces.model.FormatArgs;
import com.flipkart.lyrics.interfaces.model.TypeNameModifiers;
import com.flipkart.lyrics.interfaces.model.TypeNameNameModifiers;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;

import static com.flipkart.lyrics.helper.JavaHelper.*;

/**
 * @author kushal.sharma on 09/08/17.
 */
public class JavaMethodSpec extends MethodSpec {
    public final com.squareup.javapoet.MethodSpec.Builder builder;

    JavaMethodSpec(Builder builder) {
        super(builder);

        this.builder = com.squareup.javapoet.MethodSpec.methodBuilder(this.name);

        for (Modifier modifier : this.modifiers) {
            this.builder.addModifiers(getJavaModifier(modifier));
        }
        if (this.returnsTypeName != null) {
            this.builder.returns(getJavaTypeName(this.returnsTypeName));
        }
        for (FormatArgs formatArgs : this.statementFormatArgs) {
            this.builder.addStatement(formatArgs.getFormat(), formatArgs.getArgs());
        }
        if (this.returnType != null) {
            this.builder.returns(this.returnType);
        }
        for (ParameterSpec parameterSpec : this.parameterSpecs) {
            this.builder.addParameter((com.squareup.javapoet.ParameterSpec) parameterSpec.getParameterSpec());
        }
        for (Class<?> clazz : this.annotationClasses) {
            this.builder.addAnnotation(clazz);
        }
        for (ClassName className : this.annotationClassNames) {
            this.builder.addAnnotation(getJavaClassName(className));
        }
        for (FormatArgs code : this.codeFormatArgs) {
            this.builder.addCode(code.getFormat(), code.getArgs());
        }
        for (FormatArgs comment : this.commentFormatArgs) {
            this.builder.addComment(comment.getFormat(), comment.getArgs());
        }
        for (TypeNameNameModifiers parameter : this.parameterTypeNameNameModifiers) {
            this.builder.addParameter(getJavaTypeName(parameter.getTypeName()), parameter.getName(), getJavaModifiers(parameter.getModifiers()));
        }
        for (TypeNameModifiers parameter : this.parameterTypeNameModifiers) {
            this.builder.addParameter(parameter.getType(), parameter.getName(), getJavaModifiers(parameter.getModifiers()));
        }
        for (TypeNameModifiers parameter : this.parameterTypeName) {
            this.builder.addParameter(parameter.getType(), parameter.getName());
        }
        if (this.defaultValue != null) {
            this.builder.defaultValue(this.defaultValue.getFormat(), this.defaultValue.getArgs());
        }
    }

    @Override
    public Object getMethodSpec() {
        return builder.build();
    }

    public static final class Builder extends MethodSpec.Builder {
        public Builder() {
            super("<init>");
        }

        public Builder(String name) {
            super(name);
        }

        public MethodSpec build() {
            return new JavaMethodSpec(this);
        }
    }
}
