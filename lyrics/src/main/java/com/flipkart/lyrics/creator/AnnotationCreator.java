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

package com.flipkart.lyrics.creator;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.methods.AnnotationMethodsHandler;
import com.flipkart.lyrics.processor.modifiers.ModifiersHandler;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shrey.garg on 27/11/16.
 */
public class AnnotationCreator implements TypeCreator {
    @Override
    public TypeSpec.Builder process(String name, String fullPackage, TypeModel typeModel, Tune configuration) {
        TypeSpec.Builder typeBuilder = TypeSpec.annotationBuilder(name);
        new ModifiersHandler().process(typeBuilder, typeModel, configuration);
        new AnnotationMethodsHandler().process(typeBuilder, typeModel, configuration);

        if (typeModel.getRetention() != null) {
            typeBuilder.addAnnotation(AnnotationSpec
                    .builder(Retention.class)
                    .addMember("value", "$T.$L", RetentionPolicy.class, typeModel.getRetention())
                    .build());
        }

        if (typeModel.getElementTypes().length != 0) {
            AnnotationSpec.Builder builder = AnnotationSpec.builder(Target.class);
            for (ElementType elementType : typeModel.getElementTypes()) {
                builder.addMember("value", "$T.$L", ElementType.class, elementType);
            }
            typeBuilder.addAnnotation(builder.build());
        }

        return typeBuilder;
    }
}
