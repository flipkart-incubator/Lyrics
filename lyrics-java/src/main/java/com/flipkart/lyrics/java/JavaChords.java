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

package com.flipkart.lyrics.java;

import com.flipkart.lyrics.config.Chords;

import java.util.List;
import java.util.Map;

/**
 * Created by shrey.garg on 18/03/18.
 */
public class JavaChords implements Chords {
    private final String map = Map.class.getName();
    private final String list = List.class.getName();
    private final String string = String.class.getName();

    @Override
    public String handleMap() {
        return map;
    }

    @Override
    public String handleList() {
        return list;
    }

    @Override
    public String handleString() {
        return string;
    }
}
