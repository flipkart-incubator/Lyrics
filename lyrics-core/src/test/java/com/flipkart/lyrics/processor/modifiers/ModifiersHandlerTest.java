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

package com.flipkart.lyrics.processor.modifiers;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.specs.Modifier;
import com.flipkart.lyrics.specs.TypeSpec;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

import static com.flipkart.lyrics.test.extensions.ConfigurationExtension.NO_MODIFIERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 08/12/16.
 */
@ExtendWith(ConfigurationExtension.class)
public class ModifiersHandlerTest {

    @Test
    public void testNoModifiers(@TuneProvider(NO_MODIFIERS) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = new TypeModel();

        new ModifiersHandler(tune, null, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        Set<Modifier> modifiers = spec.modifiers;

        assertEquals(0, modifiers.size(), "Modifiers found");
    }

    @Test
    public void testSingleModifier(@TuneProvider(NO_MODIFIERS) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getModifiers()).thenReturn(new Modifier[] { Modifier.PUBLIC });

        new ModifiersHandler(tune, null, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        Set<Modifier> modifiers = spec.modifiers;

        assertEquals(1, modifiers.size(), "No Modifiers found");
        assertTrue(spec.modifiers.contains(Modifier.PUBLIC));
    }

    @Test
    public void testMultipleModifiers(@TuneProvider(NO_MODIFIERS) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getModifiers()).thenReturn(new Modifier[] { Modifier.PUBLIC, Modifier.ABSTRACT });

        new ModifiersHandler(tune, null, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        Set<Modifier> modifiers = spec.modifiers;

        assertEquals(2, modifiers.size(), "No Modifiers found");
        assertTrue(spec.modifiers.contains(Modifier.PUBLIC));
        assertTrue(spec.modifiers.contains(Modifier.ABSTRACT));
    }

    @Test
    public void testConfigModifiersAndModelModifier(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getModifiers()).thenReturn(new Modifier[] { Modifier.PRIVATE, Modifier.ABSTRACT });

        new ModifiersHandler(tune, null, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        Set<Modifier> modifiers = spec.modifiers;

        assertEquals(2, modifiers.size(), "No Modifiers found");
        assertTrue(spec.modifiers.contains(Modifier.PRIVATE));
        assertTrue(spec.modifiers.contains(Modifier.ABSTRACT));
    }

    @Test
    public void testConfigModifiers(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = new TypeModel();

        new ModifiersHandler(tune, null, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        Set<Modifier> modifiers = spec.modifiers;

        assertEquals(1, modifiers.size(), "No Modifiers found");
        assertTrue(spec.modifiers.contains(Modifier.PUBLIC));
    }
}