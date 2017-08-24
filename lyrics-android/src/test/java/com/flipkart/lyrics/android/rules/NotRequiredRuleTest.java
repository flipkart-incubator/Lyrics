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

import com.flipkart.lyrics.android.config.AndroidTune;
import com.flipkart.lyrics.android.test.annotation.TuneProvider;
import com.flipkart.lyrics.android.test.extensions.ConfigurationExtension;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.interfaces.FieldSpec;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.rules.NotRequiredRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.flipkart.lyrics.android.test.extensions.ConfigurationExtension.ANDROID_SUPPORT;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by shrey.garg on 15/01/17.
 */

@ExtendWith(ConfigurationExtension.class)
public class NotRequiredRuleTest {

    @Test
    public void testAndroidRequiredRules(@TuneProvider(ANDROID_SUPPORT) Tune tune) {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = new FieldModel();

        new NotRequiredRule(tune, null).process(builder, model);

        FieldSpec spec = builder.build();

        assertEquals("test", spec.name, "Wrong name found.");
        assertEquals(1, spec.classNames.size(), "Annotations not found.");

        assertEquals("android.support.annotation.Nullable", spec.classNames.get(0).packageName() + "." + spec.classNames.get(0).simpleName(), "Android support annotation not found.");
    }
}
