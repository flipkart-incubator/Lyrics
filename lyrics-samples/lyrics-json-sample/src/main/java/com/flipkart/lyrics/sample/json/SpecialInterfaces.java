/*
 * Copyright 2017 Flipkart Internet, pvt ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.lyrics.sample.json;

import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.specs.*;

import java.util.Map;

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
