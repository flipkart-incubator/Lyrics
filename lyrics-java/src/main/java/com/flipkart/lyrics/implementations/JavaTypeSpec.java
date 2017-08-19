package com.flipkart.lyrics.implementations;

import com.flipkart.lyrics.interfaces.AnnotationSpec;
import com.flipkart.lyrics.interfaces.FieldSpec;
import com.flipkart.lyrics.interfaces.MethodSpec;
import com.flipkart.lyrics.interfaces.TypeSpec;
import com.flipkart.lyrics.interfaces.model.KeyTypeSpec;
import com.flipkart.lyrics.interfaces.model.TypeArgsFormatArgs;
import com.flipkart.lyrics.interfaces.typenames.Kind;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;
import com.flipkart.lyrics.interfaces.typenames.TypeVariableName;
import com.squareup.javapoet.JavaFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.flipkart.lyrics.helper.JavaHelper.*;

/**
 * @author kushal.sharma on 11/08/17.
 */
public class JavaTypeSpec extends TypeSpec {
    private com.squareup.javapoet.TypeSpec.Builder builder;

    @Override
    public Object getTypeSpec() {
        return builder.build();
    }

    public JavaTypeSpec(Builder builder) {
        switch (builder.kind) {
            case CLASS:
                this.builder = com.squareup.javapoet.TypeSpec.classBuilder(builder.name);
                break;
            case INTERFACE:
                this.builder = com.squareup.javapoet.TypeSpec.interfaceBuilder(builder.name);
                break;
            case ENUM:
                this.builder = com.squareup.javapoet.TypeSpec.enumBuilder(builder.name);
                break;
            case ANNOTATION:
                this.builder = com.squareup.javapoet.TypeSpec.annotationBuilder(builder.name);
                break;
            case ANONYMOUS:
                this.builder = com.squareup.javapoet.TypeSpec.anonymousClassBuilder(builder.typeArgsFormatArgs.getTypeArgsFormat(), builder.typeArgsFormatArgs.getArgs());
                break;
            default:
                this.builder = com.squareup.javapoet.TypeSpec.classBuilder(builder.name);
        }
        if (builder.extendsType != null) this.builder.superclass(getJavaTypeName(builder.extendsType));
        for (FieldSpec fieldSpec : builder.fieldSpecs) {
            this.builder.addField((com.squareup.javapoet.FieldSpec) fieldSpec.getFieldSpec());
        }
        for (AnnotationSpec annotationSpec : builder.annotationSpecs) {
            this.builder.addAnnotation((com.squareup.javapoet.AnnotationSpec) annotationSpec.getAnnotationSpec());
        }
        for (Class<?> clazz : builder.annotationClasses) {
            this.builder.addAnnotation(clazz);
        }
        for (MethodSpec methodSpec : builder.methodSpecs) {
            this.builder.addMethod((com.squareup.javapoet.MethodSpec) methodSpec.getMethodSpec());
        }
        for (Modifier modifier : builder.modifiers) {
            this.builder.addModifiers(getJavaModifier(modifier));
        }
        for (String key : builder.enumKeys) {
            this.builder.addEnumConstant(key);
        }
        for (KeyTypeSpec keyTypeSpec : builder.enumKeyTypeSpecs) {
            this.builder.addEnumConstant(keyTypeSpec.getKey(), (com.squareup.javapoet.TypeSpec) keyTypeSpec.getTypeSpec().getTypeSpec());
        }
        for (TypeName superinterface : builder.interfaces) {
            this.builder.addSuperinterface(getJavaTypeName(superinterface));
        }
        for (TypeSpec typeSpec : builder.types) {
            this.builder.addType((com.squareup.javapoet.TypeSpec) typeSpec.getTypeSpec());
        }
        for (TypeVariableName typeVariableName : builder.typeVariableNames) {
            this.builder.addTypeVariable(getJavaTypeVariableName(typeVariableName));
        }
    }

    @Override
    public void writeToFile(String fullPackage, File targetFolder) {
        JavaFile javaFile = JavaFile.builder(fullPackage, builder.build())
                .indent("    ")
                .skipJavaLangImports(true)
                .build();
        try {
            javaFile.writeTo(targetFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final class Builder extends TypeSpec.Builder {
        private TypeName extendsType;
        private TypeArgsFormatArgs typeArgsFormatArgs;
        private List<FieldSpec> fieldSpecs = new ArrayList<>();
        private List<AnnotationSpec> annotationSpecs = new ArrayList<>();
        private List<Class<?>> annotationClasses = new ArrayList<>();
        private List<MethodSpec> methodSpecs = new ArrayList<>();
        private List<Modifier> modifiers = new ArrayList<>();
        private List<String> enumKeys = new ArrayList<>();
        private List<KeyTypeSpec> enumKeyTypeSpecs = new ArrayList<>();
        private List<TypeName> interfaces = new ArrayList<>();
        private List<TypeSpec> types = new ArrayList<>();
        private List<TypeVariableName> typeVariableNames = new ArrayList<>();

        public Builder(Kind kind, String name) {
            super(kind, name);
        }

        public Builder(Kind kind, String typeArgumentsFormat, Object... args) {
            super(kind, null);
            this.typeArgsFormatArgs = new TypeArgsFormatArgs(typeArgumentsFormat, args);
        }

        @Override
        public TypeSpec.Builder addField(FieldSpec fieldSpec) {
            this.fieldSpecs.add(fieldSpec);
            return this;
        }

        @Override
        public TypeSpec.Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotationSpecs.add(annotationSpec);
            return this;
        }

        @Override
        public TypeSpec.Builder addAnnotation(Class<?> clazz) {
            this.annotationClasses.add(clazz);
            return this;
        }

        @Override
        public TypeSpec.Builder addMethod(MethodSpec methodSpec) {
            this.methodSpecs.add(methodSpec);
            return this;
        }

        @Override
        public TypeSpec.Builder addModifiers(Modifier... modifiers) {
            this.modifiers.addAll(Arrays.asList(modifiers));
            return this;
        }

        @Override
        public TypeSpec.Builder superclass(TypeName extendsType) {
            this.extendsType = extendsType;
            return this;
        }

        @Override
        public TypeSpec.Builder addEnumConstant(String key) {
            this.enumKeys.add(key);
            return this;
        }

        @Override
        public TypeSpec.Builder addEnumConstant(String key, TypeSpec typeSpec) {
            this.enumKeyTypeSpecs.add(new KeyTypeSpec(key, typeSpec));
            return this;
        }

        @Override
        public TypeSpec.Builder addSuperinterfaces(List<TypeName> interfaces) {
            this.interfaces.addAll(interfaces);
            return this;
        }

        @Override
        public TypeSpec.Builder addType(TypeSpec typeSpec) {
            this.types.add(typeSpec);
            return this;
        }

        @Override
        public TypeSpec.Builder addTypeVariable(TypeVariableName typeVariableName) {
            this.typeVariableNames.add(typeVariableName);
            return this;
        }

        @Override
        public TypeSpec build() {
            return new JavaTypeSpec(this);
        }
    }
}
