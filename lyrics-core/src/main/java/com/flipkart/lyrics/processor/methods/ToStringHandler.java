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
import com.flipkart.lyrics.specs.MethodSpec;
import com.flipkart.lyrics.specs.TypeSpec;
import com.flipkart.lyrics.specs.Modifier;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shrey.garg on 26/11/16.
 */
public class ToStringHandler extends Handler {

    public ToStringHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        if (!tune.isToStringNeeded() || tune.getObjectMethodsStyle() == null) {
            return;
        }

        Map<String, FieldModel> fieldModels = typeModel.getFields();
        List<String> nonStaticFields = fieldModels.entrySet().stream()
                .filter(entry ->
                        (!entry.getValue().isExcludeFromToString() && !Arrays.stream(entry.getValue().getModifiers())
                                .anyMatch(modifier -> modifier.equals(Modifier.STATIC))))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        MethodSpec.Builder toStringBuilder = MethodSpec.methodBuilder("toString")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addAnnotation(Override.class);

        tune.getObjectMethodsStyle().processToString(toStringBuilder, nonStaticFields, metaInfo);

        typeBuilder.addMethod(toStringBuilder.build());
    }
}
