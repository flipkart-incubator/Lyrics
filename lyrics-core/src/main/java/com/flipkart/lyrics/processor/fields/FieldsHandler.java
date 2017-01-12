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

package com.flipkart.lyrics.processor.fields;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.processor.methods.GetterHandler;
import com.flipkart.lyrics.processor.methods.SetterHandler;
import com.flipkart.lyrics.sets.RuleSet;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

import static com.flipkart.lyrics.helper.Helper.getFieldTypeHandler;

/**
 * Created by shrey.garg on 29/11/16.
 */
public class FieldsHandler extends Handler {

    public FieldsHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        for (String key : typeModel.getFields().keySet()) {
            FieldModel fieldModel = typeModel.getFields().get(key);
            FieldSpec.Builder fieldBuilder = getFieldTypeHandler(fieldModel.getFieldType(), tune.getFieldTypeHandlerSet())
                    .process(typeBuilder, key, tune, fieldModel, metaInfo.getGenericVariables());
            handleFieldRules(fieldBuilder, fieldModel);

            FieldSpec fieldSpec = fieldBuilder.build();
            typeBuilder.addField(fieldSpec);

            if (tune.areAccessorsNeeded()) {
                new GetterHandler(tune, metaInfo, ruleSet).process(typeBuilder, fieldSpec, fieldModel);
                new SetterHandler(tune, metaInfo, ruleSet).process(typeBuilder, fieldSpec, fieldModel);
            }
        }
    }

    private void handleFieldRules(FieldSpec.Builder fieldSpec, FieldModel fieldModel) {
        ruleSet.getInclusionRule().process(fieldSpec, fieldModel);
        ruleSet.getNamedAsRule().process(fieldSpec, fieldModel);
        ruleSet.getRequiredRule().process(fieldSpec, fieldModel);
        ruleSet.getNotRequiredRule().process(fieldSpec, fieldModel);
    }
}
