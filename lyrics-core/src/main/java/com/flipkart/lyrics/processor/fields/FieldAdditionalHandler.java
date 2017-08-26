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

package com.flipkart.lyrics.processor.fields;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.specs.FieldSpec;

/**
 * Created by shrey.garg on 13/05/17.
 */
public abstract class FieldAdditionalHandler {
    protected Tune tune;
    protected MetaInfo metaInfo;

    public FieldAdditionalHandler() {
    }

    /**
     * @param fieldBuilder Builder for the current field being processed, before it is added to type.
     * @param key          The additional property name for which the handler is called.
     * @param value        The non-null value of the additional property.
     * @return False, if the field is not supposed to be added to the type. True, otherwise.
     */
    public abstract boolean process(FieldSpec.Builder fieldBuilder, String key, Object value);

    public final void setTune(Tune tune) {
        this.tune = tune;
    }

    public final void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
