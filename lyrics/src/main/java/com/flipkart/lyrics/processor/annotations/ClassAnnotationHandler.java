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

package com.flipkart.lyrics.processor.annotations;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.AnnotationModel;
import com.flipkart.lyrics.model.TypeModel;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getClassName;
import static com.flipkart.lyrics.helper.Helper.isNullOrEmpty;

/**
 * Created by shrey.garg on 25/11/16.
 */
public class ClassAnnotationHandler {
    public void process(TypeSpec.Builder typeSpec, TypeModel typeModel, Tune configuration) {
        List<AnnotationModel> classAnnotations = consolidateClassAnnotations(configuration, typeModel);
        for (AnnotationModel model : classAnnotations) {
            ClassName annotationName = getClassName(model.getClassName());
            AnnotationSpec.Builder annotationBuilder = AnnotationSpec.builder(annotationName);
            if (!isNullOrEmpty(model.getMembers())) {
                for (Map.Entry<String, String> entry : model.getMembers().entrySet()) {
                    annotationBuilder.addMember(entry.getKey(), entry.getValue());
                }
            }
            typeSpec.addAnnotation(annotationBuilder.build());
        }
    }

    private List<AnnotationModel> consolidateClassAnnotations(Tune configuration, TypeModel typeModel) {
        List<AnnotationModel> classAnnotations = new ArrayList<>();
        if (!isNullOrEmpty(configuration.getClassLevelAnnotations())) {
            configuration.getClassLevelAnnotations().forEach(classAnnotations::add);
        }

        if (!isNullOrEmpty(typeModel.getAnnotations())) {
            typeModel.getAnnotations().forEach(classAnnotations::add);
        }
        return classAnnotations;
    }
}
