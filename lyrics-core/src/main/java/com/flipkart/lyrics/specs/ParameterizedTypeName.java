/*
 * Copyright (C) 2015 Square, Inc.
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
package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.helper.Util;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static com.flipkart.lyrics.helper.Util.checkArgument;
import static com.flipkart.lyrics.helper.Util.checkNotNull;

public final class ParameterizedTypeName extends TypeName {
    public final ClassName rawType;
    public final List<TypeName> typeArguments;
    private final ParameterizedTypeName enclosingType;

    ParameterizedTypeName(ParameterizedTypeName enclosingType, ClassName rawType,
                          List<TypeName> typeArguments) {
        this(enclosingType, rawType, typeArguments, new ArrayList<>());
    }

    private ParameterizedTypeName(ParameterizedTypeName enclosingType, ClassName rawType,
                                  List<TypeName> typeArguments, List<AnnotationSpec> annotations) {
        super(annotations);
        this.rawType = checkNotNull(rawType, "rawType == null");
        this.enclosingType = enclosingType;
        this.typeArguments = Util.immutableList(typeArguments);

        checkArgument(!this.typeArguments.isEmpty() || enclosingType != null,
                "no type arguments: %s", rawType);
        for (TypeName typeArgument : this.typeArguments) {
            checkArgument(!typeArgument.isPrimitive() && typeArgument != VOID,
                    "invalid type parameter: %s", typeArgument);
        }
    }

    /**
     * Returns a parameterized type, applying {@code typeArguments} to {@code rawType}.
     */
    public static ParameterizedTypeName get(ClassName rawType, TypeName... typeArguments) {
        return new ParameterizedTypeName(null, rawType, Arrays.asList(typeArguments));
    }

    /**
     * Returns a parameterized type, applying {@code typeArguments} to {@code rawType}.
     */
    public static ParameterizedTypeName get(Class<?> rawType, Type... typeArguments) {
        return new ParameterizedTypeName(null, ClassName.get(rawType), list(typeArguments));
    }

    /**
     * Returns a parameterized type equivalent to {@code type}.
     */
    public static ParameterizedTypeName get(ParameterizedType type) {
        return get(type, new LinkedHashMap<>());
    }

    /**
     * Returns a parameterized type equivalent to {@code type}.
     */
    static ParameterizedTypeName get(ParameterizedType type, Map<Type, TypeVariableName> map) {
        ClassName rawType = ClassName.get((Class<?>) type.getRawType());
        ParameterizedType ownerType = (type.getOwnerType() instanceof ParameterizedType)
                && !Modifier.isStatic(((Class<?>) type.getRawType()).getModifiers())
                ? (ParameterizedType) type.getOwnerType() : null;
        List<TypeName> typeArguments = TypeName.list(type.getActualTypeArguments(), map);
        return (ownerType != null)
                ? get(ownerType, map).nestedClass(rawType.simpleName(), typeArguments)
                : new ParameterizedTypeName(null, rawType, typeArguments);
    }

    @Override
    public ParameterizedTypeName annotated(List<AnnotationSpec> annotations) {
        return new ParameterizedTypeName(
                enclosingType, rawType, typeArguments, concatAnnotations(annotations));
    }

    @Override
    public TypeName withoutAnnotations() {
        return new ParameterizedTypeName(
                enclosingType, rawType, typeArguments, new ArrayList<>());
    }

    /**
     * Returns a new {@link ParameterizedTypeName} instance for the specified {@code name} as nested
     * inside this class.
     */
    public ParameterizedTypeName nestedClass(String name) {
        checkNotNull(name, "name == null");
        return new ParameterizedTypeName(this, rawType.nestedClass(name), new ArrayList<TypeName>(),
                new ArrayList<>());
    }

    /**
     * Returns a new {@link ParameterizedTypeName} instance for the specified {@code name} as nested
     * inside this class, with the specified {@code typeArguments}.
     */
    public ParameterizedTypeName nestedClass(String name, List<TypeName> typeArguments) {
        checkNotNull(name, "name == null");
        return new ParameterizedTypeName(this, rawType.nestedClass(name), typeArguments,
                new ArrayList<>());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParameterizedTypeName)) return false;
        ParameterizedTypeName that = (ParameterizedTypeName) o;
        return rawType.equals((that.rawType)) && typeArguments.equals(that.typeArguments)
                && (enclosingType == null || enclosingType.equals(that.enclosingType));
    }

    @Override
    public final int hashCode() {
        int hashCode = rawType.hashCode();
        hashCode += 31 * typeArguments.hashCode();
        hashCode += 31 * enclosingType.hashCode();
        return hashCode;
    }
}