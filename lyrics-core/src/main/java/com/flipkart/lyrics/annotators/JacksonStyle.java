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

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.InclusionType;
import com.flipkart.lyrics.model.SubTypeModel;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.specs.AnnotationSpec;
import com.flipkart.lyrics.specs.FieldSpec;
import com.flipkart.lyrics.specs.MethodSpec;
import com.flipkart.lyrics.specs.TypeSpec;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getClassName;
import static com.flipkart.lyrics.helper.Helper.getRequiredFields;

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

        AnnotationSpec.Builder typeInfoAnnotation = AnnotationSpec.builder(JsonTypeInfo.class)
                .addMember("use", "$L", "JsonTypeInfo.Id.NAME")
                .addMember("include", "$L", "JsonTypeInfo.As." + (subTypeModel.isExistingProperty() ? "EXISTING_PROPERTY" : "PROPERTY"))
                .addMember("property", "$S", subTypeModel.getProperty());

        if (subTypeModel.isVisible()) {
            typeInfoAnnotation.addMember("visible", "$L", subTypeModel.isVisible());
        }

        AnnotationSpec.Builder subTypesBuilder = AnnotationSpec.builder(JsonSubTypes.class);
        for (Map.Entry<String, String> subType : subTypeModel.getSubTypeMapping().entrySet()) {
            subTypesBuilder.addMember("value", "$L",
                    AnnotationSpec.builder(JsonSubTypes.Type.class)
                            .addMember("value", "$T.class", getClassName(subType.getValue()))
                            .addMember("name", "$S", subType.getKey())
                            .build());
        }

        typeSpec.addAnnotation(typeInfoAnnotation.build());
        typeSpec.addAnnotation(subTypesBuilder.build());
    }

    @Override
    public void processIgnoreUnknownsRule(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        AnnotationSpec ignoreUnknowns = AnnotationSpec.builder(JsonIgnoreProperties.class)
                .addMember("ignoreUnknown", "$L", true).build();

        typeSpec.addAnnotation(ignoreUnknowns);
    }

    @Override
    public void processPropertyOrderRule(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        List<String> fieldOrder = typeModel.getFieldOrder();

        AnnotationSpec.Builder propertyOrderBuilder = AnnotationSpec.builder(JsonPropertyOrder.class);
        for (String field : fieldOrder) {
            propertyOrderBuilder.addMember("value", "$S", field);
        }

        typeSpec.addAnnotation(propertyOrderBuilder.build());
    }

    @Override
    public void processConstructorOrderRule(MethodSpec.Builder methodSpec, TypeModel typeModel, List<String> constructorFields) {
        AnnotationSpec.Builder constructorPropertyOrderBuilder = AnnotationSpec.builder(ConstructorProperties.class);
        for (String field : constructorFields) {
            constructorPropertyOrderBuilder.addMember("value", "$S", field);
        }

        methodSpec.addAnnotation(constructorPropertyOrderBuilder.build());
    }
}
