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

package com.flipkart.lyrics.rules.type;

import com.flipkart.lyrics.java.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.java.specs.TypeSpec;

/**
 * Created by shrey.garg on 30/11/16.
 */
public class SubTypesRule extends TypeRule {

    public SubTypesRule(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    public void process(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        if (typeModel.getSubTypes() == null) {
            return;
        }

        tune.getAnnotatorStyles().forEach(style -> style.processSubTypeRule(typeSpec, typeModel));
    }
}
