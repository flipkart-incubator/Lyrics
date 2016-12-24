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
import com.flipkart.lyrics.model.FieldModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.lang.model.element.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shrey.garg on 26/11/16.
 */
public class ToStringHandler {
    public void process(TypeSpec.Builder typeBuilder, String className, Map<String, FieldModel> fieldModels, Tune configuration) {
        if (!configuration.isToStringNeeded()) {
            return;
        }

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

        if (!configuration.useCommonsLang3()) {
            toStringBuilder.addStatement("final StringBuilder sb = new StringBuilder(\"$L{\")", className);

            if (nonStaticFields.size() > 0) {
                String firstField = nonStaticFields.get(0);
                toStringBuilder.addStatement("sb.append(\"$L=\").append($L)", firstField, firstField);
                if (nonStaticFields.size() > 1) {
                    for (String field : nonStaticFields.subList(1, nonStaticFields.size())) {
                        toStringBuilder.addStatement("sb.append(\", $L=\").append($L);", field, field);
                    }
                }
            }

            toStringBuilder.addStatement("sb.append('}')");
            toStringBuilder.addStatement("return sb.toString()");
        } else {
            ClassName toStringBuilderClass = ClassName.get(ToStringBuilder.class);
            toStringBuilder.addCode("return new $T(this)\n", toStringBuilderClass);
            for (String field : nonStaticFields) {
                toStringBuilder.addCode("\t\t.append($S, $L)\n", field, field);
            }
            toStringBuilder.addCode("\t\t.toString();\n");
        }

        typeBuilder.addMethod(toStringBuilder.build());
    }
}
