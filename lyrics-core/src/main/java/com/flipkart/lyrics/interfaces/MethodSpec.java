package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;
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

/**
 * @author kushal.sharma on 09/08/17.
 */
public class MethodSpec {
    public final String name;
    public final Type returnType;
    public final FormatArgs defaultValue;
    public final TypeName returnsTypeName;
    public final List<Modifier> modifiers = new ArrayList<>();
    public final List<FormatArgs> codeFormatArgs = new ArrayList<>();
    public final List<Class<?>> annotationClasses = new ArrayList<>();
    public final List<ParameterSpec> parameterSpecs = new ArrayList<>();
    public final List<FormatArgs> commentFormatArgs = new ArrayList<>();
    public final List<FormatArgs> statementFormatArgs = new ArrayList<>();
    public final List<ClassName> annotationClassNames = new ArrayList<>();
    public final List<TypeNameModifiers> parameterTypeName = new ArrayList<>();
    public final List<TypeNameModifiers> parameterTypeNameModifiers = new ArrayList<>();
    public final List<TypeNameNameModifiers> parameterTypeNameNameModifiers = new ArrayList<>();

    protected MethodSpec(Builder builder) {
        this.name = builder.name;
        this.modifiers.addAll(builder.modifiers);
        this.returnType = builder.returnType;
        this.defaultValue = builder.defaultValue;
        this.returnsTypeName = builder.returnsTypeName;
        this.parameterSpecs.addAll(builder.parameterSpecs);
        this.codeFormatArgs.addAll(builder.codeFormatArgs);
        this.annotationClasses.addAll(builder.annotationClasses);
        this.commentFormatArgs.addAll(builder.commentFormatArgs);
        this.parameterTypeName.addAll(builder.parameterTypeName);
        this.statementFormatArgs.addAll(builder.statementFormatArgs);
        this.annotationClassNames.addAll(builder.annotationClassNames);
        this.parameterTypeNameModifiers.addAll(builder.parameterTypeNameModifiers);
        this.parameterTypeNameNameModifiers.addAll(builder.parameterTypeNameNameModifiers);
    }

    public static Builder methodBuilder(String name) {
        return Song.factory.createMethodBuilder(name);
    }

    public static Builder constructorBuilder() {
        return Song.factory.createConstructorBuilder();
    }

    public Object getMethodSpec() {
        return null;
    }

    public static abstract class Builder {
        private final String name;
        private Type returnType;
        private FormatArgs defaultValue;
        private TypeName returnsTypeName;
        private final List<Modifier> modifiers = new ArrayList<>();
        private final List<FormatArgs> codeFormatArgs = new ArrayList<>();
        private final List<Class<?>> annotationClasses = new ArrayList<>();
        private final List<ParameterSpec> parameterSpecs = new ArrayList<>();
        private final List<FormatArgs> commentFormatArgs = new ArrayList<>();
        private final List<FormatArgs> statementFormatArgs = new ArrayList<>();
        private final List<ClassName> annotationClassNames = new ArrayList<>();
        private final List<TypeNameModifiers> parameterTypeName = new ArrayList<>();
        private final List<TypeNameModifiers> parameterTypeNameModifiers = new ArrayList<>();
        private final List<TypeNameNameModifiers> parameterTypeNameNameModifiers = new ArrayList<>();

        protected Builder(String name) {
            this.name = name;
        }

        public MethodSpec.Builder addModifiers(Modifier... modifiers) {
            this.modifiers.addAll(Arrays.asList(modifiers));
            return this;
        }

        public MethodSpec.Builder returns(TypeName typeName) {
            this.returnsTypeName = typeName;
            return this;
        }

        public MethodSpec.Builder returns(Type type) {
            this.returnType = type;
            return this;
        }

        public MethodSpec.Builder addStatement(String format, Object... args) {
            this.statementFormatArgs.add(new FormatArgs(format, args));
            return this;
        }

        public MethodSpec.Builder addParameter(ParameterSpec parameterSpec) {
            this.parameterSpecs.add(parameterSpec);
            return this;
        }

        public MethodSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotationClasses.add(clazz);
            return this;
        }

        public MethodSpec.Builder addAnnotation(ClassName className) {
            this.annotationClassNames.add(className);
            return this;
        }

        public MethodSpec.Builder addCode(String format, Object... args) {
            this.codeFormatArgs.add(new FormatArgs(format, args));
            return this;
        }

        public MethodSpec.Builder addComment(String format, Object... args) {
            this.commentFormatArgs.add(new FormatArgs(format, args));
            return this;
        }

        public MethodSpec.Builder addParameter(TypeName typeName, String name, Modifier... modifiers) {
            this.parameterTypeNameNameModifiers.add(new TypeNameNameModifiers(typeName, name, modifiers));
            return this;
        }

        public MethodSpec.Builder addParameter(Type type, String name, Modifier... modifiers) {
            this.parameterTypeNameModifiers.add(new TypeNameModifiers(type, name, modifiers));
            return this;
        }

        public MethodSpec.Builder addParameter(Type type, String name) {
            this.parameterTypeName.add(new TypeNameModifiers(type, name, null));
            return this;
        }

        public MethodSpec.Builder defaultValue(String format, Object... args) {
            this.defaultValue = new FormatArgs(format, args);
            return this;
        }

        public abstract MethodSpec build();
    }
}