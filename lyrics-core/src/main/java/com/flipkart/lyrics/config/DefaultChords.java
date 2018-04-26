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

package com.flipkart.lyrics.config;

/**
 * Created by shrey.garg on 18/03/18.
 */
public class DefaultChords implements Chords {
    private final String map = "MAP";
    private final String list = "LIST";
    private final String string = "STRING";
    private final String object = "OBJECT";

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

    @Override
    public String handleObject() {
        return object;
    }

    @Override
    public String handleInteger() {
        return "INTEGER";
    }

    @Override
    public String handleBoolean() {
        return "BOOLEAN";
    }

    @Override
    public String handleLong() {
        return "LONG";
    }

    @Override
    public String handleDouble() {
        return "DOUBLE";
    }

    @Override
    public String handleCharacter() {
        return "CHARACTER";
    }

    @Override
    public String handleShort() {
        return "SHORT";
    }

    @Override
    public String handleByte() {
        return "BYTE";
    }

    @Override
    public String handleFloat() {
        return "FLOAT";
    }
}
