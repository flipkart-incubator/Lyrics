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

package com.flipkart.lyrics.rules.type;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.helper.Helper;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.SubTypeModel;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.processAnnotationStyles;
import static com.flipkart.lyrics.test.extensions.ConfigurationExtension.JACKSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 11/12/16.
 */
@ExtendWith(ConfigurationExtension.class)
public class SubTypesRuleTest {

    @Test
    public void testNoSubTypes(@TuneProvider(JACKSON) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);

        new SubTypesRule(tune, null).process(builder, model);

        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong name found.");
        assertEquals(0, spec.annotations.size(), "Annotation not found.");
    }

    @Test
    public void testNoParsers(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        Map<String, String> values = new HashMap<>();
        values.put("abc", "com.abc.xyz");
        SubTypeModel subTypeModel = new SubTypeModel("id", values);
        when(model.getSubTypes()).thenReturn(subTypeModel);

        MetaInfo metaInfo = new MetaInfo(null, null, null, processAnnotationStyles(tune));
        new SubTypesRule(tune, metaInfo).process(builder, model);

        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong name found.");
        assertEquals(0, spec.annotations.size(), "Annotations found.");
    }

    @Test
    public void testSingleKey(@TuneProvider(JACKSON) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        Map<String, String> values = new HashMap<>();
        values.put("abc", "com.abc.xyz");
        SubTypeModel subTypeModel = new SubTypeModel("id", values);
        when(model.getSubTypes()).thenReturn(subTypeModel);

        MetaInfo metaInfo = new MetaInfo(null, null, null, processAnnotationStyles(tune));
        new SubTypesRule(tune, metaInfo).process(builder, model);

        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong name found.");
        assertEquals(2, spec.annotations.size(), "Annotations found.");

        AnnotationSpec typeInfoAnnotationSpec = spec.annotations.get(0);
        verifyJsonTypeInfo(typeInfoAnnotationSpec);

        AnnotationSpec subTypesAnnotationSpec = spec.annotations.get(1);
        assertEquals(JsonSubTypes.class.getName(), subTypesAnnotationSpec.type.toString(), "Wrong annotation found");
        assertEquals(1, subTypesAnnotationSpec.members.size(), "More than one value found");

        List<CodeBlock> subTypeValues = subTypesAnnotationSpec.members.get("value");
        assertNotNull(subTypeValues);
        assertEquals(1, subTypeValues.size(), "Unexpected values found");
    }

    @Test
    public void testMultipleKeys(@TuneProvider(JACKSON) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        Map<String, String> values = new HashMap<>();
        values.put("abc", "com.abc.xyz");
        values.put("xyz", "com.xyz.abc");
        SubTypeModel subTypeModel = new SubTypeModel("id", values);
        when(model.getSubTypes()).thenReturn(subTypeModel);

        MetaInfo metaInfo = new MetaInfo(null, null, null, processAnnotationStyles(tune));
        new SubTypesRule(tune, metaInfo).process(builder, model);

        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong name found.");
        assertEquals(2, spec.annotations.size(), "Annotations found.");

        AnnotationSpec typeInfoAnnotationSpec = spec.annotations.get(0);
        verifyJsonTypeInfo(typeInfoAnnotationSpec);

        AnnotationSpec subTypesAnnotationSpec = spec.annotations.get(1);
        assertEquals(JsonSubTypes.class.getName(), subTypesAnnotationSpec.type.toString(), "Wrong annotation found");
        assertEquals(1, subTypesAnnotationSpec.members.size(), "More than one value found");

        List<CodeBlock> subTypeValues = subTypesAnnotationSpec.members.get("value");
        assertNotNull(subTypeValues);
        assertEquals(2, subTypeValues.size(), "Unexpected values found");
    }

    private void verifyJsonTypeInfo(AnnotationSpec typeInfoAnnotationSpec) {
        assertEquals(JsonTypeInfo.class.getName(), typeInfoAnnotationSpec.type.toString(), "Wrong annotation found.");
        assertEquals(3, typeInfoAnnotationSpec.members.size(), "More than one annotation members found.");

        List<CodeBlock> use = typeInfoAnnotationSpec.members.get("use");
        assertNotNull(use, "Annotation inclusion not found");
        assertEquals("JsonTypeInfo.Id.NAME", use.get(0).toString(), "Wrong value found.");

        List<CodeBlock> include = typeInfoAnnotationSpec.members.get("include");
        assertNotNull(include, "Annotation inclusion not found");
        assertEquals("JsonTypeInfo.As.PROPERTY", include.get(0).toString(), "Wrong value found.");

        List<CodeBlock> property = typeInfoAnnotationSpec.members.get("property");
        assertNotNull(property, "Annotation inclusion not found");
        assertEquals("\"id\"", property.get(0).toString(), "Wrong value found.");
    }

}