package com.flipkart.lyrics.sample.json;

import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.java.specs.*;

import java.util.Map;

/**
 * @author kushal.sharma on 18/08/17.
 */
public class SpecialInterfaces {

    public static void processTestInterface2(TypeSpec.Builder builder, TypeModel typeModel, MetaInfo metaInfo) {
        ParameterSpec.Builder abc = ParameterSpec.builder(TypeName.SHORT, "abc");

        TypeName string = ClassName.get(String.class);
        TypeName person = ClassName.get("com.flipkart.lyrics.sample.userdb.people", "Person");
        ParameterSpec.Builder xyz = ParameterSpec.builder(ParameterizedTypeName.get(ClassName.get(Map.class), string, person), "xyz");

        MethodSpec.Builder one = MethodSpec
                .methodBuilder("one")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addAnnotation(Override.class)
                .addStatement("return null")
                .addParameter(abc.build());

        MethodSpec.Builder two = MethodSpec
                .methodBuilder("two")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addAnnotation(Override.class)
                .addParameter(abc.build())
                .addParameter(xyz.build());

        builder.addMethod(one.build());
        builder.addMethod(two.build());
    }
}
