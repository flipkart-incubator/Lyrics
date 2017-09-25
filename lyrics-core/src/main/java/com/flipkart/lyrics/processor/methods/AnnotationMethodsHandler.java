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
import com.flipkart.lyrics.helper.Helper;
import com.flipkart.lyrics.model.*;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;
import com.flipkart.lyrics.specs.MethodSpec;
import com.flipkart.lyrics.specs.Modifier;
import com.flipkart.lyrics.specs.TypeSpec;

import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.isNullOrEmpty;

/**
 * Created by shrey.garg on 03/12/16.
 */
public class AnnotationMethodsHandler extends Handler {

    public AnnotationMethodsHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        Map<String, MethodModel> methods = typeModel.getMethods();
        if (isNullOrEmpty(methods)) {
            return;
        }

        for (Map.Entry<String, MethodModel> methodEntry : methods.entrySet()) {
            FieldModel returnsModel = methodEntry.getValue().getReturnType();
            MethodSpec.Builder builder = MethodSpec.methodBuilder(methodEntry.getKey())
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .returns(Helper.processType(returnsModel, metaInfo.getGenericVariables()));

            if (methodEntry.getValue().getDefaultValue() != null) {
                builder.defaultValue(returnsModel.getFieldType() == FieldType.STRING ? "$S" : "$L", methodEntry.getValue().getDefaultValue());
            }

            typeBuilder.addMethod(builder.build());
        }
    }
}
