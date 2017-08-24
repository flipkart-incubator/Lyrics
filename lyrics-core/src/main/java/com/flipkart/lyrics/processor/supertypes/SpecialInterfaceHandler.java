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
import com.flipkart.lyrics.helper.TriConsumer;
import com.flipkart.lyrics.specs.TypeSpec;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.model.VariableModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;

import java.util.*;

/**
 * Created by shrey.garg on 07/12/16.
 */
public class SpecialInterfaceHandler extends Handler {

    public SpecialInterfaceHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        final Map<String, TriConsumer<TypeSpec.Builder, TypeModel, MetaInfo>> specialHandlers = Optional.ofNullable(tune.getSpecialInterfacesHandler()).orElseGet(HashMap::new);
        Set<VariableModel> interfaces = new HashSet<>();
        interfaces.addAll(tune.interfaces());
        interfaces.addAll(typeModel.getInterfaces());
        interfaces.forEach(i -> Optional.ofNullable(specialHandlers.get(i.getType())).ifPresent(fn -> fn.accept(typeSpec, typeModel, metaInfo)));
    }
}
