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

package com.flipkart.lyrics.processor.supertypes;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;
import com.flipkart.lyrics.specs.TypeName;
import com.flipkart.lyrics.specs.TypeSpec;

import static com.flipkart.lyrics.helper.Helper.getResolvedTypeName;

/**
 * Created by shrey.garg on 28/11/16.
 */
public class SuperClassHandler extends Handler {

    public SuperClassHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        if (typeModel.getExtendsType() == null) {
            return;
        }

        TypeName extendsType = getResolvedTypeName(typeModel.getExtendsType(), metaInfo.getGenericVariables(), tune.getChords());

        typeSpec.superclass(extendsType);
    }
}
