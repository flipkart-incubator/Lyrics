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

package com.flipkart.lyrics.processor.constructors;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.flipkart.lyrics.helper.Helper.isNullOrEmpty;

/**
 * Created by shrey.garg on 30/11/16.
 */
public class OrderedConstructorHandler extends Handler {

    public OrderedConstructorHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    public void process(TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        List<String> fieldOrder = typeModel.getFieldOrder();
        if (isNullOrEmpty(fieldOrder)) {
            return;
        }

        Map<String, FieldSpec> fields = typeBuilder.build().fieldSpecs.stream().collect(Collectors.toMap(
                field -> field.name,
                Function.identity()
        ));

        MethodSpec.Builder orderedConstructor = MethodSpec.constructorBuilder();

        for (String field : fieldOrder) {
            orderedConstructor.addParameter(fields.get(field).type, field);
            orderedConstructor.addStatement("this.$L = $L", field, field);
        }

        typeBuilder.addMethod(orderedConstructor.build());
    }
}
