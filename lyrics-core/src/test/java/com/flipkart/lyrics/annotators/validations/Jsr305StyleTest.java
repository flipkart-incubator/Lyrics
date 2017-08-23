/*
 * Copyright 2017 Flipkart Internet, pvt ltd.
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

package com.flipkart.lyrics.annotators.validations;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.interfaces.FieldSpec;
import com.flipkart.lyrics.interfaces.MethodSpec;
import com.flipkart.lyrics.interfaces.ParameterSpec;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.flipkart.lyrics.helper.ClassNames.JSR_305_NON_NULL;
import static com.flipkart.lyrics.helper.ClassNames.JSR_305_NULLABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by shrey.garg on 07/06/17.
 */

@ExtendWith(ConfigurationExtension.class)
public class Jsr305StyleTest {

    @Test
    public void testRequiredRule(@TuneProvider Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);

        new Jsr305Style().processRequiredRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(1, fieldSpec.classNames.size());

        ClassName requiredAnnotation = fieldSpec.classNames.get(0);
        assertEquals(JSR_305_NON_NULL, requiredAnnotation);
    }

    @Test
    public void testRequiredRuleForGetters(@TuneProvider Tune tune) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test");
        FieldModel model = mock(FieldModel.class);

        new Jsr305Style().processRequiredRuleForGetters(builder, model);

        MethodSpec methodSpec = builder.build();
        assertEquals("test", methodSpec.name);
        assertEquals(1, methodSpec.annotationClassNames.size());

        ClassName requiredAnnotation = methodSpec.annotationClassNames.get(0);
        assertEquals(JSR_305_NON_NULL, requiredAnnotation);
    }

    @Test
    public void testRequiredRuleForSetters(@TuneProvider Tune tune) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test");
        ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(String.class, "one");
        FieldModel model = mock(FieldModel.class);

        new Jsr305Style().processRequiredRuleForSetters(builder, model, parameterBuilder);

        MethodSpec methodSpec = builder.build();
        assertEquals("test", methodSpec.name);
        assertEquals(0, methodSpec.annotationClassNames.size());

        ParameterSpec parameterSpec = parameterBuilder.build();
        assertEquals("one", parameterSpec.name);
        assertEquals(1, parameterSpec.classNames.size());

        ClassName requiredAnnotation = parameterSpec.classNames.get(0);
        assertEquals(JSR_305_NON_NULL, requiredAnnotation);
    }

    @Test
    public void testNotRequiredRule(@TuneProvider Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);

        new Jsr305Style().processNotRequiredRule(builder, model);

        FieldSpec fieldSpec = builder.build();
        assertEquals("test", fieldSpec.name);
        assertEquals(1, fieldSpec.classNames.size());

        ClassName requiredAnnotation = fieldSpec.classNames.get(0);
        assertEquals(JSR_305_NULLABLE, requiredAnnotation);
    }

    @Test
    public void testNotRequiredRuleForGetters(@TuneProvider Tune tune) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test");
        FieldModel model = mock(FieldModel.class);

        new Jsr305Style().processNotRequiredRuleForGetters(builder, model);

        MethodSpec methodSpec = builder.build();
        assertEquals("test", methodSpec.name);
        assertEquals(1, methodSpec.annotationClassNames.size());

        ClassName requiredAnnotation = methodSpec.annotationClassNames.get(0);
        assertEquals(JSR_305_NULLABLE, requiredAnnotation);
    }

    @Test
    public void testNotRequiredRuleForSetters(@TuneProvider Tune tune) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test");
        ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(String.class, "one");
        FieldModel model = mock(FieldModel.class);

        new Jsr305Style().processNotRequiredRuleForSetters(builder, model, parameterBuilder);

        MethodSpec methodSpec = builder.build();
        assertEquals("test", methodSpec.name);
        assertEquals(0, methodSpec.annotationClassNames.size());

        ParameterSpec parameterSpec = parameterBuilder.build();
        assertEquals("one", parameterSpec.name);
        assertEquals(1, parameterSpec.classNames.size());

        ClassName requiredAnnotation = parameterSpec.classNames.get(0);
        assertEquals(JSR_305_NULLABLE, requiredAnnotation);
    }

    @Test
    public void testRequiredRuleForConstructor(@TuneProvider Tune tune) {
        ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(String.class, "one");

        new Jsr305Style().processRequiredRuleForConstructor(parameterBuilder);

        ParameterSpec parameterSpec = parameterBuilder.build();
        assertEquals("one", parameterSpec.name);
        assertEquals(1, parameterSpec.classNames.size());

        ClassName requiredAnnotation = parameterSpec.classNames.get(0);
        assertEquals(JSR_305_NON_NULL, requiredAnnotation);
    }

    @Test
    public void testNotRequiredRuleForConstructor(@TuneProvider Tune tune) {
        ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(String.class, "one");

        new Jsr305Style().processNotRequiredRuleForConstructor(parameterBuilder);

        ParameterSpec parameterSpec = parameterBuilder.build();
        assertEquals("one", parameterSpec.name);
        assertEquals(1, parameterSpec.classNames.size());

        ClassName requiredAnnotation = parameterSpec.classNames.get(0);
        assertEquals(JSR_305_NULLABLE, requiredAnnotation);
    }
}