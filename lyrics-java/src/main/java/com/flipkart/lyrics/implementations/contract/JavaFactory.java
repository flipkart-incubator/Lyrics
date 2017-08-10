package com.flipkart.lyrics.implementations.contract;

import com.flipkart.lyrics.Modifier;
import com.flipkart.lyrics.implementations.JavaAnnotationSpec;
import com.flipkart.lyrics.implementations.JavaMethodSpec;
import com.flipkart.lyrics.interfaces.*;
import com.flipkart.lyrics.interfaces.contract.Factory;
import com.flipkart.lyrics.interfaces.model.Kind;
import com.flipkart.lyrics.interfaces.model.TypeName;

/**
 * @author kushal.sharma on 09/08/17.
 */
public class JavaFactory implements Factory {
    public MethodSpec.Builder createConstructorBuilder() {
        return new JavaMethodSpec.Builder();
    }

    public MethodSpec.Builder createMethodBuilder(String name) {
        return new JavaMethodSpec.Builder(name);
    }

    @Override
    public AnnotationSpec.Builder createAnnotation(Class annotationClass) {
        return new JavaAnnotationSpec.Builder(annotationClass);
    }

    @Override
    public TypeSpec.Builder createClassBuilder(Kind kind, String name) {
        return null;
    }

    @Override
    public TypeSpec.Builder createAnnotationBuilder(Kind kind, String name) {
        return null;
    }

    @Override
    public TypeSpec.Builder createInterfaceBuilder(Kind kind, String name) {
        return null;
    }

    @Override
    public TypeSpec.Builder createEnumBuilder(Kind kind, String name) {
        return null;
    }

    @Override
    public TypeSpec.Builder createAnonymousClassBuilder(String name) {
        return null;
    }

    @Override
    public FieldSpec.Builder createFieldBuilder(TypeName typeName, String name, Modifier[] modifiers) {
        return null;
    }

    @Override
    public ParameterSpec.Builder createParameterBuilder(TypeName typeName, String name, Modifier[] modifiers) {
        return null;
    }
}
