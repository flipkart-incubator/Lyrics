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
import com.flipkart.lyrics.sets.RuleSet;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getRequiredFields;

/**
 * Created by shrey.garg on 06/02/17.
 */
public class CustomConstructorHandler extends ConstructorHandler {

    public CustomConstructorHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    protected List<String> getConstructorFields(TypeModel typeModel) {
        List<String> customConstructorFields = typeModel.getCustomConstructorFields();
        if (!tune.isCustomConstructorNeeded() || customConstructorFields == null) {
            return new ArrayList<>();
        }

        Map<String, FieldModel> fields = typeModel.getFields();
        List<String> requiredFields = getRequiredFields(fields, tune.excludeInitializedFieldsFromConstructor());
        customConstructorFields.removeAll(requiredFields);

        if (customConstructorFields.isEmpty()) {
            return new ArrayList<>();
        }

        requiredFields.addAll(customConstructorFields);

        return requiredFields;
    }

    @Override
    protected Modifier getModifier() {
        return Modifier.PUBLIC;
    }
}
