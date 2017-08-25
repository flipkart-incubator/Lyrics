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

package com.flipkart.lyrics.processor.instances;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.FieldType;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;
import com.flipkart.lyrics.specs.TypeSpec;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by shrey.garg on 30/11/16.
 */
public class EnumWithFieldsValuesHandler extends Handler {

    public EnumWithFieldsValuesHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        Map<String, Object[]> values = typeModel.getValuesWithFields();
        List<Map.Entry<String, FieldModel>> orderedFields = orderFields(typeModel.getFields(), typeModel.getFieldOrder());
        String formattedParams = getFormattedParams(orderedFields);

        for (String key : values.keySet()) {
            if (formattedParams == null) {
                typeBuilder.addEnumConstant(key);
            } else {
                typeBuilder.addEnumConstant(key, TypeSpec.anonymousClassBuilder(formattedParams, values.get(key)).build());
            }
        }
    }

    private List<Map.Entry<String, FieldModel>> orderFields(Map<String, FieldModel> fieldModels, List<String> fieldOrder) {
        Map.Entry<String, FieldModel>[] orderedFields = new Map.Entry[fieldOrder.size()];
        for (Map.Entry<String, FieldModel> entry : fieldModels.entrySet()) {
            int index = fieldOrder.indexOf(entry.getKey());
            orderedFields[index] = entry;
        }
        return Arrays.asList(orderedFields);
    }

    private String getFormattedParams(List<Map.Entry<String, FieldModel>> orderedFields) {
        if (orderedFields.isEmpty()) {
            return null;
        }

        String formattedParams = "";
        for (Map.Entry<String, FieldModel> orderedField : orderedFields) {
            formattedParams = formattedParams + (orderedField.getValue().getFieldType().equals(FieldType.STRING) ? "$S" : "$L") + ", ";
        }
        return formattedParams.substring(0, formattedParams.length() - 2);
    }
}
