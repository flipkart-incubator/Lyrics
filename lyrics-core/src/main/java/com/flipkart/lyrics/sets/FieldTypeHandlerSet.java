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
import com.flipkart.lyrics.processor.fields.FieldTypeHandler;

/**
 * Created by shrey.garg on 12/01/17.
 */
public abstract class FieldTypeHandlerSet {

    protected Tune tune;
    protected MetaInfo metaInfo;

    public FieldTypeHandlerSet() {

    }

    public FieldTypeHandlerSet(Tune tune, MetaInfo metaInfo) {
        this.tune = tune;
        this.metaInfo = metaInfo;
    }

    public abstract FieldTypeHandler getObjectTypeHandler();

    public abstract FieldTypeHandler getIntegerTypeHandler();

    public abstract FieldTypeHandler getBooleanTypeHandler();

    public abstract FieldTypeHandler getLongTypeHandler();

    public abstract FieldTypeHandler getDoubleTypeHandler();

    public abstract FieldTypeHandler getCharacterTypeHandler();

    public abstract FieldTypeHandler getShortTypeHandler();

    public abstract FieldTypeHandler getByteTypeHandler();

    public abstract FieldTypeHandler getFloatTypeHandler();

    public abstract FieldTypeHandler getStringTypeHandler();

    public abstract FieldTypeHandler getEnumTypeHandler();

    public final void setTune(Tune tune) {
        this.tune = tune;
    }

    public final void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
