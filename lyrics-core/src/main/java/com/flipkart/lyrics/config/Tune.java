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

package com.flipkart.lyrics.config;

import com.flipkart.lyrics.helper.TriConsumer;
import com.flipkart.lyrics.model.AnnotationModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.model.VariableModel;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Created by shrey.garg on 26/11/16.
 */
public interface Tune {
    boolean areJacksonStyleAnnotationsNeeded();

    boolean areGsonStyleAnnotationsNeeded();

    Modifier getDefaultFieldModifier();

    Modifier getDefaultClassModifier();

    List<AnnotationModel> getClassLevelAnnotations();

    /**
     * When switched on, include "android-support" library in your project for the classes to compile.
     */
    boolean areAndroidValidationAnnotationsNeeded();

    /**
     * When switched on, include artifactId: "jsr305" from groupId: "com.google.code.findbugs"
     * in your project for the classes to compile.
     */
    boolean areJsr305AnnotationsNeeded();

    boolean areAccessorsNeeded();

    boolean isToStringNeeded();

    boolean areHashCodeAndEqualsNeeded();

    boolean useCommonsLang3();

    Set<VariableModel> interfaces();

    boolean forceDefaultFieldModifiers();

    Map<String, TriConsumer<TypeSpec.Builder, TypeModel, MetaInfo>> getSpecialInterfacesHandler();
}
