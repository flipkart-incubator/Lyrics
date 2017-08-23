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

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.interfaces.TypeSpec;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;

/**
 * Created by shrey.garg on 26/11/16.
 */
public class GlobalInclusionRule extends TypeRule {

    public GlobalInclusionRule(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    public void process(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        if (typeModel.getInclusion() == null) {
            return;
        }

        tune.getAnnotatorStyles().forEach(style -> style.processGlobalInclusionRule(typeSpec, typeModel));
    }
}
