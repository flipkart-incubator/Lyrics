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

package com.flipkart.lyrics.styles.objectmethods;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.specs.MethodSpec;
import com.flipkart.lyrics.specs.Modifier;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by shrey.garg on 06/06/17.
 */
@ExtendWith(ConfigurationExtension.class)
public class DefaultStyleTest {

    @Test
    public void testProcessToString(@TuneProvider Tune tune) {
        MethodSpec.Builder toStringBuilder = MethodSpec.methodBuilder("toString")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class);

        List<String> fields = Arrays.asList("one", "two");
        MetaInfo metaInfo = new MetaInfo("Test", "com.flipkart.lyrics");

        DefaultStyle style = new DefaultStyle();
        style.processToString(toStringBuilder, fields, metaInfo);

        MethodSpec toString = toStringBuilder.build();
        assertEquals("toString", toString.name);
        assertEquals(0, toString.parameters.size());
    }

    @Test
    public void testProcessToStringNoFields(@TuneProvider Tune tune) {
        MethodSpec.Builder toStringBuilder = MethodSpec.methodBuilder("toString")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class);

        MetaInfo metaInfo = new MetaInfo("Test", "com.flipkart.lyrics");

        DefaultStyle style = new DefaultStyle();
        style.processToString(toStringBuilder, new ArrayList<>(), metaInfo);

        MethodSpec toString = toStringBuilder.build();
        assertEquals("toString", toString.name);
        assertEquals(0, toString.parameters.size());
    }

    @Test
    public void testProcessEqualsAndHashCode(@TuneProvider Tune tune) {
        MethodSpec.Builder equalsBuilder = MethodSpec.methodBuilder("equals")
                .addModifiers(Modifier.PUBLIC)
                .returns(boolean.class)
                .addAnnotation(Override.class)
                .addParameter(Object.class, "o");

        MethodSpec.Builder hashCodeBuilder = MethodSpec.methodBuilder("hashCode")
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addAnnotation(Override.class);

        List<String> fields = Arrays.asList("one", "two");
        MetaInfo metaInfo = new MetaInfo("Test", "com.flipkart.lyrics");

        DefaultStyle style = new DefaultStyle();
        style.processEqualsAndHashCode(equalsBuilder, hashCodeBuilder, fields, metaInfo, true);

        MethodSpec equals = equalsBuilder.build();
        MethodSpec hashCode = hashCodeBuilder.build();
    }
}