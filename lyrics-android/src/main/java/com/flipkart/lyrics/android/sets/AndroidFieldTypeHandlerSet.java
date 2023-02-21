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

package com.flipkart.lyrics.android.sets;

import com.flipkart.lyrics.android.handlers.AndroidEnumTypeHandler;
import com.flipkart.lyrics.android.handlers.AndroidObjectTypeHandler;
import com.flipkart.lyrics.processor.fields.FieldTypeHandler;
import com.flipkart.lyrics.sets.DefaultFieldTypeHandlerSet;

/**
 * Created by anshul.garg on 13/01/17.
 */
public class AndroidFieldTypeHandlerSet extends DefaultFieldTypeHandlerSet {

    @Override
    public FieldTypeHandler getObjectTypeHandler() {
        return new AndroidObjectTypeHandler(tune, metaInfo);
    }

    @Override
    public FieldTypeHandler getEnumTypeHandler() {
        return new AndroidEnumTypeHandler(tune, metaInfo);
    }
}
