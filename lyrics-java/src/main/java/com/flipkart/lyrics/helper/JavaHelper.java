package com.flipkart.lyrics.helper;

import com.flipkart.lyrics.Modifier;
import com.squareup.javapoet.TypeName;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class JavaHelper {
    public static javax.lang.model.element.Modifier getJavaModifier(Modifier modifier) {
        switch (modifier) {
            case PUBLIC:
                return javax.lang.model.element.Modifier.PUBLIC;
            case PROTECTED:
                return javax.lang.model.element.Modifier.PROTECTED;
            case PRIVATE:
                return javax.lang.model.element.Modifier.PRIVATE;
            case ABSTRACT:
                return javax.lang.model.element.Modifier.ABSTRACT;
            case DEFAULT:
                return javax.lang.model.element.Modifier.DEFAULT;
            case STATIC:
                return javax.lang.model.element.Modifier.STATIC;
            case FINAL:
                return javax.lang.model.element.Modifier.FINAL;
            case TRANSIENT:
                return javax.lang.model.element.Modifier.TRANSIENT;
            case VOLATILE:
                return javax.lang.model.element.Modifier.VOLATILE;
            case SYNCHRONIZED:
                return javax.lang.model.element.Modifier.SYNCHRONIZED;
            case NATIVE:
                return javax.lang.model.element.Modifier.NATIVE;
            case STRICTFP:
                return javax.lang.model.element.Modifier.STRICTFP;
            default:
                return null;
        }
    }

    public static TypeName getJavaTypeName(com.flipkart.lyrics.interfaces.model.TypeName typeName) {
        if (typeName == com.flipkart.lyrics.interfaces.model.TypeName.INT) {
            return TypeName.INT;
        }
        else if (typeName == com.flipkart.lyrics.interfaces.model.TypeName.BOOLEAN) {
            return TypeName.BOOLEAN;
        }
        else if (typeName == com.flipkart.lyrics.interfaces.model.TypeName.BYTE) {
            return TypeName.BYTE;
        }
        else if (typeName == com.flipkart.lyrics.interfaces.model.TypeName.CHAR) {
            return TypeName.CHAR;
        }
        else if (typeName == com.flipkart.lyrics.interfaces.model.TypeName.FLOAT) {
            return TypeName.FLOAT;
        }
        else if (typeName == com.flipkart.lyrics.interfaces.model.TypeName.DOUBLE) {
            return TypeName.DOUBLE;
        }
        else if (typeName == com.flipkart.lyrics.interfaces.model.TypeName.LONG) {
            return TypeName.LONG;
        }
        else if (typeName == com.flipkart.lyrics.interfaces.model.TypeName.SHORT) {
            return TypeName.SHORT;
        }
        else if (typeName == com.flipkart.lyrics.interfaces.model.TypeName.VOID) {
            return TypeName.VOID;
        }
        else if (typeName == com.flipkart.lyrics.interfaces.model.TypeName.OBJECT) {
            return TypeName.OBJECT;
        }
        return null;
    }
}
