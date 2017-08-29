package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.helper.Util;

import javax.lang.model.SourceVersion;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static com.flipkart.lyrics.helper.Util.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class TypeSpec {
    public final Kind kind;
    public final String name;
    public final CodeBlock anonymousTypeArguments;
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
        this.anonymousTypeArguments = builder.anonymousTypeArguments;
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

    public Builder toBuilder() {
        Builder builder = new Builder(kind, name, anonymousTypeArguments);
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

    void emit(CodeWriter codeWriter, String enumName, Set<Modifier> implicitModifiers)
            throws IOException {
        // Nested classes interrupt wrapped line indentation. Stash the current wrapping state and put
        // it back afterwards when this type is complete.
        int previousStatementLine = codeWriter.statementLine;
        codeWriter.statementLine = -1;

        try {
            codeWriter.pushType(this);
            if (enumName != null) {
                codeWriter.emitAnnotations(annotations, false);
                codeWriter.emit("$L", enumName);
                if (!anonymousTypeArguments.formatParts.isEmpty()) {
                    codeWriter.emit("(");
                    codeWriter.emit(anonymousTypeArguments);
                    codeWriter.emit(")");
                }
                if (fieldSpecs.isEmpty() && methodSpecs.isEmpty() && typeSpecs.isEmpty()) {
                    return; // Avoid unnecessary braces "{}".
                }
                codeWriter.emit(" {\n");
            } else if (anonymousTypeArguments != null) {
                TypeName supertype = !superinterfaces.isEmpty() ? superinterfaces.get(0) : superclass;
                codeWriter.emit("new $T(", supertype);
                codeWriter.emit(anonymousTypeArguments);
                codeWriter.emit(") {\n");
            } else {
                codeWriter.emitAnnotations(annotations, false);
                codeWriter.emitModifiers(modifiers, Util.union(implicitModifiers, kind.asMemberModifiers));
                if (kind == Kind.ANNOTATION) {
                    codeWriter.emit("$L $L", "@interface", name);
                } else {
                    codeWriter.emit("$L $L", kind.name().toLowerCase(Locale.US), name);
                }
                codeWriter.emitTypeVariables(typeVariables);

                List<TypeName> extendsTypes;
                List<TypeName> implementsTypes;
                if (kind == Kind.INTERFACE) {
                    extendsTypes = superinterfaces;
                    implementsTypes = Collections.emptyList();
                } else {
                    extendsTypes = superclass.equals(ClassName.OBJECT)
                            ? Collections.emptyList()
                            : Collections.singletonList(superclass);
                    implementsTypes = superinterfaces;
                }

                if (!extendsTypes.isEmpty()) {
                    codeWriter.emit(" extends");
                    boolean firstType = true;
                    for (TypeName type : extendsTypes) {
                        if (!firstType) codeWriter.emit(",");
                        codeWriter.emit(" $T", type);
                        firstType = false;
                    }
                }

                if (!implementsTypes.isEmpty()) {
                    codeWriter.emit(" implements");
                    boolean firstType = true;
                    for (TypeName type : implementsTypes) {
                        if (!firstType) codeWriter.emit(",");
                        codeWriter.emit(" $T", type);
                        firstType = false;
                    }
                }

                codeWriter.emit(" {\n");
            }

            codeWriter.indent();
            boolean firstMember = true;
            for (Iterator<Map.Entry<String, TypeSpec>> i = enumConstants.entrySet().iterator();
                 i.hasNext(); ) {
                Map.Entry<String, TypeSpec> enumConstant = i.next();
                if (!firstMember) codeWriter.emit("\n");
                enumConstant.getValue()
                        .emit(codeWriter, enumConstant.getKey(), Collections.emptySet());
                firstMember = false;
                if (i.hasNext()) {
                    codeWriter.emit(",\n");
                } else if (!fieldSpecs.isEmpty() || !methodSpecs.isEmpty() || !typeSpecs.isEmpty()) {
                    codeWriter.emit(";\n");
                } else {
                    codeWriter.emit("\n");
                }
            }

            // Static fields.
            for (FieldSpec fieldSpec : fieldSpecs) {
                if (!fieldSpec.modifiers.contains(Modifier.STATIC)) continue;
                if (!firstMember) codeWriter.emit("\n");
                fieldSpec.emit(codeWriter, kind.implicitFieldModifiers);
                firstMember = false;
            }

            // Non-static fields.
            for (FieldSpec fieldSpec : fieldSpecs) {
                if (fieldSpec.modifiers.contains(Modifier.STATIC)) continue;
                if (!firstMember) codeWriter.emit("\n");
                fieldSpec.emit(codeWriter, kind.implicitFieldModifiers);
                firstMember = false;
            }

            // Constructors.
            for (MethodSpec methodSpec : methodSpecs) {
                if (!methodSpec.isConstructor()) continue;
                if (!firstMember) codeWriter.emit("\n");
                methodSpec.emit(codeWriter, name, kind.implicitMethodModifiers);
                firstMember = false;
            }

            // Methods (static and non-static).
            for (MethodSpec methodSpec : methodSpecs) {
                if (methodSpec.isConstructor()) continue;
                if (!firstMember) codeWriter.emit("\n");
                methodSpec.emit(codeWriter, name, kind.implicitMethodModifiers);
                firstMember = false;
            }

            // Types.
            for (TypeSpec typeSpec : typeSpecs) {
                if (!firstMember) codeWriter.emit("\n");
                typeSpec.emit(codeWriter, null, kind.implicitTypeModifiers);
                firstMember = false;
            }

            codeWriter.unindent();

            codeWriter.emit("}");
            if (enumName == null && anonymousTypeArguments == null) {
                codeWriter.emit("\n"); // If this type isn't also a value, include a trailing newline.
            }
        } finally {
            codeWriter.popType();
            codeWriter.statementLine = previousStatementLine;
        }
    }

    public void writeToFile(FileWriter fileWriter, String fullPackage, File targetFolder) {
        fileWriter.writeToFile(this, fullPackage, targetFolder);
    }

    public static class Builder {
        private final Kind kind;
        private final String name;
        private final CodeBlock anonymousTypeArguments;
        private final CodeBlock.Builder doc = CodeBlock.builder();
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private final List<Modifier> modifiers = new ArrayList<>();
        private final List<TypeVariableName> typeVariables = new ArrayList<>();
        private TypeName superclass;
        private final List<TypeName> superinterfaces = new ArrayList<>();
        private final Map<String, TypeSpec> enumConstants = new LinkedHashMap<>();
        private final List<FieldSpec> fieldSpecs = new ArrayList<>();
        private final CodeBlock.Builder staticBlock = CodeBlock.builder();
        private final CodeBlock.Builder initializerBlock = CodeBlock.builder();
        private final List<MethodSpec> methodSpecs = new ArrayList<>();
        private final List<TypeSpec> typeSpecs = new ArrayList<>();

        public Builder(Kind kind, String name) {
            this.kind = kind;
            this.name = name;
            this.anonymousTypeArguments = null;
        }

        private Builder(Kind kind, String typeArgumentsFormat, Object... args) {
            this.kind = kind;
            this.name = null;
            this.anonymousTypeArguments = CodeBlock.of(typeArgumentsFormat, args);
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
            checkState(anonymousTypeArguments == null, "forbidden on anonymous types.");
            for (Modifier modifier : modifiers) {
                checkArgument(modifier != null, "modifiers contain null");
                this.modifiers.add(modifier);
            }
            return this;
        }

        public Builder addTypeVariables(Iterable<TypeVariableName> typeVariables) {
            checkState(anonymousTypeArguments == null, "forbidden on anonymous types.");
            checkArgument(typeVariables != null, "typeVariables == null");
            for (TypeVariableName typeVariable : typeVariables) {
                this.typeVariables.add(typeVariable);
            }
            return this;
        }

        public Builder addTypeVariable(TypeVariableName typeVariable) {
            checkState(anonymousTypeArguments == null, "forbidden on anonymous types.");
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
            checkState(kind == Kind.ENUM, "%s is not enum", this.name);
            checkArgument(typeSpec.anonymousTypeArguments != null,
                    "enum constants must have anonymous type arguments");
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
