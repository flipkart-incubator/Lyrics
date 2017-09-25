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

package com.flipkart.lyrics.helper;

import com.flipkart.lyrics.specs.ClassName;
import org.junit.jupiter.api.Test;

import static com.flipkart.lyrics.helper.Helper.getGetterSetterName;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by shrey.garg on 18/05/17.
 */
public class HelperTest {

    @Test
    public void testGetClassName() {
        String name = "com.flipkart.lyrics.Test";
        ClassName className = Helper.getClassName(name);
        assertEquals("com.flipkart.lyrics", className.packageName());
        assertEquals("Test", className.simpleName());
    }

    @Test
    public void testNormalGetterName() {
        String fieldName = "one";

        String getter = getGetterSetterName(fieldName, false, false, false);

        assertEquals("getOne", getter);
    }

    @Test
    public void testNormalSetterName() {
        String fieldName = "one";

        String setter = getGetterSetterName(fieldName, true, false, false);

        assertEquals("setOne", setter);
    }

    @Test
    public void testNormalGetterPrimitiveName() {
        String fieldName = "one";

        String getter = getGetterSetterName(fieldName, false, false, true);

        assertEquals("getOne", getter);
    }

    @Test
    public void testBooleanName() {
        verifyBooleanGetterName("one", "isOne", true);
        verifyBooleanSetterName("one", "setOne", true);

        verifyBooleanGetterName("one", "getOne", false);
        verifyBooleanSetterName("one", "setOne", false);
    }

    @Test
    public void testBooleanNameStartsWithIsWithNoCamelCase() {
        verifyBooleanGetterName("isone", "isIsone", true);
        verifyBooleanSetterName("isone", "setIsone", true);

        verifyBooleanGetterName("isone", "getIsone", false);
        verifyBooleanSetterName("isone", "setIsone", false);
    }

    @Test
    public void testBooleanPrimitiveNameStartsWithIsWithCamelCase() {
        verifyBooleanGetterName("isOne", "isOne", true);
        verifyBooleanSetterName("isOne", "setOne", true);

        verifyBooleanGetterName("isOne", "getOne", false);
        verifyBooleanSetterName("isOne", "setOne", false);
    }

    @Test
    public void testBooleanNameIs() {
        verifyBooleanGetterName("is", "isIs", true);
        verifyBooleanSetterName("is", "setIs", true);

        verifyBooleanGetterName("is", "getIs", false);
        verifyBooleanSetterName("is", "setIs", false);
    }

    @Test
    public void testBooleanNameOneChar() {
        verifyBooleanGetterName("i", "isI", true);
        verifyBooleanSetterName("i", "setI", true);

        verifyBooleanGetterName("i", "getI", false);
        verifyBooleanSetterName("i", "setI", false);
    }

    @Test
    public void testBooleanNameIsIs() {
        verifyBooleanGetterName("isIs", "isIs", true);
        verifyBooleanSetterName("isIs", "setIs", true);

        verifyBooleanGetterName("isIs", "getIs", false);
        verifyBooleanSetterName("isIs", "setIs", false);
    }

    private void verifyBooleanGetterName(String fieldName, String getterName, boolean primitive) {
        assertEquals(getterName, getGetterSetterName(fieldName, false, true, primitive));
    }

    private void verifyBooleanSetterName(String fieldName, String setterName, boolean primitive) {
        assertEquals(setterName, getGetterSetterName(fieldName, true, true, primitive));
    }
}