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

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MethodModel;
import com.flipkart.lyrics.model.MethodType;
import com.flipkart.lyrics.model.TypeModel;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.isNullOrEmpty;

/**
 * Created by shrey.garg on 03/12/16.
 */
public class AnnotationMethodsHandler {
    public void process(TypeSpec.Builder typeBuilder, TypeModel typeModel, Tune configuration) {
        Map<String, MethodModel> methods = typeModel.getMethods();
        if (isNullOrEmpty(methods)) {
            return;
        }

        for (Map.Entry<String, MethodModel> method : methods.entrySet()) {
            MethodSpec.Builder methodBuilder = method.getValue().getMethodType().getMethodHandler().process(typeBuilder, method.getKey(), method.getValue())
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

            if (method.getValue().getDefaultValue() != null) {
                methodBuilder.defaultValue(method.getValue().getMethodType() == MethodType.STRING ? "$S" : "$L", method.getValue().getDefaultValue());
            }

            typeBuilder.addMethod(methodBuilder.build());
        }
    }
}
