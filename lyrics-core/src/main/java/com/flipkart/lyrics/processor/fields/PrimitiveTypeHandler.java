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
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.Primitive;
import com.flipkart.lyrics.specs.ArrayTypeName;
import com.flipkart.lyrics.specs.FieldSpec;
import com.flipkart.lyrics.specs.TypeName;
import com.flipkart.lyrics.specs.TypeSpec;

import static com.flipkart.lyrics.helper.Helper.resolveModifiers;

/**
 * Created by shrey.garg on 25/11/16.
 */
public class PrimitiveTypeHandler extends FieldTypeHandler {

    private Primitive primitive;

    public PrimitiveTypeHandler(Tune tune, MetaInfo metaInfo, Primitive primitive) {
        super(tune, metaInfo);
        this.primitive = primitive;
    }

    @Override
    public FieldSpec.Builder process(TypeSpec.Builder typeSpec, String key, FieldModel fieldModel) {
        Class aClass = fieldModel.isPrimitive() ? primitive.getUnboxed() : primitive.getBoxed();
        TypeName typeName = fieldModel.isArray() ? ArrayTypeName.of(aClass) : TypeName.get(aClass);

        FieldSpec.Builder builder = FieldSpec.builder(typeName, key, resolveModifiers(tune, fieldModel)).required(fieldModel.isRequired());
        if (fieldModel.getInitializeWith() != null) {
            builder.initializer("$L", fieldModel.getInitializeWith().getValue());
        }

        return builder;
    }
}
