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
 * Created by shrey.garg on 03/12/16.
 */
public class MethodModel {
    private MethodType methodType;
    private VariableModel type;
    private boolean primitive;
    private Modifier[] modifiers = new Modifier[0];
    private boolean packageVisibility;
    private Object defaultValue;

    public MethodModel() {
    }

    public MethodModel(MethodType methodType, VariableModel type, boolean primitive, Modifier[] modifiers, boolean packageVisibility, Object defaultValue) {
        this.methodType = methodType;
        this.type = type;
        this.primitive = primitive;
        this.modifiers = modifiers;
        this.packageVisibility = packageVisibility;
        this.defaultValue = defaultValue;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public VariableModel getType() {
        return type;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public Modifier[] getModifiers() {
        return modifiers;
    }

    public boolean isPackageVisibility() {
        return packageVisibility;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

}
