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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flipkart.lyrics.java.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.InclusionType;
import com.flipkart.lyrics.model.SubTypeModel;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.java.specs.AnnotationSpec;
import com.flipkart.lyrics.java.specs.FieldSpec;
import com.flipkart.lyrics.java.specs.TypeSpec;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 06/06/17.
 */
@ExtendWith(ConfigurationExtension.class)
public class JacksonStyleTest {

    @Test
    public void testNamedAsRule(@TuneProvider Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);
        when(model.getNamedAs()).thenReturn("TEST");

        new JacksonStyle().processNamedAsRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(1, fieldSpec.annotations.size());

        AnnotationSpec namedAsAnnotation = fieldSpec.annotations.get(0);
        assertEquals(JsonProperty.class.getName(), namedAsAnnotation.type.toString());
        assertEquals(1, namedAsAnnotation.members.size());
        assertEquals("\"TEST\"", namedAsAnnotation.members.get("value").get(0).toString());
    }

    @Test
    public void testInclusionRule(@TuneProvider Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);
        when(model.getInclusion()).thenReturn(InclusionType.NON_NULL);

        new JacksonStyle().processInclusionRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(1, fieldSpec.annotations.size());

        AnnotationSpec inclusionAnnotation = fieldSpec.annotations.get(0);
        assertEquals(JsonSerialize.class.getName(), inclusionAnnotation.type.toString());
        assertEquals(1, inclusionAnnotation.members.size());
        assertEquals("JsonSerialize.Inclusion.NON_NULL", inclusionAnnotation.members.get("include").get(0).toString());
    }

    @Test
    public void testGlobalInclusionRule(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getInclusion()).thenReturn(InclusionType.NON_NULL);

        new JacksonStyle().processGlobalInclusionRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(1, typeSpec.annotations.size());

        AnnotationSpec inclusionAnnotation = typeSpec.annotations.get(0);
        assertEquals(JsonSerialize.class.getName(), inclusionAnnotation.type.toString());
        assertEquals(1, inclusionAnnotation.members.size());
        assertEquals("JsonSerialize.Inclusion.NON_NULL", inclusionAnnotation.members.get("include").get(0).toString());
    }

    @Test
    public void processSubTypeRule(@TuneProvider Tune tune) {
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
        assertEquals(JsonTypeInfo.class.getName(), typeInfoAnnotation.type.toString());
        assertEquals(3, typeInfoAnnotation.members.size());
        assertEquals("JsonTypeInfo.Id.NAME", typeInfoAnnotation.members.get("use").get(0).toString());
        assertEquals("JsonTypeInfo.As.PROPERTY", typeInfoAnnotation.members.get("include").get(0).toString());
        assertEquals("\"one\"", typeInfoAnnotation.members.get("property").get(0).toString());

        AnnotationSpec subTypesAnnotation = typeSpec.annotations.get(1);
        assertEquals(JsonSubTypes.class.getName(), subTypesAnnotation.type.toString());
        assertEquals(1, subTypesAnnotation.members.size());
        assertEquals(2, subTypesAnnotation.members.get("value").size());
        assertEquals("@com.fasterxml.jackson.annotation.JsonSubTypes.Type(name = \"A\", value = com.flipkart.lyrics.A.class)", subTypesAnnotation.members.get("value").get(0).toString());
        assertEquals("@com.fasterxml.jackson.annotation.JsonSubTypes.Type(name = \"B\", value = com.flipkart.lyrics.B.class)", subTypesAnnotation.members.get("value").get(1).toString());
    }

    @Test
    public void processSubTypeRuleExistingProperty(@TuneProvider Tune tune) {
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
        assertEquals(JsonTypeInfo.class.getName(), typeInfoAnnotation.type.toString());
        assertEquals(3, typeInfoAnnotation.members.size());
        assertEquals("JsonTypeInfo.Id.NAME", typeInfoAnnotation.members.get("use").get(0).toString());
        assertEquals("JsonTypeInfo.As.EXISTING_PROPERTY", typeInfoAnnotation.members.get("include").get(0).toString());
        assertEquals("\"one\"", typeInfoAnnotation.members.get("property").get(0).toString());

        AnnotationSpec subTypesAnnotation = typeSpec.annotations.get(1);
        assertEquals(JsonSubTypes.class.getName(), subTypesAnnotation.type.toString());
        assertEquals(1, subTypesAnnotation.members.size());
        assertEquals(2, subTypesAnnotation.members.get("value").size());
        assertEquals("@com.fasterxml.jackson.annotation.JsonSubTypes.Type(name = \"A\", value = com.flipkart.lyrics.A.class)", subTypesAnnotation.members.get("value").get(0).toString());
        assertEquals("@com.fasterxml.jackson.annotation.JsonSubTypes.Type(name = \"B\", value = com.flipkart.lyrics.B.class)", subTypesAnnotation.members.get("value").get(1).toString());
    }

    @Test
    public void testPropertyOrderRule(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getFieldOrder()).thenReturn(Arrays.asList("one", "two"));

        new JacksonStyle().processPropertyOrderRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(1, typeSpec.annotations.size());

        AnnotationSpec orderAnnotation = typeSpec.annotations.get(0);
        assertEquals(JsonPropertyOrder.class.getName(), orderAnnotation.type.toString());
        assertEquals(1, orderAnnotation.members.size());
        assertEquals(2, orderAnnotation.members.get("value").size());
        assertEquals("\"one\"", orderAnnotation.members.get("value").get(0).toString());
        assertEquals("\"two\"", orderAnnotation.members.get("value").get(1).toString());
    }
}