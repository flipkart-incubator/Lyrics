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

package com.flipkart.lyrics.processor.supertypes;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.model.VariableModel;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created by shrey.garg on 07/12/16.
 */
public class SpecialInterfaceHandler {

    public void process(TypeSpec.Builder typeSpec, TypeModel typeModel, Map<String, TypeVariableName> typeVariableNames, Tune configuration) {
        final Map<String, BiConsumer<TypeSpec.Builder, TypeModel>> specialHandlers = Optional.ofNullable(configuration.getSpecialInterfacesHandler()).orElseGet(HashMap::new);
        Set<VariableModel> interfaces = new HashSet<>();
        interfaces.addAll(configuration.interfaces());
        interfaces.addAll(typeModel.getInterfaces());
        interfaces.forEach(i -> Optional.ofNullable(specialHandlers.get(i.getType())).ifPresent(fn -> fn.accept(typeSpec, typeModel)));
    }

}
