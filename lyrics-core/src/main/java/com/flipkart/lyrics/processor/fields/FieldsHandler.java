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

package com.flipkart.lyrics.processor.fields;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.annotations.FieldAnnotationHandler;
import com.flipkart.lyrics.processor.methods.GetterHandler;
import com.flipkart.lyrics.processor.methods.SetterHandler;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shrey.garg on 29/11/16.
 */
public class FieldsHandler {
    public Map<String, FieldSpec> process(TypeSpec.Builder typeBuilder, TypeModel typeModel, Tune configuration, Map<String, TypeVariableName> typeVariableNames) {
        Map<String, FieldSpec> fields = new HashMap<>();
        for (String key : typeModel.getFields().keySet()) {
            FieldModel fieldModel = typeModel.getFields().get(key);
            FieldSpec.Builder fieldBuilder = fieldModel.getFieldType().getHandler().process(typeBuilder, key, configuration, fieldModel, typeVariableNames);
            new FieldAnnotationHandler().process(fieldBuilder, fieldModel, configuration);
            FieldSpec fieldSpec = fieldBuilder.build();
            fields.put(key, fieldSpec);
            typeBuilder.addField(fieldSpec);

            if (configuration.areAccessorsNeeded()) {
                generateGetterAndSetter(typeBuilder, fieldSpec, fieldModel);
            }
        }
        return fields;
    }

    public Map<String, FieldSpec> process(TypeSpec.Builder typeBuilder, TypeModel typeModel, Tune configuration) {
        return process(typeBuilder, typeModel, configuration, new HashMap<>());
    }

    private void generateGetterAndSetter(TypeSpec.Builder typeBuilder, FieldSpec fieldSpec, FieldModel fieldModel) {
        typeBuilder.addMethod(new GetterHandler().process(fieldSpec, fieldModel));
        typeBuilder.addMethod(new SetterHandler().process(fieldSpec, fieldModel));
    }
}
