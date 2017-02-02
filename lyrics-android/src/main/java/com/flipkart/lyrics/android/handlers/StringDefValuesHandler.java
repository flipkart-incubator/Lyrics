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

package com.flipkart.lyrics.android.handlers;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;

/**
 * Created by anshul.garg on 11/01/17.
 */
public class StringDefValuesHandler extends Handler {

    public StringDefValuesHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        String valuesStr = "";
        for (String key : getEnumValues(typeModel)) {
            valuesStr += key + ", ";
            typeBuilder.addField(FieldSpec.builder(ClassName.get(String.class), key)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", key)
                    .build());
        }

        AnnotationSpec.Builder retentionAnnotation = AnnotationSpec.builder(ClassName.get(Retention.class)).
                addMember("value", "$T.SOURCE", ClassName.get(RetentionPolicy.class));
        AnnotationSpec.Builder intDefAnnotation = AnnotationSpec.builder(ClassName.get("android.support.annotation", "StringDef"))
                .addMember("value", "{ $L }", valuesStr.substring(0, valuesStr.length() - 2));

        typeBuilder.addType(TypeSpec.annotationBuilder(metaInfo.getClassName() + "Def").
                addModifiers(Modifier.PUBLIC).
                addAnnotation(retentionAnnotation.build()).
                addAnnotation(intDefAnnotation.build()).
                build());
    }

    protected Collection<String> getEnumValues(TypeModel typeModel) {
        return typeModel.getValues();
    }
}