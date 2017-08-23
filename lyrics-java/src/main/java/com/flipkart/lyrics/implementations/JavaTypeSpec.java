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
    public final com.squareup.javapoet.TypeSpec.Builder builder;

    JavaTypeSpec(Builder builder) {
        super(builder);

        switch (this.kind) {
            case CLASS:
                this.builder = com.squareup.javapoet.TypeSpec.classBuilder(this.name);
                break;
            case INTERFACE:
                this.builder = com.squareup.javapoet.TypeSpec.interfaceBuilder(this.name);
                break;
            case ENUM:
                this.builder = com.squareup.javapoet.TypeSpec.enumBuilder(this.name);
                break;
            case ANNOTATION:
                this.builder = com.squareup.javapoet.TypeSpec.annotationBuilder(this.name);
                break;
            case ANONYMOUS:
                this.builder = com.squareup.javapoet.TypeSpec.anonymousClassBuilder(this.typeArgsFormatArgs.getTypeArgsFormat(), this.typeArgsFormatArgs.getArgs());
                break;
            default:
                this.builder = com.squareup.javapoet.TypeSpec.classBuilder(this.name);
        }

        if (this.extendsType != null) this.builder.superclass(getJavaTypeName(this.extendsType));
        for (FieldSpec fieldSpec : this.fieldSpecs) {
            this.builder.addField((com.squareup.javapoet.FieldSpec) fieldSpec.getFieldSpec());
        }
        for (AnnotationSpec annotationSpec : this.annotationSpecs) {
            this.builder.addAnnotation((com.squareup.javapoet.AnnotationSpec) annotationSpec.getAnnotationSpec());
        }
        for (Class<?> clazz : this.annotationClasses) {
            this.builder.addAnnotation(clazz);
        }
        for (MethodSpec methodSpec : this.methodSpecs) {
            this.builder.addMethod((com.squareup.javapoet.MethodSpec) methodSpec.getMethodSpec());
        }
        for (Modifier modifier : this.modifiers) {
            this.builder.addModifiers(getJavaModifier(modifier));
        }
        for (String key : this.enumKeys) {
            this.builder.addEnumConstant(key);
        }
        for (KeyTypeSpec keyTypeSpec : this.enumKeyTypeSpecs) {
            this.builder.addEnumConstant(keyTypeSpec.getKey(), (com.squareup.javapoet.TypeSpec) keyTypeSpec.getTypeSpec().getTypeSpec());
        }
        for (TypeName superinterface : this.interfaces) {
            this.builder.addSuperinterface(getJavaTypeName(superinterface));
        }
        for (TypeSpec typeSpec : this.types) {
            this.builder.addType((com.squareup.javapoet.TypeSpec) typeSpec.getTypeSpec());
        }
        for (TypeVariableName typeVariableName : this.typeVariableNames) {
            this.builder.addTypeVariable(getJavaTypeVariableName(typeVariableName));
        }
    }

    @Override
    public Object getTypeSpec() {
        return builder.build();
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
        public Builder(Kind kind, String name) {
            super(kind, name);
        }

        public Builder(Kind kind, String typeArgumentsFormat, Object... args) {
            super(kind, typeArgumentsFormat, args);
        }

        @Override
        public TypeSpec build() {
            return new JavaTypeSpec(this);
        }
    }
}
