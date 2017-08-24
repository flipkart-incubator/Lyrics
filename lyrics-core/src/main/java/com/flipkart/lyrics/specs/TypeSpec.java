package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.Song;

import java.io.File;
import java.util.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class TypeSpec {
    public final Kind kind;
    public final String name;
    public TypeName superclass;
    public CodeBlock anonymousTypeArguments;
    public final List<TypeSpec> types = new ArrayList<>();
    public final List<Modifier> modifiers = new ArrayList<>();
    public final List<TypeName> superinterfaces = new ArrayList<>();
    public final List<FieldSpec> fieldSpecs = new ArrayList<>();
    public final List<MethodSpec> methodSpecs = new ArrayList<>();
    public final List<AnnotationSpec> annotationSpecs = new ArrayList<>();
    public final List<TypeVariableName> typeVariables = new ArrayList<>();
    public final Map<String, TypeSpec> enumConstants = new HashMap<>();

    public TypeSpec(Builder builder) {
        this.kind = builder.kind;
        this.name = builder.name;
        this.types.addAll(builder.types);
        this.superclass = builder.superclass;
        this.modifiers.addAll(builder.modifiers);
        this.superinterfaces.addAll(builder.superinterfaces);
        this.fieldSpecs.addAll(builder.fieldSpecs);
        this.methodSpecs.addAll(builder.methodSpecs);
        this.anonymousTypeArguments = builder.anonymousTypeArguments;
        this.annotationSpecs.addAll(builder.annotationSpecs);
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

    public void writeToFile(String fullPackage, File targetFolder) { }

    public static abstract class Builder {
        private final Kind kind;
        private final String name;
        private TypeName superclass;
        private CodeBlock anonymousTypeArguments;
        private final List<TypeSpec> types = new ArrayList<>();
        private final List<Modifier> modifiers = new ArrayList<>();
        private final List<TypeName> superinterfaces = new ArrayList<>();
        private final List<FieldSpec> fieldSpecs = new ArrayList<>();
        private final List<MethodSpec> methodSpecs = new ArrayList<>();
        private final List<AnnotationSpec> annotationSpecs = new ArrayList<>();
        private final List<TypeVariableName> typeVariables = new ArrayList<>();
        private final Map<String, TypeSpec> enumConstants = new HashMap<>();

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
            this.annotationSpecs.add(annotationSpec);
            return this;
        }

        public TypeSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotationSpecs.add(AnnotationSpec.builder(clazz).build());
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
