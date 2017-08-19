package com.flipkart.lyrics.interfaces.contract;

import com.flipkart.lyrics.interfaces.*;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

/**
 * @author kushal.sharma on 09/08/17.
 */
public interface Factory {
    MethodSpec.Builder createConstructorBuilder();

    MethodSpec.Builder createMethodBuilder(String name);

    AnnotationSpec.Builder createAnnotationBuilder(Class clazz);

    AnnotationSpec.Builder createAnnotationBuilder(ClassName className);

    TypeSpec.Builder createClassBuilder(String name);

    TypeSpec.Builder createAnnotationBuilder(String name);

    TypeSpec.Builder createInterfaceBuilder(String name);

    TypeSpec.Builder createEnumBuilder(String name);

    TypeSpec.Builder createAnonymousClassBuilder(String typeArgumentsFormat, Object... args);

    FieldSpec.Builder createFieldBuilder(TypeName typeName, String name, Modifier[] modifiers);

    ParameterSpec.Builder createParameterBuilder(TypeName typeName, String name, Modifier[] modifiers);

    ParameterSpec.Builder createParameterBuilder(Class<?> clazz, String name, Modifier[] modifiers);
}
