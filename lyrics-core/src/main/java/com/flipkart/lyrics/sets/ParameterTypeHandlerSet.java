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

package com.flipkart.lyrics.sets;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.processor.constructors.ParameterTypeHandler;

/**
 * Created by shrey.garg on 03/02/17.
 */
public abstract class ParameterTypeHandlerSet {

    protected Tune tune;
    protected MetaInfo metaInfo;

    public ParameterTypeHandlerSet() {

    }

    public ParameterTypeHandlerSet(Tune tune, MetaInfo metaInfo) {
        this.tune = tune;
        this.metaInfo = metaInfo;
    }

    public abstract ParameterTypeHandler getObjectTypeHandler();

    public abstract ParameterTypeHandler getIntegerTypeHandler();

    public abstract ParameterTypeHandler getBooleanTypeHandler();

    public abstract ParameterTypeHandler getLongTypeHandler();

    public abstract ParameterTypeHandler getDoubleTypeHandler();

    public abstract ParameterTypeHandler getCharacterTypeHandler();

    public abstract ParameterTypeHandler getShortTypeHandler();

    public abstract ParameterTypeHandler getByteTypeHandler();

    public abstract ParameterTypeHandler getFloatTypeHandler();

    public abstract ParameterTypeHandler getStringTypeHandler();

    public abstract ParameterTypeHandler getEnumTypeHandler();

    public final void setTune(Tune tune) {
        this.tune = tune;
    }

    public final void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
