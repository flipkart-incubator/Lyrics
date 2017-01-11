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
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.sets.DefaultRuleSet;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import com.squareup.javapoet.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.lang.model.element.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 09/12/16.
 */
@ExtendWith(ConfigurationExtension.class)
public class SetterHandlerTest {

    @Test
    public void testSetter() {
        FieldSpec.Builder builder = FieldSpec.builder(TypeName.INT, "test");
        FieldSpec fieldSpec = builder.build();

        FieldModel model = mock(FieldModel.class);
        when(model.getFieldType()).thenReturn(FieldType.INTEGER);

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder("Klazz");
        MetaInfo metaInfo = new MetaInfo(null, null);
        new SetterHandler(null, metaInfo, new DefaultRuleSet(null, metaInfo)).process(classBuilder, fieldSpec, model);

        TypeSpec spec = classBuilder.build();
        assertEquals(1, spec.methodSpecs.size());
        MethodSpec methodSpec = spec.methodSpecs.get(0);

        assertEquals(1, methodSpec.parameters.size());
        assertEquals(TypeName.INT, methodSpec.parameters.get(0).type);
        assertEquals("test", methodSpec.parameters.get(0).name);

        assertTrue(methodSpec.hasModifier(Modifier.PUBLIC));
        assertEquals(TypeName.VOID, methodSpec.returnType);
        assertEquals("setTest", methodSpec.name);
        assertEquals(CodeBlock.of("this.test = test;").toString().trim(), methodSpec.code.toString().trim());
    }

}