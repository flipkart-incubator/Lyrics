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

package com.flipkart.lyrics.processor.generics;

import com.flipkart.lyrics.model.GenericVariableModel;
import com.flipkart.lyrics.model.TypeModel;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getTypeVariables;

/**
 * Created by shrey.garg on 25/11/16.
 */
public class GenericsHandler {
    public Map<String, TypeVariableName> process(TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        Map<String, TypeVariableName> typeVariableNames = getTypeVariables(typeModel.getGenericVariables());
        for (GenericVariableModel variable : typeModel.getGenericVariables()) {
            typeBuilder.addTypeVariable(typeVariableNames.get(variable.getName()));
        }
        return typeVariableNames;
    }
}
