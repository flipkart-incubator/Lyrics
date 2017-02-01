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

package com.flipkart.lyrics.model;

import javax.lang.model.element.Modifier;

/**
 * Created by shrey.garg on 25/11/16.
 */
public class FieldModel {
    private String namedAs;
    private FieldType fieldType;
    private VariableModel type = new VariableModel();
    private boolean primitive;
    private boolean array;
    private Modifier[] modifiers = new Modifier[0];
    private boolean packageVisibility;
    private InclusionType inclusion;
    private boolean required;
    private boolean excludeFromToString;
    private boolean excludeFromEqualsAndHashCode;
    private InitializerModel initializeWith;
    private boolean deprecated;

    public FieldModel() {
    }

    public FieldModel(String namedAs, FieldType fieldType, VariableModel type, boolean primitive, Modifier[] modifiers, boolean packageVisibility, InclusionType inclusion, boolean required, boolean excludeFromToString, boolean excludeFromEqualsAndHashCode, boolean array, InitializerModel initializeWith, boolean deprecated) {
        this.namedAs = namedAs;
        this.fieldType = fieldType;
        this.type = type;
        this.primitive = primitive;
        this.modifiers = modifiers;
        this.packageVisibility = packageVisibility;
        this.inclusion = inclusion;
        this.required = required;
        this.excludeFromToString = excludeFromToString;
        this.excludeFromEqualsAndHashCode = excludeFromEqualsAndHashCode;
        this.array = array;
        this.initializeWith = initializeWith;
        this.deprecated = deprecated;
    }

    public String getNamedAs() {
        return namedAs;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public VariableModel getType() {
        return type;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public boolean isArray() {
        return array;
    }

    public Modifier[] getModifiers() {
        return modifiers;
    }

    public boolean isPackageVisibility() {
        return packageVisibility;
    }

    public InclusionType getInclusion() {
        return inclusion;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isExcludeFromToString() {
        return excludeFromToString;
    }

    public boolean isExcludeFromEqualsAndHashCode() {
        return excludeFromEqualsAndHashCode;
    }

    public InitializerModel getInitializeWith() {
        return initializeWith;
    }

    public boolean isDeprecated() {
        return deprecated;
    }
}
