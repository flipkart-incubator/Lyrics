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

package com.flipkart.lyrics.android.config;

import com.flipkart.lyrics.android.annotators.validations.AndroidValidationStyle;
import com.flipkart.lyrics.android.sets.AndroidCreatorSet;
import com.flipkart.lyrics.android.sets.AndroidFieldTypeHandlerSet;
import com.flipkart.lyrics.android.sets.AndroidHandlerSet;
import com.flipkart.lyrics.android.sets.AndroidParameterTypeHandlerSet;
import com.flipkart.lyrics.annotators.validations.ValidationAnnotatorStyle;
import com.flipkart.lyrics.java.config.JavaTune;
import com.flipkart.lyrics.sets.CreatorSet;
import com.flipkart.lyrics.sets.FieldTypeHandlerSet;
import com.flipkart.lyrics.sets.HandlerSet;
import com.flipkart.lyrics.sets.ParameterTypeHandlerSet;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shrey.garg on 15/01/17.
 */
public class AndroidTune extends JavaTune {

    private final FieldTypeHandlerSet fieldTypeHandlerSet = new AndroidFieldTypeHandlerSet();
    private final ParameterTypeHandlerSet parameterTypeHandlerSet = new AndroidParameterTypeHandlerSet();
    private final CreatorSet creatorSet = new AndroidCreatorSet();
    private final HandlerSet handlerSet = new AndroidHandlerSet();

    @Override
    public CreatorSet getCreatorSet() {
        return creatorSet;
    }

    @Override
    public HandlerSet getHandlerSet() {
        return handlerSet;
    }

    @Override
    public FieldTypeHandlerSet getFieldTypeHandlerSet() {
        return fieldTypeHandlerSet;
    }

    @Override
    public ParameterTypeHandlerSet getParameterTypeHandlerSet() {
        return parameterTypeHandlerSet;
    }

    @Override
    public List<ValidationAnnotatorStyle> getValidationAnnotatorStyles() {
        return Arrays.asList(
                new AndroidValidationStyle()
        );
    }
}
