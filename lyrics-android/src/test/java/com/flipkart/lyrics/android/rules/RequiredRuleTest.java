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

package com.flipkart.lyrics.android.rules;

import com.flipkart.lyrics.android.test.annotation.TuneProvider;
import com.flipkart.lyrics.android.test.extensions.ConfigurationExtension;
import com.flipkart.lyrics.java.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.rules.RequiredRule;
import com.flipkart.lyrics.java.specs.FieldSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.flipkart.lyrics.android.test.extensions.ConfigurationExtension.ANDROID_SUPPORT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 15/01/17.
 */

@ExtendWith(ConfigurationExtension.class)
public class RequiredRuleTest {

    @Test
    public void testAndroidRequiredRules(@TuneProvider(ANDROID_SUPPORT) Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);
        when(model.isRequired()).thenReturn(true);

        new RequiredRule(tune, null).process(builder, model);

        FieldSpec spec = builder.build();

        assertEquals("test", spec.name, "Wrong name found.");
        assertEquals(1, spec.annotations.size(), "Annotations not found.");

        assertEquals("android.support.annotation.NonNull", spec.annotations.get(0).type.toString(), "Android support annotation not found.");
    }
}
