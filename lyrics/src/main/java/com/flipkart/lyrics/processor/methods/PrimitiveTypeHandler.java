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

import com.flipkart.lyrics.model.MethodModel;
import com.flipkart.lyrics.model.Primitive;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.Map;

/**
 * Created by shrey.garg on 05/12/16.
 */
public class PrimitiveTypeHandler extends MethodHandler {
    private Primitive primitive;

    public PrimitiveTypeHandler(Primitive primitive) {
        super();
        this.primitive = primitive;
    }

    @Override
    public MethodSpec.Builder process(TypeSpec.Builder typeSpec, String key, MethodModel methodModel, Map<String, TypeVariableName> typeVariableNames) {
        return MethodSpec.methodBuilder(key)
                .addModifiers(methodModel.getModifiers())
                .returns(methodModel.isPrimitive() ? primitive.getUnboxed() : primitive.getBoxed());
    }
}
