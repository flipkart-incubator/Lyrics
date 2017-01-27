/*
 * Copyright 2016 Flipkart Internet, pvt ltd.
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

package com.flipkart.lyrics.helper;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.creator.TypeCreator;
import com.flipkart.lyrics.model.*;
import com.flipkart.lyrics.processor.fields.FieldTypeHandler;
import com.flipkart.lyrics.sets.CreatorSet;
import com.flipkart.lyrics.sets.FieldTypeHandlerSet;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;
import java.util.*;

/**
 * Created by shrey.garg on 25/11/16.
 */
public class Helper {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static TypeName getClassName(String name, Map<String, TypeVariableName> typeVariableNames) {
        int lastIndex = name.lastIndexOf(".");
        if (lastIndex < 0) {
            return typeVariableNames.get(name);
        }

        return ClassName.get(name.substring(0, lastIndex), name.substring(lastIndex + 1));
    }

    public static ClassName getClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return ClassName.get(name.substring(0, lastIndex), name.substring(lastIndex + 1));
    }

    private static String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String getGetterSetterName(String name, boolean isSetter, boolean isBoolean, boolean isPrimitive) {
        String capitalizedName = capitalize(name);

        String prefix = "get";

        if (isBoolean) {
            if (capitalizedName.startsWith("Is")) {
                if (capitalizedName.length() > 2) {
                    capitalizedName = capitalizedName.substring(2);
                } else if (isPrimitive && !isSetter) {
                    capitalizedName = "";
                }
            }

            if (isPrimitive) {
                prefix = "is";
            }
        }

        if (isSetter) {
            prefix = "set";
        }

        return prefix + capitalizedName;
    }

    public static TypeName getResolvedTypeName(VariableModel variableModel, final Map<String, TypeVariableName> typeVariableNames) {
        TypeName typeName;
        if (variableModel.getTypes().length > 0) {
            ClassName className = getClassName(variableModel.getType());

            TypeName[] typeNames = new TypeName[variableModel.getTypes().length];
            for (int i = 0; i < variableModel.getTypes().length; i++) {
                typeNames[i] = getResolvedTypeName(variableModel.getTypes()[i], typeVariableNames);
            }

            typeName = ParameterizedTypeName.get(className, typeNames);
        } else {
            typeName = getClassName(variableModel.getType(), typeVariableNames);
        }

        return typeName;
    }

    public static Map<String, TypeVariableName> getTypeVariables(List<GenericVariableModel> genericVariables) {
        Map<String, TypeVariableName> typeVariableNames = new HashMap<>();
        for (GenericVariableModel variable : genericVariables) {
            List<TypeName> resolvedBounds = new ArrayList<>();
            for (VariableModel variableModel : variable.getBounds()) {
                if (variableModel != null) {
                    resolvedBounds.add(getResolvedTypeName(variableModel, typeVariableNames));
                }
            }

            TypeVariableName typeVariableName = TypeVariableName.get(variable.getName());
            if (!resolvedBounds.isEmpty()) {
                typeVariableName = typeVariableName.withBounds(resolvedBounds);
            }

            typeVariableNames.put(variable.getName(), typeVariableName);
        }
        return typeVariableNames;
    }

    public static Modifier[] resolveModifiers(Tune tune, FieldModel fieldModel) {
        if (tune.getDefaultFieldModifier() == null) {
            return fieldModel.getModifiers();
        }

        if (tune.forceDefaultFieldModifiers() || (fieldModel.getModifiers().length == 0 && !fieldModel.isPackageVisibility())) {
            return new Modifier[] { tune.getDefaultFieldModifier() };
        }

        return fieldModel.getModifiers();
    }

    public static TypeCreator getCreator(Type type, CreatorSet creatorSet) {
        switch (type) {
            case ANNOTATION:
                return creatorSet.getAnnotationCreator();
            case CLASS:
                return creatorSet.getClassCreator();
            case ENUM:
                return creatorSet.getEnumCreator();
            case INTERFACE:
                return creatorSet.getInterfaceCreator();
            default:
                return creatorSet.getClassCreator();
        }
    }

    public static FieldTypeHandler getFieldTypeHandler(FieldType type, FieldTypeHandlerSet handlerSet) {
        switch (type) {
            case BOOLEAN:
                return handlerSet.getBooleanTypeHandler();
            case BYTE:
                return handlerSet.getByteTypeHandler();
            case CHARACTER:
                return handlerSet.getCharacterTypeHandler();
            case DOUBLE:
                return handlerSet.getDoubleTypeHandler();
            case FLOAT:
                return handlerSet.getFloatTypeHandler();
            case INTEGER:
                return handlerSet.getIntegerTypeHandler();
            case LONG:
                return handlerSet.getLongTypeHandler();
            case SHORT:
                return handlerSet.getShortTypeHandler();
            case OBJECT:
                return handlerSet.getObjectTypeHandler();
            case STRING:
                return handlerSet.getStringTypeHandler();
            case ENUM:
                return handlerSet.getEnumTypeHandler();
            default:
                return handlerSet.getObjectTypeHandler();
        }
    }

    public static TypeName processType(FieldModel fieldModel, Map<String, TypeVariableName> typeVariableNames) {
        if (fieldModel == null) {
            return TypeName.VOID;
        }

        Primitive primitive;
        switch (fieldModel.getFieldType()) {
            case STRING:
                return TypeName.get(String.class);
            case ENUM:
            case OBJECT:
                TypeName typeName;
                if (fieldModel.getType().getType() == null) {
                    typeName = TypeName.OBJECT;
                } else {
                    typeName = getResolvedTypeName(fieldModel.getType(), typeVariableNames);
                }
                return typeName;
            case SHORT:
                primitive = Primitive.SHORT;
                break;
            case INTEGER:
                primitive = Primitive.INTEGER;
                break;
            case LONG:
                primitive = Primitive.LONG;
                break;
            case FLOAT:
                primitive = Primitive.FLOAT;
                break;
            case DOUBLE:
                primitive = Primitive.DOUBLE;
                break;
            case BYTE:
                primitive = Primitive.BYTE;
                break;
            case BOOLEAN:
                primitive = Primitive.BOOLEAN;
                break;
            case CHARACTER:
                primitive = Primitive.CHARACTER;
                break;
            default:
                primitive = Primitive.BYTE;
                break;

        }

        return TypeName.get(fieldModel.isPrimitive() ? primitive.getUnboxed() : primitive.getBoxed());
    }
}
