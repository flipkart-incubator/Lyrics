package com.flipkart.lyrics.java.helper;

import com.flipkart.lyrics.java.specs.*;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class JavaHelper {
    public static javax.lang.model.element.Modifier[] getJavaModifiers(Modifier... modifiers) {
        javax.lang.model.element.Modifier[] javaModifiers = new javax.lang.model.element.Modifier[modifiers.length];
        for (int i = 0; i < modifiers.length; i++) {
            javaModifiers[i] = getJavaModifier(modifiers[i]);
        }
        return javaModifiers;
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

    public static com.squareup.javapoet.TypeName getJavaTypeName(TypeName typeName) {
        if (typeName == TypeName.INT) {
            return com.squareup.javapoet.TypeName.INT;
        } else if (typeName == TypeName.BOOLEAN) {
            return com.squareup.javapoet.TypeName.BOOLEAN;
        } else if (typeName == TypeName.BYTE) {
            return com.squareup.javapoet.TypeName.BYTE;
        } else if (typeName == TypeName.CHAR) {
            return com.squareup.javapoet.TypeName.CHAR;
        } else if (typeName == TypeName.FLOAT) {
            return com.squareup.javapoet.TypeName.FLOAT;
        } else if (typeName == TypeName.DOUBLE) {
            return com.squareup.javapoet.TypeName.DOUBLE;
        } else if (typeName == TypeName.LONG) {
            return com.squareup.javapoet.TypeName.LONG;
        } else if (typeName == TypeName.SHORT) {
            return com.squareup.javapoet.TypeName.SHORT;
        } else if (typeName == TypeName.VOID) {
            return com.squareup.javapoet.TypeName.VOID;
        } else if (typeName == TypeName.OBJECT) {
            return com.squareup.javapoet.TypeName.OBJECT;
        } else if (typeName instanceof ClassName) {
            return getJavaClassName(typeName);
        } else if (typeName instanceof ParameterizedTypeName) {
            return getJavaParameterizedTypeName((ParameterizedTypeName) typeName);
        } else if (typeName instanceof ArrayTypeName) {
            return com.squareup.javapoet.ArrayTypeName.of(getJavaTypeName(((ArrayTypeName) typeName).componentType));
        } else if (typeName instanceof TypeVariableName) {
            return getJavaTypeVariableName((TypeVariableName) typeName);
        }
        return com.squareup.javapoet.TypeName.OBJECT;
    }

    public static com.squareup.javapoet.ClassName getJavaClassName(TypeName typeName) {
        if (typeName instanceof ClassName) {
            ClassName className = (ClassName) typeName;
            return com.squareup.javapoet.ClassName.get(className.packageName(), className.simpleName());
        } else return null;
    }

    public static com.squareup.javapoet.ParameterizedTypeName getJavaParameterizedTypeName(ParameterizedTypeName parameterizedTypeName) {
        com.squareup.javapoet.TypeName[] typeNameArray = new com.squareup.javapoet.TypeName[parameterizedTypeName.typeArguments.size()];
        for (int i = 0; i < parameterizedTypeName.typeArguments.size(); i++) {
            typeNameArray[i] = getJavaTypeName(parameterizedTypeName.typeArguments.get(i));
        }
        return com.squareup.javapoet.ParameterizedTypeName.get(getJavaClassName(parameterizedTypeName.rawType), typeNameArray);
    }

    public static com.squareup.javapoet.TypeVariableName getJavaTypeVariableName(TypeVariableName typeVariableName) {
        com.squareup.javapoet.TypeName[] typeNameArray = new com.squareup.javapoet.TypeName[typeVariableName.bounds.size()];
        for (int i = 0; i < typeVariableName.bounds.size(); i++) {
            typeNameArray[i] = getJavaTypeName(typeVariableName.bounds.get(i));
        }
        return com.squareup.javapoet.TypeVariableName.get(typeVariableName.name, typeNameArray);
    }
}
