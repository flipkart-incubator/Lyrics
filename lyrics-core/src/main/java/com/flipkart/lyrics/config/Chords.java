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
 * Allowing the usage of various "types" in a language independent representation
 * Created by shrey.garg on 18/03/18.
 */
public interface Chords {
    /**
     * @return Representation of "MAP" for the language being used.
     */
    String handleMap();

    /**
     * @return Representation of "LIST" for the language being used.
     */
    String handleList();

    /**
     * @return Representation of "STRING" for the language being used.
     */
    String handleString();

    /**
     * @return Representation of "OBJECT" for the language being used.
     */
    String handleObject();

    /**
     * @return Representation of "INTEGER" for the language being used.
     */
    String handleInteger();

    /**
     * @return Representation of "BOOLEAN" for the language being used.
     */
    String handleBoolean();

    /**
     * @return Representation of "LONG" for the language being used.
     */
    String handleLong();

    /**
     * @return Representation of "DOUBLE" for the language being used.
     */
    String handleDouble();

    /**
     * @return Representation of "CHARACTER" for the language being used.
     */
    String handleCharacter();

    /**
     * @return Representation of "SHORT" for the language being used.
     */
    String handleShort();

    /**
     * @return Representation of "BYTE" for the language being used.
     */
    String handleByte();

    /**
     * @return Representation of "FLOAT" for the language being used.
     */
    String handleFloat();
}
