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

package com.flipkart.lyrics.processor.constructors;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.specs.ParameterSpec;
import com.flipkart.lyrics.specs.TypeSpec;

import static com.flipkart.lyrics.helper.Helper.processType;

/**
 * Created by shrey.garg on 03/02/17.
 */
public class ObjectParameterTypeHandler extends ParameterTypeHandler {

    public ObjectParameterTypeHandler(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    public ParameterSpec.Builder process(TypeSpec.Builder typeSpec, String field, FieldModel fieldModel) {
        return ParameterSpec.builder(processType(fieldModel, metaInfo.getGenericVariables(), tune.getChords()), field);
    }
}
