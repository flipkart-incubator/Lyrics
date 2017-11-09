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
package com.flipkart.lyrics.specs;

public enum Modifier {

    // See JLS sections 8.1.1, 8.3.1, 8.4.3, 8.8.3, and 9.1.1.
    // java.lang.reflect.Modifier includes INTERFACE, but that's a VMism.

    /**
     * The modifier {@code public}
     */PUBLIC,
    /**
     * The modifier {@code protected}
     */PROTECTED,
    /**
     * The modifier {@code private}
     */PRIVATE,
    /**
     * The modifier {@code abstract}
     */ABSTRACT,
    /**
     * The modifier {@code default}
     *
     * @since 1.8
     */
    DEFAULT,
    /**
     * The modifier {@code static}
     */STATIC,
    /**
     * The modifier {@code final}
     */FINAL,
    /**
     * The modifier {@code transient}
     */TRANSIENT,
    /**
     * The modifier {@code volatile}
     */VOLATILE,
    /**
     * The modifier {@code synchronized}
     */SYNCHRONIZED,
    /**
     * The modifier {@code native}
     */NATIVE,
    /**
     * The modifier {@code strictfp}
     */STRICTFP,

    CONVENIENCE,
    REQUIRED,
    OVERRIDE;


    /**
     * Returns this modifier's name in lowercase.
     */
    public String toString() {
        return name().toLowerCase(java.util.Locale.US);
    }
}

