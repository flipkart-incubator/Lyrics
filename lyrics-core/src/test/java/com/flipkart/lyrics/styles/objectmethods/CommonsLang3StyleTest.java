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
//package com.flipkart.lyrics.styles.objectmethods;
//
//import com.flipkart.lyrics.model.MetaInfo;
//import com.squareup.javapoet.MethodSpec;
//import org.junit.jupiter.api.Test;
//
//import javax.lang.model.element.Modifier;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
///**
// * Created by shrey.garg on 06/06/17.
// */
//public class CommonsLang3StyleTest {
//
//    @Test
//    public void testProcessToString() {
//        MethodSpec.Builder toStringBuilder = MethodSpec.methodBuilder("toString")
//                .addModifiers(Modifier.PUBLIC)
//                .returns(String.class);
//
//        List<String> fields = Arrays.asList("one", "two");
//        MetaInfo metaInfo = new MetaInfo("Test", "com.flipkart.lyrics");
//
//        CommonsLang3Style style = new CommonsLang3Style();
//        style.processToString(toStringBuilder, fields, metaInfo);
//
//        MethodSpec toString = toStringBuilder.build();
//        assertEquals("toString", toString.name);
//        assertEquals(0, toString.parameters.size());
//        assertEquals(
//                "return new org.apache.commons.lang3.builder.ToStringBuilder(this)\n" +
//                        "\t\t.append(\"one\", one)\n" +
//                        "\t\t.append(\"two\", two)\n" +
//                        "\t\t.toString();\n", toString.code.toString());
//    }
//
//    @Test
//    public void testProcessToStringNoFields() {
//        MethodSpec.Builder toStringBuilder = MethodSpec.methodBuilder("toString")
//                .addModifiers(Modifier.PUBLIC)
//                .returns(String.class);
//
//        MetaInfo metaInfo = new MetaInfo("Test", "com.flipkart.lyrics");
//
//        CommonsLang3Style style = new CommonsLang3Style();
//        style.processToString(toStringBuilder, new ArrayList<>(), metaInfo);
//
//        MethodSpec toString = toStringBuilder.build();
//        assertEquals("toString", toString.name);
//        assertEquals(0, toString.parameters.size());
//        assertEquals(
//                "return new org.apache.commons.lang3.builder.ToStringBuilder(this)\n" +
//                        "\t\t.toString();\n", toString.code.toString());
//    }
//
//    @Test
//    public void testProcessEqualsAndHashCode() {
//        MethodSpec.Builder equalsBuilder = MethodSpec.methodBuilder("equals")
//                .addModifiers(Modifier.PUBLIC)
//                .returns(boolean.class)
//                .addAnnotation(Override.class)
//                .addParameter(Object.class, "o");
//
//        MethodSpec.Builder hashCodeBuilder = MethodSpec.methodBuilder("hashCode")
//                .addModifiers(Modifier.PUBLIC)
//                .returns(int.class)
//                .addAnnotation(Override.class);
//
//        List<String> fields = Arrays.asList("one", "two");
//        MetaInfo metaInfo = new MetaInfo("Test", "com.flipkart.lyrics");
//
//        CommonsLang3Style style = new CommonsLang3Style();
//        style.processEqualsAndHashCode(equalsBuilder, hashCodeBuilder, fields, metaInfo, true);
//
//        MethodSpec equals = equalsBuilder.build();
//        MethodSpec hashCode = hashCodeBuilder.build();
//
//        assertEquals(
//                "if (this == o) return true;\n" +
//                        "if (!(o instanceof Test)) return false;\n" +
//                        "\n" +
//                        "Test that = (Test) o;\n" +
//                        "\n" +
//                        "return new org.apache.commons.lang3.builder.EqualsBuilder()\n" +
//                        "\t\t.appendSuper(super.equals(that))\n" +
//                        "\t\t.append(one, that.one)\n" +
//                        "\t\t.append(two, that.two)\n" +
//                        "\t\t.isEquals();\n", equals.code.toString());
//
//        assertEquals(
//                "return new org.apache.commons.lang3.builder.HashCodeBuilder()\n" +
//                        "\t\t.appendSuper(super.hashCode())\n" +
//                        "\t\t.append(one)\n" +
//                        "\t\t.append(two)\n" +
//                        "\t\t.toHashCode();\n", hashCode.code.toString());
//    }
//
//    @Test
//    public void testProcessEqualsAndHashCodeNoSuperEquality() {
//        MethodSpec.Builder equalsBuilder = MethodSpec.methodBuilder("equals")
//                .addModifiers(Modifier.PUBLIC)
//                .returns(boolean.class)
//                .addAnnotation(Override.class)
//                .addParameter(Object.class, "o");
//
//        MethodSpec.Builder hashCodeBuilder = MethodSpec.methodBuilder("hashCode")
//                .addModifiers(Modifier.PUBLIC)
//                .returns(int.class)
//                .addAnnotation(Override.class);
//
//        List<String> fields = Arrays.asList("one", "two");
//        MetaInfo metaInfo = new MetaInfo("Test", "com.flipkart.lyrics");
//
//        CommonsLang3Style style = new CommonsLang3Style();
//        style.processEqualsAndHashCode(equalsBuilder, hashCodeBuilder, fields, metaInfo, false);
//
//        MethodSpec equals = equalsBuilder.build();
//        MethodSpec hashCode = hashCodeBuilder.build();
//
//        assertEquals(
//                "if (this == o) return true;\n" +
//                        "if (!(o instanceof Test)) return false;\n" +
//                        "\n" +
//                        "Test that = (Test) o;\n" +
//                        "\n" +
//                        "return new org.apache.commons.lang3.builder.EqualsBuilder()\n" +
//                        "\t\t.append(one, that.one)\n" +
//                        "\t\t.append(two, that.two)\n" +
//                        "\t\t.isEquals();\n", equals.code.toString());
//
//        assertEquals(
//                "return new org.apache.commons.lang3.builder.HashCodeBuilder()\n" +
//                        "\t\t.append(one)\n" +
//                        "\t\t.append(two)\n" +
//                        "\t\t.toHashCode();\n", hashCode.code.toString());
//    }
//
//    @Test
//    public void testProcessEqualsAndHashCodeNoFields() {
//        MethodSpec.Builder equalsBuilder = MethodSpec.methodBuilder("equals")
//                .addModifiers(Modifier.PUBLIC)
//                .returns(boolean.class)
//                .addAnnotation(Override.class)
//                .addParameter(Object.class, "o");
//
//        MethodSpec.Builder hashCodeBuilder = MethodSpec.methodBuilder("hashCode")
//                .addModifiers(Modifier.PUBLIC)
//                .returns(int.class)
//                .addAnnotation(Override.class);
//
//        MetaInfo metaInfo = new MetaInfo("Test", "com.flipkart.lyrics");
//
//        CommonsLang3Style style = new CommonsLang3Style();
//        style.processEqualsAndHashCode(equalsBuilder, hashCodeBuilder, new ArrayList<>(), metaInfo, true);
//
//        MethodSpec equals = equalsBuilder.build();
//        MethodSpec hashCode = hashCodeBuilder.build();
//
//        assertEquals(
//                "if (this == o) return true;\n" +
//                        "if (!(o instanceof Test)) return false;\n" +
//                        "\n" +
//                        "Test that = (Test) o;\n" +
//                        "\n" +
//                        "return new org.apache.commons.lang3.builder.EqualsBuilder()\n" +
//                        "\t\t.appendSuper(super.equals(that))\n" +
//                        "\t\t.isEquals();\n", equals.code.toString());
//
//        assertEquals(
//                "return new org.apache.commons.lang3.builder.HashCodeBuilder()\n" +
//                        "\t\t.appendSuper(super.hashCode())\n" +
//                        "\t\t.toHashCode();\n", hashCode.code.toString());
//    }
//
//}