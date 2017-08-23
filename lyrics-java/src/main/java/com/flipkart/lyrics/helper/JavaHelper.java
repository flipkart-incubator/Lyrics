package com.flipkart.lyrics.helper;

import com.flipkart.lyrics.interfaces.typenames.*;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.lang.reflect.Type;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class JavaHelper {
    public static javax.lang.model.element.Modifier[] getJavaModifiers(Modifier... modifiers) {
        javax.lang.model.element.Modifier[] newModifiers = new javax.lang.model.element.Modifier[modifiers.length];
        for (int i = 0; i < modifiers.length; i++) {
            newModifiers[i] = getJavaModifier(modifiers[i]);
        }
        return newModifiers;
    }

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

    public static TypeName getJavaTypeName(com.flipkart.lyrics.interfaces.typenames.TypeName typeName) {
        if (typeName == com.flipkart.lyrics.interfaces.typenames.TypeName.INT) {
            return TypeName.INT;
        } else if (typeName == com.flipkart.lyrics.interfaces.typenames.TypeName.BOOLEAN) {
            return TypeName.BOOLEAN;
        } else if (typeName == com.flipkart.lyrics.interfaces.typenames.TypeName.BYTE) {
            return TypeName.BYTE;
        } else if (typeName == com.flipkart.lyrics.interfaces.typenames.TypeName.CHAR) {
            return TypeName.CHAR;
        } else if (typeName == com.flipkart.lyrics.interfaces.typenames.TypeName.FLOAT) {
            return TypeName.FLOAT;
        } else if (typeName == com.flipkart.lyrics.interfaces.typenames.TypeName.DOUBLE) {
            return TypeName.DOUBLE;
        } else if (typeName == com.flipkart.lyrics.interfaces.typenames.TypeName.LONG) {
            return TypeName.LONG;
        } else if (typeName == com.flipkart.lyrics.interfaces.typenames.TypeName.SHORT) {
            return TypeName.SHORT;
        } else if (typeName == com.flipkart.lyrics.interfaces.typenames.TypeName.VOID) {
            return TypeName.VOID;
        } else if (typeName == com.flipkart.lyrics.interfaces.typenames.TypeName.OBJECT) {
            return TypeName.OBJECT;
        } else if (typeName instanceof ClassName) {
            return getJavaClassName((ClassName) typeName);
        } else if (typeName instanceof ParameterizedTypeName) {
            return getJavaParameterizedTypeName((ParameterizedTypeName) typeName);
        } else if (typeName instanceof ArrayTypeName) {
            return com.squareup.javapoet.ArrayTypeName.of(getJavaTypeName(((ArrayTypeName) typeName).componentType));
        } else if (typeName instanceof com.flipkart.lyrics.interfaces.typenames.TypeVariableName) {
            return getJavaTypeVariableName((com.flipkart.lyrics.interfaces.typenames.TypeVariableName) typeName);
        }
        return TypeName.OBJECT;
    }

    public static com.squareup.javapoet.ClassName getJavaClassName(ClassName className) {
        return com.squareup.javapoet.ClassName.get(className.packageName(), className.simpleName());
    }

    public static com.squareup.javapoet.ParameterizedTypeName getJavaParameterizedTypeName(ParameterizedTypeName parameterizedTypeName) {
        TypeName[] typeNameArray = new TypeName[parameterizedTypeName.typeArguments.size()];
        for (int i = 0; i < parameterizedTypeName.typeArguments.size(); i++) {
            typeNameArray[i] = getJavaTypeName(parameterizedTypeName.typeArguments.get(i));
        }
        return com.squareup.javapoet.ParameterizedTypeName.get(getJavaClassName(parameterizedTypeName.rawType), typeNameArray);
    }

    public static TypeVariableName getJavaTypeVariableName(com.flipkart.lyrics.interfaces.typenames.TypeVariableName typeVariableName) {
        TypeName[] typeNameArray = new TypeName[typeVariableName.bounds.size()];
        for (int i = 0; i < typeVariableName.bounds.size(); i++) {
            typeNameArray[i] = getJavaTypeName(typeVariableName.bounds.get(i));
        }
        return TypeVariableName.get(typeVariableName.name, typeNameArray);
    }
}
