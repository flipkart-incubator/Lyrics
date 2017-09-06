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
package com.flipkart.lyrics.sample.json;

import com.flipkart.lyrics.helper.TriConsumer;
import com.flipkart.lyrics.java.JavaTune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.specs.TypeSpec;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kushal.sharma on 18/08/17.
 */
public class SampleJavaTune extends JavaTune {
    private static final Map<String, TriConsumer<TypeSpec.Builder, TypeModel, MetaInfo>> specialHandlers = new HashMap<>();

    static {
        specialHandlers.put("com.flipkart.lyrics.sample.userdb.people.TestInterface2", SpecialInterfaces::processTestInterface2);
    }

    @Override
    public Map<String, TriConsumer<TypeSpec.Builder, TypeModel, MetaInfo>> getSpecialInterfacesHandler() {
        return specialHandlers;
    }
}
