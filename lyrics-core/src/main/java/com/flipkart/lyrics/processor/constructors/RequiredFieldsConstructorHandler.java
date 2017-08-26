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
import com.flipkart.lyrics.specs.Modifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getRequiredFields;

/**
 * Created by shrey.garg on 01/02/17.
 */
public class RequiredFieldsConstructorHandler extends ConstructorHandler {

    public RequiredFieldsConstructorHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    protected List<String> getConstructorFields(TypeModel typeModel) {
        if (!tune.isRequiredFieldConstructorNeeded()) {
            return new ArrayList<>();
        }

        Map<String, FieldModel> fields = typeModel.getFields();

        return getRequiredFields(fields, tune.excludeInitializedFieldsFromConstructor());
    }

    @Override
    protected Modifier getModifier() {
        return Modifier.PUBLIC;
    }
}
