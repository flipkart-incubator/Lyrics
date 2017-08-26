package com.flipkart.lyrics.java.specs.contract;

import com.flipkart.lyrics.java.specs.*;

/**
 * @author kushal.sharma on 09/08/17.
 */
public class JavaFactory implements Factory {
    @Override
    public MethodSpec.Builder createConstructorBuilder() {
        return new JavaMethodSpec.Builder();
    }

    @Override
    public MethodSpec.Builder createMethodBuilder(String name) {
        return new JavaMethodSpec.Builder(name);
    }

    @Override
    public AnnotationSpec.Builder createAnnotationBuilder(ClassName className) {
        return new JavaAnnotationSpec.Builder(className);
    }

    @Override
    public TypeSpec.Builder createClassBuilder(String name) {
        return new JavaTypeSpec.Builder(Kind.CLASS, name);
    }

    @Override
    public TypeSpec.Builder createAnnotationBuilder(String name) {
        return new JavaTypeSpec.Builder(Kind.ANNOTATION, name);
    }

    @Override
    public TypeSpec.Builder createInterfaceBuilder(String name) {
        return new JavaTypeSpec.Builder(Kind.INTERFACE, name);
    }

    @Override
    public TypeSpec.Builder createEnumBuilder(String name) {
        return new JavaTypeSpec.Builder(Kind.ENUM, name);
    }

    @Override
    public TypeSpec.Builder createAnonymousClassBuilder(String typeArgumentsFormat, Object... args) {
        return new JavaTypeSpec.Builder(Kind.ANONYMOUS, typeArgumentsFormat, args);
    }

    @Override
    public FieldSpec.Builder createFieldBuilder(TypeName typeName, String name, Modifier[] modifiers) {
        return new JavaFieldSpec.Builder(typeName, name, modifiers);
    }

    @Override
    public ParameterSpec.Builder createParameterBuilder(TypeName typeName, String name, Modifier[] modifiers) {
        return new JavaParameterSpec.Builder(typeName, name, modifiers);
    }
}
