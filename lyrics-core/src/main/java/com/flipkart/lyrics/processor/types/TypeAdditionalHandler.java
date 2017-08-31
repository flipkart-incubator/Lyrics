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
package com.flipkart.lyrics.processor.types;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.specs.TypeSpec;

/**
 * Created by shrey.garg on 06/06/17.
 */
public abstract class TypeAdditionalHandler {
    protected Tune tune;
    protected MetaInfo metaInfo;

    /**
     * @param typeBuilder Builder for the current type being processed, after all the other handlers and rules are applied.
     * @param key         The additional property name for which the handler is called.
     * @param value       The non-null value of the additional property.
     */
    public abstract void process(TypeSpec.Builder typeBuilder, String key, Object value);

    public final void setTune(Tune tune) {
        this.tune = tune;
    }

    public final void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
