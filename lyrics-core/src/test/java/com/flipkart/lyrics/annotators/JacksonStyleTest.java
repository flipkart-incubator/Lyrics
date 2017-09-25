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

package com.flipkart.lyrics.annotators;

import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.InclusionType;
import com.flipkart.lyrics.model.SubTypeModel;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.specs.AnnotationSpec;
import com.flipkart.lyrics.specs.FieldSpec;
import com.flipkart.lyrics.specs.TypeSpec;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 06/06/17.
 */
public class JacksonStyleTest {

    @Test
    public void testNamedAsRule() {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);
        when(model.getNamedAs()).thenReturn("TEST");

        new JacksonStyle().processNamedAsRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(1, fieldSpec.annotations.size());

        AnnotationSpec namedAsAnnotation = fieldSpec.annotations.get(0);
        assertEquals(1, namedAsAnnotation.members.size());
    }

    @Test
    public void testInclusionRule() {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);
        when(model.getInclusion()).thenReturn(InclusionType.NON_NULL);

        new JacksonStyle().processInclusionRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(1, fieldSpec.annotations.size());

        AnnotationSpec inclusionAnnotation = fieldSpec.annotations.get(0);
        assertEquals(1, inclusionAnnotation.members.size());
    }

    @Test
    public void testGlobalInclusionRule() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getInclusion()).thenReturn(InclusionType.NON_NULL);

        new JacksonStyle().processGlobalInclusionRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(1, typeSpec.annotations.size());

        AnnotationSpec inclusionAnnotation = typeSpec.annotations.get(0);
        assertEquals(1, inclusionAnnotation.members.size());
    }

    @Test
    public void processSubTypeRule() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);

        Map<String, String> subTypes = new HashMap<>();
        subTypes.put("A", "com.flipkart.lyrics.A");
        subTypes.put("B", "com.flipkart.lyrics.B");
        SubTypeModel subTypeModel = new SubTypeModel("one", subTypes);
        when(model.getSubTypes()).thenReturn(subTypeModel);

        new JacksonStyle().processSubTypeRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(2, typeSpec.annotations.size());

        AnnotationSpec typeInfoAnnotation = typeSpec.annotations.get(0);
        assertEquals(3, typeInfoAnnotation.members.size());

        AnnotationSpec subTypesAnnotation = typeSpec.annotations.get(1);
        assertEquals(1, subTypesAnnotation.members.size());
        assertEquals(2, subTypesAnnotation.members.get("value").size());
    }

    @Test
    public void processSubTypeRuleExistingProperty() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);

        Map<String, String> subTypes = new HashMap<>();
        subTypes.put("A", "com.flipkart.lyrics.A");
        subTypes.put("B", "com.flipkart.lyrics.B");
        SubTypeModel subTypeModel = mock(SubTypeModel.class);
        when(subTypeModel.getProperty()).thenReturn("one");
        when(subTypeModel.getSubTypeMapping()).thenReturn(subTypes);
        when(subTypeModel.isExistingProperty()).thenReturn(true);

        when(model.getSubTypes()).thenReturn(subTypeModel);

        new JacksonStyle().processSubTypeRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(2, typeSpec.annotations.size());

        AnnotationSpec typeInfoAnnotation = typeSpec.annotations.get(0);
        assertEquals(3, typeInfoAnnotation.members.size());
    }

    @Test
    public void testPropertyOrderRule() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getFieldOrder()).thenReturn(Arrays.asList("one", "two"));

        new JacksonStyle().processPropertyOrderRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(1, typeSpec.annotations.size());

        AnnotationSpec orderAnnotation = typeSpec.annotations.get(0);
        assertEquals(1, orderAnnotation.members.size());
        assertEquals(2, orderAnnotation.members.get("value").size());
    }
}