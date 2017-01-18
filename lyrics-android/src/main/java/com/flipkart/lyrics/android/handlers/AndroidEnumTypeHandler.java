/*
 * Copyright 2017 Flipkart Internet, pvt ltd.
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

package com.flipkart.lyrics.android.handlers;

import com.flipkart.lyrics.android.config.AndroidTune;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.FieldType;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.processor.fields.FieldTypeHandler;
import com.flipkart.lyrics.processor.fields.ObjectTypeHandler;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import static com.flipkart.lyrics.helper.Helper.getClassName;
import static com.flipkart.lyrics.helper.Helper.resolveModifiers;

/**
 * Created by anshul.garg on 13/01/17.
 */
public class AndroidEnumTypeHandler extends FieldTypeHandler {

    public AndroidEnumTypeHandler(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    public FieldSpec.Builder process(TypeSpec.Builder typeSpec, String key, FieldModel fieldModel) {
        AndroidTune tune = (AndroidTune) this.tune;
        String unresolvedType = fieldModel.getType().getType();

        if (fieldModel.getFieldType() != FieldType.ENUM || !tune.createStringDefsFor().contains(unresolvedType)) {
            return new ObjectTypeHandler(tune, metaInfo).process(typeSpec, key, fieldModel);
        }

        String enumName = unresolvedType.substring(unresolvedType.lastIndexOf(".") + 1);
        AnnotationSpec annotation = AnnotationSpec.builder(getClassName(unresolvedType + "." + enumName + "Def")).build();
        return FieldSpec.builder(TypeName.get(String.class), key, resolveModifiers(tune, fieldModel)).addAnnotation(annotation);
    }
}
