/*
 * Copyright 2016 Flipkart Internet, pvt ltd.
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

import javax.lang.model.SourceVersion;
import java.lang.reflect.Type;
import java.util.*;

import static com.flipkart.lyrics.helper.Util.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class TypeSpec {
    public final Kind kind;
    public final String name;
    public final String anonymousClassFormat;
    public final Object[] anonymousClassArgs;
    public final CodeBlock javadoc;
    public final List<AnnotationSpec> annotations;
    public final Set<Modifier> modifiers;
    public final List<TypeVariableName> typeVariables;
    public final TypeName superclass;
    public final List<TypeName> superinterfaces;
    public final Map<String, TypeSpec> enumConstants;
    public final List<FieldSpec> fieldSpecs;
    public final CodeBlock staticBlock;
    public final CodeBlock initializerBlock;
    public final List<MethodSpec> methodSpecs;
    public final List<TypeSpec> typeSpecs;

    public TypeSpec(Builder builder) {
        this.kind = builder.kind;
        this.name = builder.name;
        this.anonymousClassFormat = builder.anonymousClassFormat;
        this.anonymousClassArgs = builder.anonymousClassArgs;
        this.javadoc = builder.doc.build();
        this.annotations = Util.immutableList(builder.annotations);
        this.modifiers = Util.immutableSet(builder.modifiers);
        this.typeVariables = Util.immutableList(builder.typeVariables);
        this.superclass = builder.superclass;
        this.superinterfaces = Util.immutableList(builder.superinterfaces);
        this.enumConstants = Util.immutableMap(builder.enumConstants);
        this.fieldSpecs = Util.immutableList(builder.fieldSpecs);
        this.staticBlock = builder.staticBlock.build();
        this.initializerBlock = builder.initializerBlock.build();
        this.methodSpecs = Util.immutableList(builder.methodSpecs);
        this.typeSpecs = Util.immutableList(builder.typeSpecs);
    }

    public static Builder classBuilder(String name) {
        return new Builder(Kind.CLASS, name);
    }

    public static Builder annotationBuilder(String name) {
        return new Builder(Kind.ANNOTATION, name);
    }

    public static Builder interfaceBuilder(String name) {
        return new Builder(Kind.INTERFACE, name);
    }

    public static Builder enumBuilder(String name) {
        return new Builder(Kind.ENUM, name);
    }

    public static Builder anonymousClassBuilder(String typeArgumentsFormat, Object... args) {
        return new Builder(Kind.ANONYMOUS, typeArgumentsFormat, args);
    }

    public boolean hasModifier(Modifier modifier) {
        return modifiers.contains(modifier);
    }

    public Builder toBuilder() {
        Builder builder = new Builder(kind, name);
        builder.doc.add(javadoc);
        builder.annotations.addAll(annotations);
        builder.modifiers.addAll(modifiers);
        builder.typeVariables.addAll(typeVariables);
        builder.superclass = superclass;
        builder.superinterfaces.addAll(superinterfaces);
        builder.enumConstants.putAll(enumConstants);
        builder.fieldSpecs.addAll(fieldSpecs);
        builder.methodSpecs.addAll(methodSpecs);
        builder.typeSpecs.addAll(typeSpecs);
        builder.initializerBlock.add(initializerBlock);
        builder.staticBlock.add(staticBlock);
        return builder;
    }

    public static class Builder {
        private final Kind kind;
        private final String name;
        private final String anonymousClassFormat;
        private final Object[] anonymousClassArgs;
        private final CodeBlock.Builder doc = CodeBlock.builder();
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private final List<Modifier> modifiers = new ArrayList<>();
        private final List<TypeVariableName> typeVariables = new ArrayList<>();
        private final List<TypeName> superinterfaces = new ArrayList<>();
        private final Map<String, TypeSpec> enumConstants = new LinkedHashMap<>();
        private final List<FieldSpec> fieldSpecs = new ArrayList<>();
        private final CodeBlock.Builder staticBlock = CodeBlock.builder();
        private final CodeBlock.Builder initializerBlock = CodeBlock.builder();
        private final List<MethodSpec> methodSpecs = new ArrayList<>();
        private final List<TypeSpec> typeSpecs = new ArrayList<>();
        private TypeName superclass;

        public Builder(Kind kind, String name) {
            this.kind = kind;
            this.name = name;
            this.anonymousClassFormat = null;
            this.anonymousClassArgs = null;
        }

        private Builder(Kind kind, String typeArgumentsFormat, Object... args) {
            this.kind = kind;
            this.name = null;
            this.anonymousClassFormat = typeArgumentsFormat;
            this.anonymousClassArgs = args;
        }

        public Builder addDoc(String format, Object... args) {
            doc.add(format, args);
            return this;
        }

        public Builder addDoc(CodeBlock block) {
            doc.add(block);
            return this;
        }

        public Builder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
            checkArgument(annotationSpecs != null, "annotationSpecs == null");
            for (AnnotationSpec annotationSpec : annotationSpecs) {
                this.annotations.add(annotationSpec);
            }
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotations.add(annotationSpec);
            return this;
        }

        public Builder addAnnotation(ClassName annotation) {
            return addAnnotation(AnnotationSpec.builder(annotation).build());
        }

        public Builder addAnnotation(Class<?> annotation) {
            return addAnnotation(ClassName.get(annotation));
        }

        public Builder addModifiers(Modifier... modifiers) {
            for (Modifier modifier : modifiers) {
                checkArgument(modifier != null, "modifiers contain null");
                this.modifiers.add(modifier);
            }
            return this;
        }

        public Builder addTypeVariables(Iterable<TypeVariableName> typeVariables) {
            checkArgument(typeVariables != null, "typeVariables == null");
            for (TypeVariableName typeVariable : typeVariables) {
                this.typeVariables.add(typeVariable);
            }
            return this;
        }

        public Builder addTypeVariable(TypeVariableName typeVariable) {
            typeVariables.add(typeVariable);
            return this;
        }

        public Builder superclass(TypeName superclass) {
            checkState(this.kind == Kind.CLASS, "only classes have super classes, not " + this.kind);
            checkArgument(!superclass.isPrimitive(), "superclass may not be a primitive");
            this.superclass = superclass;
            return this;
        }

        public Builder superclass(Type superclass) {
            return superclass(TypeName.get(superclass));
        }

        public Builder addSuperinterfaces(Iterable<? extends TypeName> superinterfaces) {
            checkArgument(superinterfaces != null, "superinterfaces == null");
            for (TypeName superinterface : superinterfaces) {
                addSuperinterface(superinterface);
            }
            return this;
        }

        public Builder addSuperinterface(TypeName superinterface) {
            checkArgument(superinterface != null, "superinterface == null");
            this.superinterfaces.add(superinterface);
            return this;
        }

        public Builder addSuperinterface(Type superinterface) {
            return addSuperinterface(TypeName.get(superinterface));
        }

        public Builder addEnumConstant(String name) {
            return addEnumConstant(name, anonymousClassBuilder("").build());
        }

        public Builder addEnumConstant(String name, TypeSpec typeSpec) {
            checkArgument(SourceVersion.isName(name), "not a valid enum constant: %s", name);
            enumConstants.put(name, typeSpec);
            return this;
        }

        public Builder addFields(Iterable<FieldSpec> fieldSpecs) {
            checkArgument(fieldSpecs != null, "fieldSpecs == null");
            for (FieldSpec fieldSpec : fieldSpecs) {
                addField(fieldSpec);
            }
            return this;
        }

        public Builder addField(FieldSpec fieldSpec) {
            if (kind == Kind.INTERFACE || kind == Kind.ANNOTATION) {
                requireExactlyOneOf(fieldSpec.modifiers, Modifier.PUBLIC, Modifier.PRIVATE);
                Set<Modifier> check = EnumSet.of(Modifier.STATIC, Modifier.FINAL);
                checkState(fieldSpec.modifiers.containsAll(check), "%s %s.%s requires modifiers %s",
                        kind, name, fieldSpec.name, check);
            }
            fieldSpecs.add(fieldSpec);
            return this;
        }

        public Builder addField(TypeName type, String name, Modifier... modifiers) {
            return addField(FieldSpec.builder(type, name, modifiers).build());
        }

        public Builder addField(Type type, String name, Modifier... modifiers) {
            return addField(TypeName.get(type), name, modifiers);
        }

        public Builder addStaticBlock(CodeBlock block) {
            staticBlock.beginControlFlow("static").add(block).endControlFlow();
            return this;
        }

        public Builder addInitializerBlock(CodeBlock block) {
            if ((kind != Kind.CLASS && kind != Kind.ENUM)) {
                throw new UnsupportedOperationException(kind + " can't have initializer blocks");
            }
            initializerBlock.add("{\n")
                    .indent()
                    .add(block)
                    .unindent()
                    .add("}\n");
            return this;
        }

        public Builder addMethods(Iterable<MethodSpec> methodSpecs) {
            checkArgument(methodSpecs != null, "methodSpecs == null");
            for (MethodSpec methodSpec : methodSpecs) {
                addMethod(methodSpec);
            }
            return this;
        }

        public Builder addMethod(MethodSpec methodSpec) {
            if (kind == Kind.INTERFACE) {
                requireExactlyOneOf(methodSpec.modifiers, Modifier.ABSTRACT, Modifier.STATIC, Util.DEFAULT);
                requireExactlyOneOf(methodSpec.modifiers, Modifier.PUBLIC, Modifier.PRIVATE);
            } else if (kind == Kind.ANNOTATION) {
                checkState(methodSpec.modifiers.equals(kind.implicitMethodModifiers),
                        "%s %s.%s requires modifiers %s",
                        kind, name, methodSpec.name, kind.implicitMethodModifiers);
            }
            if (kind != Kind.ANNOTATION) {
                checkState(methodSpec.defaultValue == null, "%s %s.%s cannot have a default value",
                        kind, name, methodSpec.name);
            }
            if (kind != Kind.INTERFACE) {
                checkState(!hasDefaultModifier(methodSpec.modifiers), "%s %s.%s cannot be default",
                        kind, name, methodSpec.name);
            }
            methodSpecs.add(methodSpec);
            return this;
        }

        public Builder addTypes(Iterable<TypeSpec> typeSpecs) {
            checkArgument(typeSpecs != null, "typeSpecs == null");
            for (TypeSpec typeSpec : typeSpecs) {
                addType(typeSpec);
            }
            return this;
        }

        public Builder addType(TypeSpec typeSpec) {
            checkArgument(typeSpec.modifiers.containsAll(kind.implicitTypeModifiers),
                    "%s %s.%s requires modifiers %s", kind, name, typeSpec.name,
                    kind.implicitTypeModifiers);
            typeSpecs.add(typeSpec);
            return this;
        }

        public TypeSpec build() {
            return new TypeSpec(this);
        }
    }
}
