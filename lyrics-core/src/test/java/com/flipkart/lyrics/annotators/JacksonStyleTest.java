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
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.interfaces.AnnotationSpec;
import com.flipkart.lyrics.interfaces.FieldSpec;
import com.flipkart.lyrics.interfaces.TypeSpec;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.InclusionType;
import com.flipkart.lyrics.model.SubTypeModel;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        assertEquals(1, fieldSpec.annotationSpecs.size());

        AnnotationSpec namedAsAnnotation = fieldSpec.annotationSpecs.get(0);
        assertEquals(JsonProperty.class.getName(), namedAsAnnotation.clazz.getName());
        assertEquals(1, namedAsAnnotation.members.size());
        assertEquals("TEST", namedAsAnnotation.members.get(0).getArgs()[0]);
    }

    @Test
    public void testInclusionRule(@TuneProvider Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);
        when(model.getInclusion()).thenReturn(InclusionType.NON_NULL);

        new JacksonStyle().processInclusionRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(1, fieldSpec.annotationSpecs.size());

        AnnotationSpec inclusionAnnotation = fieldSpec.annotationSpecs.get(0);
        assertEquals(JsonSerialize.class.getName(), inclusionAnnotation.clazz.getName());
        assertEquals(1, inclusionAnnotation.members.size());
    }

    @Test
    public void testGlobalInclusionRule(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getInclusion()).thenReturn(InclusionType.NON_NULL);

        new JacksonStyle().processGlobalInclusionRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(1, typeSpec.annotationSpecs.size());

        AnnotationSpec inclusionAnnotation = typeSpec.annotationSpecs.get(0);
        assertEquals(JsonSerialize.class.getName(), inclusionAnnotation.clazz.getName());
        assertEquals(1, inclusionAnnotation.members.size());
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
        assertEquals(2, typeSpec.annotationSpecs.size());

        AnnotationSpec typeInfoAnnotation = typeSpec.annotationSpecs.get(0);
        assertEquals(JsonTypeInfo.class.getName(), typeInfoAnnotation.clazz.getName());
        assertEquals(3, typeInfoAnnotation.members.size());
        assertEquals("JsonTypeInfo.Id.NAME", typeInfoAnnotation.members.get(0).getArgs()[0]);
        assertEquals("JsonTypeInfo.As.PROPERTY", typeInfoAnnotation.members.get(1).getArgs()[0]);
        assertEquals("one", typeInfoAnnotation.members.get(2).getArgs()[0]);

        AnnotationSpec subTypesAnnotation = typeSpec.annotationSpecs.get(1);
        assertEquals(JsonSubTypes.class.getName(), subTypesAnnotation.clazz.getName());
        assertEquals(1, subTypesAnnotation.members.get(0).getArgs().length);
        assertEquals(1, subTypesAnnotation.members.get(1).getArgs().length);
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
        assertEquals(2, typeSpec.annotationSpecs.size());

        AnnotationSpec typeInfoAnnotation = typeSpec.annotationSpecs.get(0);
        assertEquals(JsonTypeInfo.class.getName(), typeInfoAnnotation.clazz.getName());
        assertEquals(3, typeInfoAnnotation.members.size());
        assertEquals("JsonTypeInfo.Id.NAME", typeInfoAnnotation.members.get(0).getArgs()[0]);
        assertEquals("JsonTypeInfo.As.EXISTING_PROPERTY", typeInfoAnnotation.members.get(1).getArgs()[0]);
        assertEquals("one", typeInfoAnnotation.members.get(2).getArgs()[0]);

        AnnotationSpec subTypesAnnotation = typeSpec.annotationSpecs.get(1);
        assertEquals(JsonSubTypes.class.getName(), subTypesAnnotation.clazz.getName());
        assertEquals(1, subTypesAnnotation.members.get(0).getArgs().length);
        assertEquals(1, subTypesAnnotation.members.get(1).getArgs().length);
    }

    @Test
    public void testPropertyOrderRule(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getFieldOrder()).thenReturn(Arrays.asList("one", "two"));

        new JacksonStyle().processPropertyOrderRule(builder, model);

        TypeSpec typeSpec = builder.build();
        assertEquals("Test", typeSpec.name);
        assertEquals(1, typeSpec.annotationSpecs.size());

        AnnotationSpec orderAnnotation = typeSpec.annotationSpecs.get(0);
        assertEquals(JsonPropertyOrder.class.getName(), orderAnnotation.clazz.getName());
        assertEquals(1, orderAnnotation.members.get(0).getArgs().length);
        assertEquals(1, orderAnnotation.members.get(1).getArgs().length);
        assertEquals("one", orderAnnotation.members.get(0).getArgs()[0]);
        assertEquals("two", orderAnnotation.members.get(1).getArgs()[0]);
    }
}