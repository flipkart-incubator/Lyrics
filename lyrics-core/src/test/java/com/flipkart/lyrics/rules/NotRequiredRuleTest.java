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

package com.flipkart.lyrics.rules;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.interfaces.FieldSpec;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.flipkart.lyrics.test.extensions.ConfigurationExtension.JSR_305;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 11/12/16.
 */
@ExtendWith(ConfigurationExtension.class)
public class NotRequiredRuleTest {

    @Test
    public void testNoRequiredSets(@TuneProvider Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = new FieldModel();

        new NotRequiredRule(tune, null).process(builder, model);

        FieldSpec spec = builder.build();

        assertEquals("test", spec.name, "Wrong name found.");
        assertEquals(0, spec.annotationSpecs.size(), "Annotations found.");
    }

    @Test
    public void testPrimitiveField(@TuneProvider Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);
        when(model.isPrimitive()).thenReturn(true);

        new NotRequiredRule(tune, null).process(builder, model);

        FieldSpec spec = builder.build();

        assertEquals("test", spec.name, "Wrong name found.");
        assertEquals(0, spec.annotationSpecs.size(), "Annotations found.");
    }

    @Test
    public void testJsrRequiredRules(@TuneProvider(JSR_305) Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = new FieldModel();

        new NotRequiredRule(tune, null).process(builder, model);

        FieldSpec spec = builder.build();

        assertEquals("test", spec.name, "Wrong name found.");
        assertEquals(1, spec.classNames.size(), "Annotations not found.");

        assertEquals("javax.annotation.Nullable", spec.classNames.get(0).packageName() + "." + spec.classNames.get(0).simpleName(), "JSR-305 annotation not found.");
    }
}