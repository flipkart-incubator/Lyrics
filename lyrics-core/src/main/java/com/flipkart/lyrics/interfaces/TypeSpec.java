package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.model.KeyTypeSpec;
import com.flipkart.lyrics.interfaces.model.TypeArgsFormatArgs;
import com.flipkart.lyrics.interfaces.typenames.Kind;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;
import com.flipkart.lyrics.interfaces.typenames.TypeVariableName;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class TypeSpec {
    public final Kind kind;
    public final String name;
    public TypeName extendsType;
    public TypeArgsFormatArgs typeArgsFormatArgs;
    public final List<TypeSpec> types = new ArrayList<>();
    public final List<String> enumKeys = new ArrayList<>();
    public final List<Modifier> modifiers = new ArrayList<>();
    public final List<TypeName> interfaces = new ArrayList<>();
    public final List<FieldSpec> fieldSpecs = new ArrayList<>();
    public final List<MethodSpec> methodSpecs = new ArrayList<>();
    public final List<Class<?>> annotationClasses = new ArrayList<>();
    public final List<KeyTypeSpec> enumKeyTypeSpecs = new ArrayList<>();
    public final List<AnnotationSpec> annotationSpecs = new ArrayList<>();
    public final List<TypeVariableName> typeVariableNames = new ArrayList<>();

    public TypeSpec(Builder builder) {
        this.kind = builder.kind;
        this.name = builder.name;
        this.types.addAll(builder.types);
        this.extendsType = builder.extendsType;
        this.enumKeys.addAll(builder.enumKeys);
        this.modifiers.addAll(builder.modifiers);
        this.interfaces.addAll(builder.interfaces);
        this.fieldSpecs.addAll(builder.fieldSpecs);
        this.methodSpecs.addAll(builder.methodSpecs);
        this.typeArgsFormatArgs = builder.typeArgsFormatArgs;
        this.annotationSpecs.addAll(builder.annotationSpecs);
        this.enumKeyTypeSpecs.addAll(builder.enumKeyTypeSpecs);
        this.annotationClasses.addAll(builder.annotationClasses);
        this.typeVariableNames.addAll(builder.typeVariableNames);
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
        private TypeName extendsType;
        private TypeArgsFormatArgs typeArgsFormatArgs;
        private final List<TypeSpec> types = new ArrayList<>();
        private final List<String> enumKeys = new ArrayList<>();
        private final List<Modifier> modifiers = new ArrayList<>();
        private final List<TypeName> interfaces = new ArrayList<>();
        private final List<FieldSpec> fieldSpecs = new ArrayList<>();
        private final List<MethodSpec> methodSpecs = new ArrayList<>();
        private final List<Class<?>> annotationClasses = new ArrayList<>();
        private final List<KeyTypeSpec> enumKeyTypeSpecs = new ArrayList<>();
        private final List<AnnotationSpec> annotationSpecs = new ArrayList<>();
        private final List<TypeVariableName> typeVariableNames = new ArrayList<>();

        public Builder(Kind kind, String name) {
            this.kind = kind;
            this.name = name;
        }

        public Builder(Kind kind, String typeArgumentsFormat, Object... args) {
            this.kind = kind;
            this.name = null;
            this.typeArgsFormatArgs = new TypeArgsFormatArgs(typeArgumentsFormat, args);
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
            this.annotationClasses.add(clazz);
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

        public TypeSpec.Builder superclass(TypeName extendsType) {
            this.extendsType = extendsType;
            return this;
        }

        public TypeSpec.Builder addEnumConstant(String key) {
            this.enumKeys.add(key);
            return this;
        }

        public TypeSpec.Builder addEnumConstant(String key, TypeSpec typeSpec) {
            this.enumKeyTypeSpecs.add(new KeyTypeSpec(key, typeSpec));
            return this;
        }

        public TypeSpec.Builder addSuperinterfaces(List<TypeName> interfaces) {
            this.interfaces.addAll(interfaces);
            return this;
        }

        public TypeSpec.Builder addType(TypeSpec typeSpec) {
            this.types.add(typeSpec);
            return this;
        }

        public TypeSpec.Builder addTypeVariable(TypeVariableName typeVariableName) {
            this.typeVariableNames.add(typeVariableName);
            return this;
        }

        public abstract TypeSpec build();
    }
}
