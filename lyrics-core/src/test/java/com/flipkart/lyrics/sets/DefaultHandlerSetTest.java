///*
// * Copyright 2016 Flipkart Internet, pvt ltd.
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
//package com.flipkart.lyrics.sets;
//
//import com.flipkart.lyrics.processor.Handler;
//import com.flipkart.lyrics.processor.annotations.ClassAnnotationHandler;
//import com.flipkart.lyrics.processor.constructors.NoArgsConstructorHandler;
//import com.flipkart.lyrics.processor.constructors.OrderedConstructorHandler;
//import com.flipkart.lyrics.processor.fields.FieldsHandler;
//import com.flipkart.lyrics.processor.generics.GenericsHandler;
//import com.flipkart.lyrics.processor.instances.EnumValuesHandler;
//import com.flipkart.lyrics.processor.methods.AnnotationMethodsHandler;
//import com.flipkart.lyrics.processor.methods.EqualsAndHashCodeHandler;
//import com.flipkart.lyrics.processor.methods.ToStringHandler;
//import com.flipkart.lyrics.processor.modifiers.ModifiersHandler;
//import com.flipkart.lyrics.processor.supertypes.InterfaceHandler;
//import com.flipkart.lyrics.processor.supertypes.SpecialInterfaceHandler;
//import com.flipkart.lyrics.processor.supertypes.SuperClassHandler;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Created by shrey.garg on 02/01/17.
// */
//public class DefaultHandlerSetTest {
//
//    private HandlerSet handlerSet;
//
//    @BeforeEach
//    public void setup() {
//        handlerSet = new DefaultHandlerSet(null, null, null);
//    }
//
//    @Test
//    public void testGetTypeAnnotationHandler() {
//        Handler handler = handlerSet.getTypeAnnotationHandler();
//        assertTrue(handler instanceof ClassAnnotationHandler);
//    }
//
//    @Test
//    public void testGetGenericsHandler() {
//        Handler handler = handlerSet.getGenericsHandler();
//        assertTrue(handler instanceof GenericsHandler);
//    }
//
//    @Test
//    public void testGetModifiersHandler() {
//        Handler handler = handlerSet.getModifiersHandler();
//        assertTrue(handler instanceof ModifiersHandler);
//    }
//
//    @Test
//    public void testGetSuperClassHandler() {
//        Handler handler = handlerSet.getSuperClassHandler();
//        assertTrue(handler instanceof SuperClassHandler);
//    }
//
//    @Test
//    public void testGetInterfacesHandler() {
//        Handler handler = handlerSet.getInterfacesHandler();
//        assertTrue(handler instanceof InterfaceHandler);
//    }
//
//    @Test
//    public void testGetFieldsHandler() {
//        Handler handler = handlerSet.getFieldsHandler();
//        assertTrue(handler instanceof FieldsHandler);
//    }
//
//    @Test
//    public void testGetEqualsAndHashCodeHandler() {
//        Handler handler = handlerSet.getEqualsAndHashCodeHandler();
//        assertTrue(handler instanceof EqualsAndHashCodeHandler);
//    }
//
//    @Test
//    public void testGetToStringHandler() {
//        Handler handler = handlerSet.getToStringHandler();
//        assertTrue(handler instanceof ToStringHandler);
//    }
//
//    @Test
//    public void testGetNoArgsConstructorHandler() {
//        Handler handler = handlerSet.getNoArgsConstructorHandler();
//        assertTrue(handler instanceof NoArgsConstructorHandler);
//    }
//
//    @Test
//    public void testGetSpecialInterfacesHandler() {
//        Handler handler = handlerSet.getSpecialInterfacesHandler();
//        assertTrue(handler instanceof SpecialInterfaceHandler);
//    }
//
//    @Test
//    public void testGetAnnotationMethodsHandler() {
//        Handler handler = handlerSet.getAnnotationMethodsHandler();
//        assertTrue(handler instanceof AnnotationMethodsHandler);
//    }
//
//    @Test
//    public void testGetEnumValuesHandler() {
//        Handler handler = handlerSet.getEnumValuesHandler();
//        assertTrue(handler instanceof EnumValuesHandler);
//    }
//
//    @Test
//    public void testGetOrderedConstructorHandler() {
//        Handler handler = handlerSet.getOrderedConstructorHandler();
//        assertTrue(handler instanceof OrderedConstructorHandler);
//    }
//
//}