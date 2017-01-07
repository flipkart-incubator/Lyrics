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

package com.flipkart.lyrics.processor.methods;

import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.FieldType;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

import static com.flipkart.lyrics.helper.Helper.getGetterSetterName;

/**
 * Created by shrey.garg on 25/11/16.
 */
public class SetterHandler {
    public MethodSpec.Builder process(FieldSpec fieldSpec, FieldModel fieldModel) {
        String methodName = getGetterSetterName(fieldSpec.name, true, fieldModel.getFieldType() == FieldType.BOOLEAN, fieldModel.isPrimitive());
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(fieldSpec.type, fieldSpec.name)
                .addStatement("this.$L = $L", fieldSpec.name, fieldSpec.name);
    }
}
