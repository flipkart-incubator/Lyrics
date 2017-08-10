package com.flipkart.lyrics.interfaces.model;

import com.flipkart.lyrics.interfaces.AnnotationSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class TypeName {
    public static final TypeName VOID = new TypeName("void");
    public static final TypeName BOOLEAN = new TypeName("boolean");
    public static final TypeName BYTE = new TypeName("byte");
    public static final TypeName SHORT = new TypeName("short");
    public static final TypeName INT = new TypeName("int");
    public static final TypeName LONG = new TypeName("long");
    public static final TypeName CHAR = new TypeName("char");
    public static final TypeName FLOAT = new TypeName("float");
    public static final TypeName DOUBLE = new TypeName("double");
    public static final ClassName OBJECT = ClassName.get("java.lang", "Object");
    private static final ClassName BOXED_VOID = ClassName.get("java.lang", "Void");
    private static final ClassName BOXED_BOOLEAN = ClassName.get("java.lang", "Boolean");
    private static final ClassName BOXED_BYTE = ClassName.get("java.lang", "Byte");
    private static final ClassName BOXED_SHORT = ClassName.get("java.lang", "Short");
    private static final ClassName BOXED_INT = ClassName.get("java.lang", "Integer");
    private static final ClassName BOXED_LONG = ClassName.get("java.lang", "Long");
    private static final ClassName BOXED_CHAR = ClassName.get("java.lang", "Character");
    private static final ClassName BOXED_FLOAT = ClassName.get("java.lang", "Float");
    private static final ClassName BOXED_DOUBLE = ClassName.get("java.lang", "Double");
    private final String keyword;
    public final List<AnnotationSpec> annotations;

    private TypeName(String keyword) {
        this(keyword, new ArrayList<>());
    }

    private TypeName(String keyword, List<AnnotationSpec> annotations) {
        this.keyword = keyword;
        this.annotations = annotations;
    }

    TypeName(List<AnnotationSpec> annotations) {
        this(null, annotations);
    }

    public TypeName box() {
        if (this.keyword == null) {
            return this;
        } else if (this == VOID) {
            return BOXED_VOID;
        } else if (this == BOOLEAN) {
            return BOXED_BOOLEAN;
        } else if (this == BYTE) {
            return BOXED_BYTE;
        } else if (this == SHORT) {
            return BOXED_SHORT;
        } else if (this == INT) {
            return BOXED_INT;
        } else if (this == LONG) {
            return BOXED_LONG;
        } else if (this == CHAR) {
            return BOXED_CHAR;
        } else if (this == FLOAT) {
            return BOXED_FLOAT;
        } else if (this == DOUBLE) {
            return BOXED_DOUBLE;
        } else {
            throw new AssertionError(this.keyword);
        }
    }
}
