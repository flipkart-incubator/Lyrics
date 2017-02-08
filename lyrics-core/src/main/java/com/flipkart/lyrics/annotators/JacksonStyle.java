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

package com.flipkart.lyrics.annotators;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.InclusionType;
import com.flipkart.lyrics.model.SubTypeModel;
import com.flipkart.lyrics.model.TypeModel;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getClassName;

/**
 * Created by shrey.garg on 03/01/17.
 */
public class JacksonStyle extends AnnotatorStyle {

    @Override
    public void processNamedAsRule(FieldSpec.Builder fieldSpec, FieldModel fieldModel) {
        String jsonProperty = fieldModel.getNamedAs();
        AnnotationSpec annotationSpec = AnnotationSpec.builder(JsonProperty.class)
                .addMember("value", "$S", jsonProperty)
                .build();
        fieldSpec.addAnnotation(annotationSpec);
    }

    @Override
    public void processInclusionRule(FieldSpec.Builder fieldSpec, FieldModel fieldModel) {
        InclusionType inclusion = fieldModel.getInclusion();
        AnnotationSpec annotationSpec = AnnotationSpec.builder(JsonSerialize.class)
                .addMember("include", "JsonSerialize.Inclusion.$L", inclusion)
                .build();
        fieldSpec.addAnnotation(annotationSpec);
    }

    @Override
    public void processGlobalInclusionRule(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        AnnotationSpec annotationSpec = AnnotationSpec.builder(JsonSerialize.class)
                .addMember("include", "JsonSerialize.Inclusion.$L", typeModel.getInclusion())
                .build();
        typeSpec.addAnnotation(annotationSpec);

    }

    @Override
    public void processSubTypeRule(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        SubTypeModel subTypeModel = typeModel.getSubTypes();

        AnnotationSpec typeInfoAnnotation = AnnotationSpec.builder(JsonTypeInfo.class)
                .addMember("use", "$L", "JsonTypeInfo.Id.NAME")
                .addMember("include", "$L", "JsonTypeInfo.As." + (subTypeModel.isExistingProperty() ? "EXISTING_PROPERTY" : "PROPERTY"))
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
