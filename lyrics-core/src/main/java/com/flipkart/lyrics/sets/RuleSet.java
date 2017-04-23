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
import com.flipkart.lyrics.rules.FieldRule;
import com.flipkart.lyrics.rules.method.MethodRule;
import com.flipkart.lyrics.rules.type.TypeRule;

/**
 * Created by shrey.garg on 30/12/16.
 */
public abstract class RuleSet {

    protected Tune tune;
    protected MetaInfo metaInfo;

    public RuleSet() {
    }

    public RuleSet(Tune tune, MetaInfo metaInfo) {
        this.tune = tune;
        this.metaInfo = metaInfo;
    }

    /**
     * @return An instance of TypeRule that knows how to handle JSON-SubTypes, schema defines relationships of the schema class to
     * all of its child classes using {@link com.flipkart.lyrics.model.SubTypeModel} class.
     */
    public abstract TypeRule getSubTypeRule();
    public abstract TypeRule getGlobalInclusionRule();
    public abstract TypeRule getPropertyOrderRule();

    public abstract TypeRule getGlobalDeprecatedRule();
    public abstract FieldRule getDeprecatedRule();

    public abstract FieldRule getNamedAsRule();
    public abstract FieldRule getInclusionRule();

    public abstract FieldRule getRequiredRule();
    public abstract MethodRule getGetterRequiredRule();
    public abstract MethodRule getSetterRequiredRule();

    public abstract FieldRule getNotRequiredRule();
    public abstract MethodRule getGetterNotRequiredRule();
    public abstract MethodRule getSetterNotRequiredRule();

    public void setTune(Tune tune) {
        this.tune = tune;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
