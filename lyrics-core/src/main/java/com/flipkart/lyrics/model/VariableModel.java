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

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by shrey.garg on 28/11/16.
 */
public class VariableModel {
    private String type;
    private VariableModel[] types = new VariableModel[0];
    private FieldType fieldType;

    public VariableModel() {
    }

    public VariableModel(String type) {
        this.type = type;
    }

    public VariableModel(String type, VariableModel[] types) {
        this.type = type;
        this.types = types;
    }

    public String getType() {
        return type;
    }

    public VariableModel[] getTypes() {
        return types;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariableModel)) return false;

        VariableModel that = (VariableModel) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (!Objects.equals(fieldType, that.fieldType)) return false;
        return Arrays.equals(types, that.types);
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result += fieldType != null ? fieldType.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(types);
        return result;
    }
}
