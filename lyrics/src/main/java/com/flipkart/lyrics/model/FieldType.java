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

import com.flipkart.lyrics.processor.fields.FieldTypeHandler;
import com.flipkart.lyrics.processor.fields.ObjectTypeHandler;
import com.flipkart.lyrics.processor.fields.PrimitiveTypeHandler;
import com.flipkart.lyrics.processor.fields.StringTypeHandler;

/**
 * Created by shrey.garg on 25/11/16.
 */
public enum FieldType {
    OBJECT(new ObjectTypeHandler()),
    STRING(new StringTypeHandler()),
    INTEGER(new PrimitiveTypeHandler(Primitive.INTEGER)),
    BOOLEAN(new PrimitiveTypeHandler(Primitive.BOOLEAN)),
    LONG(new PrimitiveTypeHandler(Primitive.LONG)),
    DOUBLE(new PrimitiveTypeHandler(Primitive.DOUBLE)),
    CHARACTER(new PrimitiveTypeHandler(Primitive.CHARACTER)),
    SHORT(new PrimitiveTypeHandler(Primitive.SHORT)),
    BYTE(new PrimitiveTypeHandler(Primitive.BYTE)),
    FLOAT(new PrimitiveTypeHandler(Primitive.FLOAT));

    private FieldTypeHandler handler;

    FieldType(FieldTypeHandler handler) {
        this.handler = handler;
    }

    public FieldTypeHandler getHandler() {
        return handler;
    }
}
