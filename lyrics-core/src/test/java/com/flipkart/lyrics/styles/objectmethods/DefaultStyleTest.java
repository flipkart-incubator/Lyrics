package com.flipkart.lyrics.styles.objectmethods;

import com.flipkart.lyrics.model.MetaInfo;
import com.squareup.javapoet.MethodSpec;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by shrey.garg on 06/06/17.
 */
public class DefaultStyleTest {

    @Test
    public void testProcessToString() {
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
        assertEquals(
                "final StringBuilder sb = new StringBuilder(\"Test{\");\n" +
                "sb.append(\"one=\").append(one);\n" +
                "sb.append(\", two=\").append(two);;\n" +
                "sb.append('}');\n" +
                "return sb.toString();\n", toString.code.toString());
    }

    @Test
    public void testProcessToStringNoFields() {
        MethodSpec.Builder toStringBuilder = MethodSpec.methodBuilder("toString")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class);

        MetaInfo metaInfo = new MetaInfo("Test", "com.flipkart.lyrics");

        DefaultStyle style = new DefaultStyle();
        style.processToString(toStringBuilder, new ArrayList<>(), metaInfo);

        MethodSpec toString = toStringBuilder.build();
        assertEquals("toString", toString.name);
        assertEquals(0, toString.parameters.size());
        assertEquals(
                "final StringBuilder sb = new StringBuilder(\"Test{\");\n" +
                "sb.append('}');\n" +
                "return sb.toString();\n", toString.code.toString());
    }

    @Test
    public void testProcessEqualsAndHashCode() {
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

        assertEquals(
                "// Currently not supported.\n" +
                "return false;\n", equals.code.toString());

        assertEquals(
                "// Currently not supported.\n" +
                        "return 0;\n", hashCode.code.toString());
    }

}