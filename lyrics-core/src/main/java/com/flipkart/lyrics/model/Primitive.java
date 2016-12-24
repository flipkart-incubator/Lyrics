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

package com.flipkart.lyrics.model;

/**
 * Created by shrey.garg on 05/12/16.
 */
public enum Primitive {
    BYTE(byte.class, Byte.class),
    SHORT(short.class, Short.class),
    INTEGER(int.class, Integer.class),
    LONG(long.class, Long.class),
    FLOAT(float.class, Float.class),
    DOUBLE(double.class, Double.class),
    BOOLEAN(boolean.class, Boolean.class),
    CHARACTER(char.class, Character.class);

    private Class unboxed;
    private Class boxed;

    Primitive(Class unboxed, Class boxed) {
        this.unboxed = unboxed;
        this.boxed = boxed;
    }

    public Class getUnboxed() {
        return unboxed;
    }

    public void setUnboxed(Class unboxed) {
        this.unboxed = unboxed;
    }

    public Class getBoxed() {
        return boxed;
    }

    public void setBoxed(Class boxed) {
        this.boxed = boxed;
    }
}
