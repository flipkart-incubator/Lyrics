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

import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.specs.AnnotationSpec;
import com.flipkart.lyrics.specs.FieldSpec;
import com.flipkart.lyrics.specs.MethodSpec;
import com.flipkart.lyrics.specs.TypeSpec;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.beans.ConstructorProperties;
import java.util.List;

/**
 * Created by shrey.garg on 03/01/17.
 */
public class GsonStyle extends AnnotatorStyle {

    @Override
    public void processNamedAsRule(FieldSpec.Builder fieldSpec, FieldModel fieldModel) {
        String jsonProperty = fieldModel.getNamedAs();
        AnnotationSpec annotationSpec = AnnotationSpec.builder(SerializedName.class)
                .addMember("value", "$S", jsonProperty)
                .build();
        fieldSpec.addAnnotation(annotationSpec);
        fieldSpec.addAnnotation(Expose.class);
    }

    @Override
    public void processInclusionRule(FieldSpec.Builder fieldSpec, FieldModel fieldModel) {

    }

    @Override
    public void processGlobalInclusionRule(TypeSpec.Builder typeSpec, TypeModel typeModel) {

    }

    @Override
    public void processSubTypeRule(TypeSpec.Builder typeSpec, TypeModel typeModel) {

    }

    @Override
    public void processIgnoreUnknownsRule(TypeSpec.Builder typeSpec, TypeModel typeModel) {

    }

    @Override
    public void processPropertyOrderRule(TypeSpec.Builder typeSpec, TypeModel typeModel) {

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
