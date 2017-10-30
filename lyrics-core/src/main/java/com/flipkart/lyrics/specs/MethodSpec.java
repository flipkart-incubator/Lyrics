/*
 * Copyright 2017 Flipkart Internet, pvt ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.helper.Util;

import java.lang.reflect.Type;
import java.util.*;

import static com.flipkart.lyrics.helper.Util.checkNotNull;

public class MethodSpec {
    static final String CONSTRUCTOR = "<init>";

    public final String name;
    public final CodeBlock doc;
    public final List<AnnotationSpec> annotations;
    public final Set<Modifier> modifiers;
    public final List<TypeVariableName> typeVariables;
    public final TypeName returnType;
    public final List<ParameterSpec> parameters;
    public final boolean varargs;
    public final List<TypeName> exceptions;
    public final CodeBlock defaultValue;
    public final CodeBlock code;

    protected MethodSpec(Builder builder) {
        this.name = checkNotNull(builder.name, "name == null");
        this.doc = builder.doc.build();
        this.annotations = Util.immutableList(builder.annotations);
        this.modifiers = Util.immutableSet(builder.modifiers);
        this.typeVariables = Util.immutableList(builder.typeVariables);
        this.returnType = builder.returnType;
        this.parameters = Util.immutableList(builder.parameters);
        this.varargs = builder.varargs;
        this.exceptions = Util.immutableList(builder.exceptions);
        this.defaultValue = builder.defaultValue;
        this.code = builder.code.build();
    }

    public static Builder methodBuilder(String name) {
        return new Builder(name);
    }

    public static Builder constructorBuilder() {
        return new Builder(CONSTRUCTOR);
    }

    public boolean hasModifier(Modifier modifier) {
        return modifiers.contains(modifier);
    }

    public Builder toBuilder() {
        Builder builder = new Builder(name);
        builder.doc.add(doc);
        builder.annotations.addAll(annotations);
        builder.modifiers.addAll(modifiers);
        builder.typeVariables.addAll(typeVariables);
        builder.returnType = returnType;
        builder.parameters.addAll(parameters);
        builder.exceptions.addAll(exceptions);
        builder.varargs = varargs;
        builder.defaultValue = defaultValue;
        return builder;
    }

    public boolean isConstructor() {
        return name.equals(CONSTRUCTOR);
    }

    public static final class Builder {
        private final String name;
        private final CodeBlock.Builder doc = CodeBlock.builder();
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private final List<Modifier> modifiers = new ArrayList<>();
        private final List<ParameterSpec> parameters = new ArrayList<>();
        private final Set<TypeName> exceptions = new LinkedHashSet<>();
        private final CodeBlock.Builder code = CodeBlock.builder();
        private List<TypeVariableName> typeVariables = new ArrayList<>();
        private TypeName returnType;
        private boolean varargs;
        private CodeBlock defaultValue;

        protected Builder(String name) {
            this.name = name;
        }

        public MethodSpec.Builder addException(TypeName exception) {
            this.exceptions.add(exception);
            return this;
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

        public MethodSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotations.add(AnnotationSpec.builder(clazz).build());
            return this;
        }

        public MethodSpec.Builder addAnnotation(ClassName className) {
            this.annotations.add(AnnotationSpec.builder(className).build());
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

        public MethodSpec.Builder addCode(String format, Object... args) {
            this.code.add(format, args);
            return this;
        }

        public MethodSpec.Builder addComment(String format, Object... args) {
            this.code.add("// " + format + "\n", args);
            return this;
        }

        public MethodSpec.Builder addStatement(String format, Object... args) {
            this.code.addStatement(format, args);
            return this;
        }

        public MethodSpec.Builder beginControlFlow(String format, Object... args) {
            this.code.beginControlFlow(format, args);
            return this;
        }

        public Builder nextControlFlow(String controlFlow, Object... args) {
            this.code.nextControlFlow(controlFlow, args);
            return this;
        }

        public Builder endControlFlow() {
            this.code.endControlFlow();
            return this;
        }

        public Builder endControlFlow(String controlFlow, Object... args) {
            this.code.endControlFlow(controlFlow, args);
            return this;
        }

        public MethodSpec build() {
            return new MethodSpec(this);
        }
    }
}