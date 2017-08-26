package com.flipkart.lyrics.java.specs;

import com.flipkart.lyrics.java.helper.Util;

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