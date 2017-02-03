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

package com.flipkart.lyrics.android.handlers;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.processor.constructors.ParameterTypeHandler;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by shrey.garg on 03/02/17.
 */
public class AndroidEnumParameterTypeHandler extends ParameterTypeHandler {

    public AndroidEnumParameterTypeHandler(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    public ParameterSpec.Builder process(TypeSpec.Builder typeSpec, String key, FieldModel fieldModel) {
        return ParameterSpec.builder(String.class, key);
    }

}
