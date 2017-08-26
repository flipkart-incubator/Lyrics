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

package com.flipkart.lyrics.helper;


import com.flipkart.lyrics.specs.ClassName;

/**
 * Created by shrey.garg on 30/11/16.
 */
public class ClassNames {

    /**
     * Used with configuration option: "areJsr305AnnotationsNeeded".
     * Include artifactId: "jsr305" from groupId: "com.google.code.findbugs"
     */
    public static final ClassName JSR_305_NON_NULL = ClassName.get("javax.annotation", "Nonnull");
    public static final ClassName JSR_305_NULLABLE = ClassName.get("javax.annotation", "Nullable");


    /**
     * Used with configuration option: "areAndroidValidationAnnotationsNeeded".
     * Include "android-support" library.
     */
    public static final ClassName ANDROID_VALIDATIONS_NON_NULL = ClassName.get("android.support.annotation", "NonNull");
    public static final ClassName ANDROID_VALIDATIONS_NULLABLE = ClassName.get("android.support.annotation", "Nullable");
}
