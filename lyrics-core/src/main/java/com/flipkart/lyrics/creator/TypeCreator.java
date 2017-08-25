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

package com.flipkart.lyrics.creator;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.sets.HandlerSet;
import com.flipkart.lyrics.specs.TypeSpec;

import java.util.Optional;

/**
 * Created by shrey.garg on 27/11/16.
 */
public abstract class TypeCreator {
    public abstract TypeSpec.Builder process(HandlerSet handlerSet, TypeModel typeModel);

    protected void handleAdditionalProperties(Tune tune, TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        tune.getTypeAdditionalPropertiesHandler().forEach((key, processor) -> {
            Optional.ofNullable(typeModel.getAdditionalFields().get(key)).ifPresent(value ->
                    processor.process(typeBuilder, key, value)
            );
        });
    }
}
