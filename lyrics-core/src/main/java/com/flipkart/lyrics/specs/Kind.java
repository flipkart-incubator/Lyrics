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
package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.helper.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public enum Kind {
    CLASS(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet()),
    INTERFACE(Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.ABSTRACT)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC)), Util.immutableSet(Arrays.asList(Modifier.STATIC))),
    ENUM(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.singleton(Modifier.STATIC)),
    ANNOTATION(Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.ABSTRACT)), Util.immutableSet(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC)), Util.immutableSet(Arrays.asList(Modifier.STATIC))),
    ANONYMOUS(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet());


    final Set<Modifier> implicitFieldModifiers;
    final Set<Modifier> implicitMethodModifiers;
    final Set<Modifier> implicitTypeModifiers;
    final Set<Modifier> asMemberModifiers;

    Kind(Set<Modifier> implicitFieldModifiers, Set<Modifier> implicitMethodModifiers, Set<Modifier> implicitTypeModifiers, Set<Modifier> asMemberModifiers) {
        this.implicitFieldModifiers = implicitFieldModifiers;
        this.implicitMethodModifiers = implicitMethodModifiers;
        this.implicitTypeModifiers = implicitTypeModifiers;
        this.asMemberModifiers = asMemberModifiers;
    }
}