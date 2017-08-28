package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.helper.Util;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class TypeSpec {
    public final Kind kind;
    public final String name;
    public final List<TypeSpec> typeSpecs = new ArrayList<>();
    public final Set<Modifier> modifiers = new HashSet<>();
    public final List<TypeName> superinterfaces = new ArrayList<>();
    public final List<FieldSpec> fieldSpecs = new ArrayList<>();
    public final List<MethodSpec> methodSpecs = new ArrayList<>();
    public final List<AnnotationSpec> annotations = new ArrayList<>();
    public final List<TypeVariableName> typeVariables = new ArrayList<>();
    public final Map<String, TypeSpec> enumConstants = new HashMap<>();
    public final TypeName superclass;
    public final CodeBlock anonymousTypeArguments;

    public TypeSpec(Builder builder) {
        this.kind = builder.kind;
        this.name = builder.name;
        this.typeSpecs.addAll(builder.types);
        this.superclass = builder.superclass;
        this.modifiers.addAll(builder.modifiers);
        this.superinterfaces.addAll(builder.superinterfaces);
        this.fieldSpecs.addAll(builder.fieldSpecs);
        this.methodSpecs.addAll(builder.methodSpecs);
        this.anonymousTypeArguments = builder.anonymousTypeArguments;
        this.annotations.addAll(builder.annotations);
        this.enumConstants.putAll(builder.enumConstants);
        this.typeVariables.addAll(builder.typeVariables);
    }

    public static Builder classBuilder(String name) {
        return Song.factory.createClassBuilder(name);
    }

    public static Builder annotationBuilder(String name) {
        return Song.factory.createAnnotationBuilder(name);
    }

    public static Builder interfaceBuilder(String name) {
        return Song.factory.createInterfaceBuilder(name);
    }

    public static Builder enumBuilder(String name) {
        return Song.factory.createEnumBuilder(name);
    }

    public static Builder anonymousClassBuilder(String typeArgumentsFormat, Object... args) {
        return Song.factory.createAnonymousClassBuilder(typeArgumentsFormat, args);
    }

    public Object getTypeSpec() {
        return null;
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

    public void writeToFile(String fullPackage, File targetFolder) {
    }

    public static abstract class Builder {
        private final Kind kind;
        private final String name;
        private final List<TypeSpec> types = new ArrayList<>();
        private final Set<Modifier> modifiers = new HashSet<>();
        private final List<TypeName> superinterfaces = new ArrayList<>();
        private final List<FieldSpec> fieldSpecs = new ArrayList<>();
        private final List<MethodSpec> methodSpecs = new ArrayList<>();
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private final List<TypeVariableName> typeVariables = new ArrayList<>();
        private final Map<String, TypeSpec> enumConstants = new HashMap<>();
        private TypeName superclass;
        private CodeBlock anonymousTypeArguments;

        public Builder(Kind kind, String name) {
            this.kind = kind;
            this.name = name;
        }

        public Builder(Kind kind, String typeArgumentsFormat, Object... args) {
            this.kind = kind;
            this.name = null;
            this.anonymousTypeArguments = CodeBlock.of(typeArgumentsFormat, args);
        }

        public TypeSpec.Builder addField(FieldSpec fieldSpec) {
            this.fieldSpecs.add(fieldSpec);
            return this;
        }

        public TypeSpec.Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotations.add(annotationSpec);
            return this;
        }

        public TypeSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotations.add(AnnotationSpec.builder(clazz).build());
            return this;
        }

        public TypeSpec.Builder addMethod(MethodSpec methodSpec) {
            this.methodSpecs.add(methodSpec);
            return this;
        }

        public TypeSpec.Builder addModifiers(Modifier... modifiers) {
            this.modifiers.addAll(Arrays.asList(modifiers));
            return this;
        }

        public TypeSpec.Builder superclass(TypeName superclass) {
            this.superclass = superclass;
            return this;
        }

        public TypeSpec.Builder addEnumConstant(String key) {
            this.enumConstants.put(key, null);
            return this;
        }

        public TypeSpec.Builder addEnumConstant(String key, TypeSpec typeSpec) {
            this.enumConstants.put(key, typeSpec);
            return this;
        }

        public TypeSpec.Builder addSuperinterfaces(List<TypeName> superinterfaces) {
            this.superinterfaces.addAll(superinterfaces);
            return this;
        }

        public TypeSpec.Builder addType(TypeSpec typeSpec) {
            this.types.add(typeSpec);
            return this;
        }

        public TypeSpec.Builder addTypeVariable(TypeVariableName typeVariables) {
            this.typeVariables.add(typeVariables);
            return this;
        }

        public abstract TypeSpec build();
    }
}
