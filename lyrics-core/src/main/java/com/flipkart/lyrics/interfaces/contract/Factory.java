package com.flipkart.lyrics.interfaces.contract;

import com.flipkart.lyrics.Modifier;
import com.flipkart.lyrics.interfaces.*;
import com.flipkart.lyrics.interfaces.model.Kind;
import com.flipkart.lyrics.interfaces.model.TypeName;

/**
 * @author kushal.sharma on 09/08/17.
 */
public interface Factory {
    MethodSpec.Builder createConstructorBuilder();

    MethodSpec.Builder createMethodBuilder(String name);

    AnnotationSpec.Builder createAnnotation(Class annotationClass);

    TypeSpec.Builder createClassBuilder(Kind kind, String name);

    TypeSpec.Builder createAnnotationBuilder(Kind kind, String name);

    TypeSpec.Builder createInterfaceBuilder(Kind kind, String name);

    TypeSpec.Builder createEnumBuilder(Kind kind, String name);

    TypeSpec.Builder createAnonymousClassBuilder(String name);

    FieldSpec.Builder createFieldBuilder(TypeName typeName, String name, Modifier[] modifiers);

    ParameterSpec.Builder createParameterBuilder(TypeName typeName, String name, Modifier[] modifiers);
}
