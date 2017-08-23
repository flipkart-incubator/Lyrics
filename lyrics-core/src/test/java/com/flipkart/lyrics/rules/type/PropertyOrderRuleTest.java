///*
// * Copyright 2017 Flipkart Internet, pvt ltd.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.flipkart.lyrics.rules.type;
//
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import com.flipkart.lyrics.config.Tune;
//import com.flipkart.lyrics.model.TypeModel;
//import com.flipkart.lyrics.test.annotation.TuneProvider;
//import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
//import com.squareup.javapoet.AnnotationSpec;
//import com.squareup.javapoet.CodeBlock;
//import com.squareup.javapoet.TypeSpec;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static com.flipkart.lyrics.test.extensions.ConfigurationExtension.JACKSON;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
///**
// * Created by shrey.garg on 11/12/16.
// */
//@ExtendWith(ConfigurationExtension.class)
//public class PropertyOrderRuleTest {
//
//    @Test
//    public void testNoPropertyOrder(@TuneProvider(JACKSON) Tune tune) {
//        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
//        TypeModel model = mock(TypeModel.class);
//
//        new PropertyOrderRule(tune, null).process(builder, model);
//
//        TypeSpec spec = builder.build();
//
//        assertEquals("Test", spec.name, "Wrong name found.");
//        assertEquals(0, spec.annotations.size(), "Annotation not found.");
//    }
//
//    @Test
//    public void testNoParsers(@TuneProvider Tune tune) {
//        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
//        TypeModel model = mock(TypeModel.class);
//        List<String> fieldOrder = Arrays.asList("one", "two");
//        when(model.getFieldOrder()).thenReturn(fieldOrder);
//
//        new PropertyOrderRule(tune, null).process(builder, model);
//
//        TypeSpec spec = builder.build();
//
//        assertEquals("Test", spec.name, "Wrong name found.");
//        assertEquals(0, spec.annotations.size(), "Annotations found.");
//    }
//
//    @Test
//    public void testSingleProperty(@TuneProvider(JACKSON) Tune tune) {
//        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
//        TypeModel model = mock(TypeModel.class);
//        List<String> fieldOrder = Collections.singletonList("one");
//        when(model.getFieldOrder()).thenReturn(fieldOrder);
//
//        new PropertyOrderRule(tune, null).process(builder, model);
//
//        TypeSpec spec = builder.build();
//
//        assertEquals("Test", spec.name, "Wrong name found.");
//        assertEquals(1, spec.annotations.size(), "Annotation not found.");
//
//        AnnotationSpec propertyOrderAnnotationSpec = spec.annotations.get(0);
//        assertEquals(JsonPropertyOrder.class.getName(), propertyOrderAnnotationSpec.type.toString(), "Wrong annotation found");
//        assertEquals(1, propertyOrderAnnotationSpec.members.size(), "More than one value found");
//
//        List<CodeBlock> propertyValues = propertyOrderAnnotationSpec.members.get("value");
//        assertNotNull(propertyValues);
//        assertEquals(1, propertyValues.size(), "Unexpected values found");
//    }
//
//    @Test
//    public void testMultipleProperties(@TuneProvider(JACKSON) Tune tune) {
//        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
//        TypeModel model = mock(TypeModel.class);
//        List<String> fieldOrder = Arrays.asList("one", "two");
//        when(model.getFieldOrder()).thenReturn(fieldOrder);
//
//        new PropertyOrderRule(tune, null).process(builder, model);
//
//        TypeSpec spec = builder.build();
//
//        assertEquals("Test", spec.name, "Wrong name found.");
//        assertEquals(1, spec.annotations.size(), "Annotation not found.");
//
//        AnnotationSpec propertyOrderAnnotationSpec = spec.annotations.get(0);
//        assertEquals(JsonPropertyOrder.class.getName(), propertyOrderAnnotationSpec.type.toString(), "Wrong annotation found");
//        assertEquals(1, propertyOrderAnnotationSpec.members.size(), "More than one value found");
//
//        List<CodeBlock> propertyValues = propertyOrderAnnotationSpec.members.get("value");
//        assertNotNull(propertyValues);
//        assertEquals(2, propertyValues.size(), "Unexpected values found");
//        assertAll("Correct property order",
//                () -> assertEquals("\"one\"", propertyValues.get(0).toString()),
//                () -> assertEquals("\"two\"", propertyValues.get(1).toString())
//        );
//    }
//}