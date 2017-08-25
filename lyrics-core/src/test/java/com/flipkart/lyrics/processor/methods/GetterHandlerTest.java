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

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.FieldType;
import com.flipkart.lyrics.sets.DefaultRuleSet;
import com.flipkart.lyrics.specs.*;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 09/12/16.
 */
@ExtendWith(ConfigurationExtension.class)
public class GetterHandlerTest {

    @Test
    public void testGetter(@TuneProvider Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(TypeName.INT, "test");
        FieldSpec fieldSpec = builder.build();

        FieldModel model = mock(FieldModel.class);
        when(model.getFieldType()).thenReturn(FieldType.INTEGER);

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder("Klazz");
        new GetterHandler(tune, null, new DefaultRuleSet(tune, null)).process(classBuilder, fieldSpec, model);

        TypeSpec spec = classBuilder.build();
        assertEquals(1, spec.methodSpecs.size());
        MethodSpec methodSpec = spec.methodSpecs.get(0);

        assertTrue(methodSpec.modifiers.contains(Modifier.PUBLIC));
        assertEquals(TypeName.INT, methodSpec.returnType);
        assertEquals("getTest", methodSpec.name);
        //assertEquals(CodeBlock.of("return $L;", "test").toString().trim(), methodSpec.code.toString().trim());
    }
}