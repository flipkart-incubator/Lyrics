package com.flipkart.lyrics;

import com.flipkart.lyrics.interfaces.*;
import com.flipkart.lyrics.interfaces.contract.Factory;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Kind;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

/**
 * @author kushal.sharma on 23/08/17.
 */
public class TestFactory implements Factory {
    @Override
    public MethodSpec.Builder createConstructorBuilder() {
        return new TestMethodSpec.Builder();
    }

    @Override
    public MethodSpec.Builder createMethodBuilder(String name) {
        return new TestMethodSpec.Builder(name);
    }

    @Override
    public AnnotationSpec.Builder createAnnotationBuilder(Class clazz) {
        return new TestAnnotationSpec.Builder(clazz);
    }

    @Override
    public AnnotationSpec.Builder createAnnotationBuilder(ClassName className) {
        return new TestAnnotationSpec.Builder(className);
    }

    @Override
    public TypeSpec.Builder createClassBuilder(String name) {
        return new TestTypeSpec.Builder(Kind.CLASS, name);
    }

    @Override
    public TypeSpec.Builder createAnnotationBuilder(String name) {
        return new TestTypeSpec.Builder(Kind.ANNOTATION, name);
    }

    @Override
    public TypeSpec.Builder createInterfaceBuilder(String name) {
        return new TestTypeSpec.Builder(Kind.INTERFACE, name);
    }

    @Override
    public TypeSpec.Builder createEnumBuilder(String name) {
        return new TestTypeSpec.Builder(Kind.ENUM, name);
    }

    @Override
    public TypeSpec.Builder createAnonymousClassBuilder(String typeArgumentsFormat, Object... args) {
        return new TestTypeSpec.Builder(Kind.ANONYMOUS, typeArgumentsFormat, args);
    }

    @Override
    public FieldSpec.Builder createFieldBuilder(TypeName typeName, String name, Modifier[] modifiers) {
        return new TestFieldSpec.Builder(typeName, name, modifiers);
    }

    @Override
    public FieldSpec.Builder createFieldBuilder(Class<?> clazz, String name, Modifier[] modifiers) {
        return new TestFieldSpec.Builder(clazz, name, modifiers);
    }

    @Override
    public ParameterSpec.Builder createParameterBuilder(TypeName typeName, String name, Modifier[] modifiers) {
        return new TestParameterSpec.Builder(typeName, name, modifiers);
    }

    @Override
    public ParameterSpec.Builder createParameterBuilder(Class<?> clazz, String name, Modifier[] modifiers) {
        return new TestParameterSpec.Builder(clazz, name, modifiers);
    }
}
