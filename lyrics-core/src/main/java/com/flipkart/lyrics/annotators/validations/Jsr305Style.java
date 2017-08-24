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

package com.flipkart.lyrics.annotators.validations;

import com.flipkart.lyrics.specs.FieldSpec;
import com.flipkart.lyrics.specs.MethodSpec;
import com.flipkart.lyrics.specs.ParameterSpec;
import com.flipkart.lyrics.model.FieldModel;

import static com.flipkart.lyrics.helper.ClassNames.JSR_305_NON_NULL;
import static com.flipkart.lyrics.helper.ClassNames.JSR_305_NULLABLE;

/**
 * Created by shrey.garg on 03/01/17.
 */
public class Jsr305Style extends ValidationAnnotatorStyle {

    @Override
    public void processRequiredRule(FieldSpec.Builder fieldSpec, FieldModel fieldModel) {
        fieldSpec.addAnnotation(JSR_305_NON_NULL);
    }

    @Override
    public void processRequiredRuleForGetters(MethodSpec.Builder methodSpec, FieldModel fieldModel) {
        methodSpec.addAnnotation(JSR_305_NON_NULL);
    }

    @Override
    public void processRequiredRuleForSetters(MethodSpec.Builder methodSpec, FieldModel fieldModel, ParameterSpec.Builder parameterSpec) {
        parameterSpec.addAnnotation(JSR_305_NON_NULL);
    }

    @Override
    public void processNotRequiredRule(FieldSpec.Builder fieldSpec, FieldModel fieldModel) {
        fieldSpec.addAnnotation(JSR_305_NULLABLE);
    }

    @Override
    public void processNotRequiredRuleForGetters(MethodSpec.Builder methodSpec, FieldModel fieldModel) {
        methodSpec.addAnnotation(JSR_305_NULLABLE);
    }

    @Override
    public void processNotRequiredRuleForSetters(MethodSpec.Builder methodSpec, FieldModel fieldModel, ParameterSpec.Builder parameterSpec) {
        parameterSpec.addAnnotation(JSR_305_NULLABLE);
    }

    @Override
    public void processRequiredRuleForConstructor(ParameterSpec.Builder parameterSpec) {
        parameterSpec.addAnnotation(JSR_305_NON_NULL);
    }

    @Override
    public void processNotRequiredRuleForConstructor(ParameterSpec.Builder parameterSpec) {
        parameterSpec.addAnnotation(JSR_305_NULLABLE);
    }
}
