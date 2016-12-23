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

package com.flipkart.lyrics.rules;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.InclusionType;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;

/**
 * Created by shrey.garg on 26/11/16.
 */
public class InclusionRule implements FieldAnnotationRule {
    @Override
    public void process(FieldSpec.Builder fieldSpec, FieldModel fieldModel, Tune configuration) {
        if (fieldModel.getInclusion() == null) {
            return;
        }

        InclusionType inclusion = fieldModel.getInclusion();

        if (configuration.areJacksonStyleAnnotationsNeeded()) {
            AnnotationSpec annotationSpec = AnnotationSpec.builder(JsonSerialize.class)
                    .addMember("include", "JsonSerialize.Inclusion.$L", inclusion)
                    .build();
            fieldSpec.addAnnotation(annotationSpec);
        }

        if (configuration.areGsonStyleAnnotationsNeeded()) {

        }
    }
}
