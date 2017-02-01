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

package com.flipkart.lyrics.sets;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.rules.*;
import com.flipkart.lyrics.rules.method.*;
import com.flipkart.lyrics.rules.type.GlobalDeprecatedRule;
import com.flipkart.lyrics.rules.type.GlobalInclusionRule;
import com.flipkart.lyrics.rules.type.SubTypesRule;
import com.flipkart.lyrics.rules.type.TypeRule;

/**
 * Created by shrey.garg on 30/12/16.
 */
public class DefaultRuleSet extends RuleSet {

    public DefaultRuleSet() {
        super();
    }

    public DefaultRuleSet(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    public TypeRule getSubTypeRule() {
        return new SubTypesRule(tune, metaInfo);
    }

    @Override
    public TypeRule getGlobalInclusionRule() {
        return new GlobalInclusionRule(tune, metaInfo);
    }

    @Override
    public TypeRule getGlobalDeprecatedRule() {
        return new GlobalDeprecatedRule(tune, metaInfo);
    }

    @Override
    public FieldRule getDeprecatedRule() {
        return new DeprecatedRule(tune, metaInfo);
    }

    @Override
    public FieldRule getNamedAsRule() {
        return new NamedAsRule(tune, metaInfo);
    }

    @Override
    public FieldRule getRequiredRule() {
        return new RequiredRule(tune, metaInfo);
    }

    @Override
    public MethodRule getGetterRequiredRule() {
        return new GetterRequiredRule(tune, metaInfo);
    }

    @Override
    public MethodRule getSetterRequiredRule() {
        return new SetterRequiredRule(tune, metaInfo);
    }

    @Override
    public FieldRule getNotRequiredRule() {
        return new NotRequiredRule(tune, metaInfo);
    }

    @Override
    public MethodRule getGetterNotRequiredRule() {
        return new GetterNotRequiredRule(tune, metaInfo);
    }

    @Override
    public MethodRule getSetterNotRequiredRule() {
        return new SetterNotRequiredRule(tune, metaInfo);
    }

    @Override
    public FieldRule getInclusionRule() {
        return new InclusionRule(tune, metaInfo);
    }

}
