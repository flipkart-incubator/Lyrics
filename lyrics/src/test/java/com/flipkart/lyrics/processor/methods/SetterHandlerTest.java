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

package com.flipkart.lyrics.processor.methods;

import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.FieldType;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 09/12/16.
 */
public class SetterHandlerTest {

    @Test
    public void testGetter() {
        FieldSpec.Builder builder = FieldSpec.builder(TypeName.INT, "test");
        FieldSpec fieldSpec = builder.build();

        FieldModel model = mock(FieldModel.class);
        when(model.getFieldType()).thenReturn(FieldType.INTEGER);

        MethodSpec methodSpec = new SetterHandler().process(fieldSpec, model);

        assertEquals(1, methodSpec.parameters.size());
        assertEquals(TypeName.INT, methodSpec.parameters.get(0).type);
        assertEquals("test", methodSpec.parameters.get(0).name);

        assertTrue(methodSpec.hasModifier(Modifier.PUBLIC));
        assertEquals(TypeName.VOID, methodSpec.returnType);
        assertEquals("setTest", methodSpec.name);
        assertEquals(CodeBlock.of("this.test = test;").toString().trim(), methodSpec.code.toString().trim());
    }

}