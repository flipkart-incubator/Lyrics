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

import com.flipkart.lyrics.processor.constructors.ObjectParameterTypeHandler;
import com.flipkart.lyrics.processor.constructors.ParameterTypeHandler;

/**
 * Created by shrey.garg on 03/02/17.
 */
public class DefaultParameterTypeHandlerSet extends ParameterTypeHandlerSet {
    @Override
    public ParameterTypeHandler getObjectTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }

    @Override
    public ParameterTypeHandler getIntegerTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }

    @Override
    public ParameterTypeHandler getBooleanTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }

    @Override
    public ParameterTypeHandler getLongTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }

    @Override
    public ParameterTypeHandler getDoubleTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }

    @Override
    public ParameterTypeHandler getCharacterTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }

    @Override
    public ParameterTypeHandler getShortTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }

    @Override
    public ParameterTypeHandler getByteTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }

    @Override
    public ParameterTypeHandler getFloatTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }

    @Override
    public ParameterTypeHandler getStringTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }

    @Override
    public ParameterTypeHandler getEnumTypeHandler() {
        return new ObjectParameterTypeHandler(tune, metaInfo);
    }
}
