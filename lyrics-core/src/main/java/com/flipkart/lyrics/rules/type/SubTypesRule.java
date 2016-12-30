/*
 * Copyright 2016 Flipkart Internet, pvt ltd.
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

package com.flipkart.lyrics.rules.type;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.SubTypeModel;
import com.flipkart.lyrics.model.TypeModel;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getClassName;

/**
 * Created by shrey.garg on 30/11/16.
 */
public class SubTypesRule implements TypeRule {
    @Override
    public void process(TypeSpec.Builder typeSpec, TypeModel typeModel, Tune configuration) {
        if (typeModel.getSubTypes() == null) {
            return;
        }

        SubTypeModel subTypeModel = typeModel.getSubTypes();
        if (configuration.areJacksonStyleAnnotationsNeeded()) {
            AnnotationSpec typeInfoAnnotation = AnnotationSpec.builder(JsonTypeInfo.class)
                    .addMember("use", "$L", "JsonTypeInfo.Id.NAME")
                    .addMember("include", "$L", "JsonTypeInfo.As.PROPERTY")
                    .addMember("property", "$S", subTypeModel.getProperty())
                    .build();

            AnnotationSpec.Builder subTypesBuilder = AnnotationSpec.builder(JsonSubTypes.class);
            for (Map.Entry<String, String> subType : subTypeModel.getSubTypeMapping().entrySet()) {
                subTypesBuilder.addMember("value", "$L",
                        AnnotationSpec.builder(JsonSubTypes.Type.class)
                                .addMember("value", "$T.class", getClassName(subType.getValue()))
                                .addMember("name", "$S", subType.getKey())
                                .build());
            }

            typeSpec.addAnnotation(typeInfoAnnotation);
            typeSpec.addAnnotation(subTypesBuilder.build());
        }
    }
}
