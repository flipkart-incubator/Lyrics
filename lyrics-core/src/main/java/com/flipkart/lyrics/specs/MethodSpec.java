package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.Song;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author kushal.sharma on 09/08/17.
 */
public class MethodSpec {
    static final String CONSTRUCTOR = "<init>";

    public final String name;
    public final CodeBlock defaultValue;
    public final TypeName returnType;
    public final Set<Modifier> modifiers = new HashSet<>();
    public final List<CodeBlock> codeBlocks = new ArrayList<>();
    public final List<CodeBlock> comments = new ArrayList<>();
    public final List<CodeBlock> statements = new ArrayList<>();
    public final List<AnnotationSpec> annotations = new ArrayList<>();
    public final List<ParameterSpec> parameters = new ArrayList<>();
    public boolean varargs;

    protected MethodSpec(Builder builder) {
        this.name = builder.name;
        this.modifiers.addAll(builder.modifiers);
        this.defaultValue = builder.defaultValue;
        this.returnType = builder.returnType;
        this.codeBlocks.addAll(builder.codeBlocks);
        this.comments.addAll(builder.comments);
        this.statements.addAll(builder.statements);
        this.annotations.addAll(builder.annotations);
        this.parameters.addAll(builder.parameters);
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

    public Builder toBuilder() {
        Builder builder = new Builder(name);
        builder.annotations.addAll(annotations);
        builder.modifiers.addAll(modifiers);
        builder.returnType = returnType;
        builder.codeBlocks.addAll(codeBlocks);
        builder.comments.addAll(comments);
        builder.statements.addAll(statements);
        builder.parameters.addAll(parameters);
        builder.defaultValue = defaultValue;
        return builder;
    }

    void emit(CodeWriter codeWriter, String enclosingName, Set<Modifier> implicitModifiers)
            throws IOException {
        codeWriter.emitAnnotations(annotations, false);
        codeWriter.emitModifiers(modifiers, implicitModifiers);

        if (isConstructor()) {
            codeWriter.emit("$L(", enclosingName);
        } else {
            codeWriter.emit("$T $L(", returnType, name);
        }

        boolean firstParameter = true;
        for (Iterator<ParameterSpec> i = parameters.iterator(); i.hasNext(); ) {
            ParameterSpec parameter = i.next();
            if (!firstParameter) codeWriter.emit(", ");
            parameter.emit(codeWriter, !i.hasNext() && varargs);
            firstParameter = false;
        }

        codeWriter.emit(")");

        if (defaultValue != null && !defaultValue.isEmpty()) {
            codeWriter.emit(" default ");
            codeWriter.emit(defaultValue);
        }

        if (modifiers.contains(Modifier.ABSTRACT)) {
            codeWriter.emit(";\n");
        } else {
            codeWriter.emit(" {\n");

            codeWriter.indent();
            codeWriter.emit("");
            codeWriter.unindent();

            codeWriter.emit("}\n");
        }
    }

    public boolean isConstructor() {
        return name.equals(CONSTRUCTOR);
    }

    public static class Builder {
        private final String name;
        private final Set<Modifier> modifiers = new HashSet<>();
        private final List<CodeBlock> codeBlocks = new ArrayList<>();
        private final List<CodeBlock> comments = new ArrayList<>();
        private final List<CodeBlock> statements = new ArrayList<>();
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private final List<ParameterSpec> parameters = new ArrayList<>();
        private CodeBlock defaultValue;
        private TypeName returnType;

        protected Builder(String name) {
            this.name = name;
        }

        public MethodSpec.Builder addModifiers(Modifier... modifiers) {
            this.modifiers.addAll(Arrays.asList(modifiers));
            return this;
        }

        public MethodSpec.Builder returns(TypeName typeName) {
            this.returnType = typeName;
            return this;
        }

        public MethodSpec.Builder returns(Type type) {
            this.returnType = TypeName.get(type);
            return this;
        }

        public MethodSpec.Builder addStatement(String format, Object... args) {
            this.statements.add(CodeBlock.of(format, args));
            return this;
        }

        public MethodSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotations.add(AnnotationSpec.builder(clazz).build());
            return this;
        }

        public MethodSpec.Builder addAnnotation(ClassName className) {
            this.annotations.add(AnnotationSpec.builder(className).build());
            return this;
        }

        public MethodSpec.Builder addCode(String format, Object... args) {
            this.codeBlocks.add(CodeBlock.of(format, args));
            return this;
        }

        public MethodSpec.Builder addComment(String format, Object... args) {
            this.comments.add(CodeBlock.of(format, args));
            return this;
        }

        public MethodSpec.Builder addParameter(ParameterSpec parameterSpec) {
            this.parameters.add(parameterSpec);
            return this;
        }

        public MethodSpec.Builder addParameter(TypeName typeName, String name, Modifier... modifiers) {
            this.parameters.add(ParameterSpec.builder(typeName, name, modifiers).build());
            return this;
        }

        public MethodSpec.Builder addParameter(Class<?> type, String name, Modifier... modifiers) {
            this.parameters.add(ParameterSpec.builder(type, name, modifiers).build());
            return this;
        }

        public MethodSpec.Builder defaultValue(String format, Object... args) {
            this.defaultValue = CodeBlock.of(format, args);
            return this;
        }

        public MethodSpec build() {
            return new MethodSpec(this);
        }
    }
}