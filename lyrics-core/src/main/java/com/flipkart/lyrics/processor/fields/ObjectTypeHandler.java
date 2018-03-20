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
import com.flipkart.lyrics.model.InitializerModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.specs.*;

import static com.flipkart.lyrics.helper.Helper.*;

/**
 * Created by shrey.garg on 25/11/16.
 */
public class ObjectTypeHandler extends FieldTypeHandler {

    public ObjectTypeHandler(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    public FieldSpec.Builder process(TypeSpec.Builder typeSpec, String key, FieldModel fieldModel) {
        TypeName typeName;
        if (fieldModel.getType() == null || fieldModel.getType().getType() == null) {
            typeName = TypeName.OBJECT;
        } else {
            typeName = getResolvedTypeName(fieldModel.getType(), metaInfo.getGenericVariables(), tune.getChords());
        }

        typeName = fieldModel.isArray() ? ArrayTypeName.of(typeName) : typeName;
        FieldSpec.Builder builder = FieldSpec.builder(typeName, key, resolveModifiers(tune, fieldModel)).required(fieldModel.isRequired());
        InitializerModel initializeWith = fieldModel.getInitializeWith();
        if (initializeWith != null) {
            if (initializeWith.getValue() != null) {
                ClassName fromType = getClassName(initializeWith.getFromType());
                builder.initializer("$T.$L", fromType, initializeWith.getValue());
            } else if (initializeWith.getNewInstanceOf() != null) {
                ClassName instanceOf = getClassName(initializeWith.getNewInstanceOf());
                String initializerString = "new $T" + (initializeWith.isInferGenerics() ? "<>" : "") + "()";
                builder.initializer(initializerString, instanceOf);
            }
        }

        return builder;
    }
}
