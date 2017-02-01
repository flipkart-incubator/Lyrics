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


package com.flipkart.lyrics.processor.constructors;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.flipkart.lyrics.helper.Helper.processType;

/**
 * Created by shrey.garg on 01/02/17.
 */
public class RequiredFieldsConstructorHandler extends Handler {

    public RequiredFieldsConstructorHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        if (!tune.isRequiredFieldConstructorNeeded()) {
            return;
        }

        Map<String, FieldModel> fields = typeModel.getFields();
        List<String> requiredFields = fields.entrySet().stream()
                .filter(entry -> entry.getValue().isRequired())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (requiredFields.isEmpty()) {
            return;
        }

        MethodSpec.Builder constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);

        for (String field : requiredFields) {
            ParameterSpec.Builder parameterSpec = ParameterSpec.builder(processType(fields.get(field), metaInfo.getGenericVariables()), field);

            if (!fields.get(field).isPrimitive()) {
                tune.getValidationAnnotatorStyles().forEach(style -> style.processRequiredRuleForConstructor(parameterSpec));
            }

            constructor.addParameter(parameterSpec.build());
            constructor.addStatement("this.$L = $L", field, field);
        }

        typeSpec.addMethod(constructor.build());
    }

}
