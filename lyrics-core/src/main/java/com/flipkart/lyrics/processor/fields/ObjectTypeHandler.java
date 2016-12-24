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
import com.squareup.javapoet.*;

import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getResolvedTypeName;
import static com.flipkart.lyrics.helper.Helper.resolveModifiers;

/**
 * Created by shrey.garg on 25/11/16.
 */
public class ObjectTypeHandler extends FieldTypeHandler {
    @Override
    public FieldSpec.Builder process(TypeSpec.Builder typeSpec, String key, Tune configuration, FieldModel fieldModel, Map<String, TypeVariableName> typeVariableNames) {
        TypeName typeName;
        if (fieldModel.getType() == null) {
            typeName = TypeName.OBJECT;
        } else {
            typeName = getResolvedTypeName(fieldModel.getType(), typeVariableNames);
        }

        typeName = fieldModel.isArray() ? ArrayTypeName.of(typeName) : typeName;
        return FieldSpec.builder(typeName, key, resolveModifiers(configuration, fieldModel));
    }
}
