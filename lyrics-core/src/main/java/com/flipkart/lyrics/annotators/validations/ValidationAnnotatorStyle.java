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

import com.flipkart.lyrics.model.FieldModel;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

/**
 * Created by shrey.garg on 03/01/17.
 */
public abstract class ValidationAnnotatorStyle {

    public abstract void processRequiredRule(FieldSpec.Builder fieldSpec, FieldModel fieldModel);

    public abstract void processRequiredRuleForGetters(MethodSpec.Builder methodSpec, FieldModel fieldModel);

    public abstract void processRequiredRuleForSetters(MethodSpec.Builder methodSpec, FieldModel fieldModel, ParameterSpec.Builder param);

    public abstract void processNotRequiredRule(FieldSpec.Builder fieldSpec, FieldModel fieldModel);

    public abstract void processNotRequiredRuleForGetters(MethodSpec.Builder methodSpec, FieldModel fieldModel);

    public abstract void processNotRequiredRuleForSetters(MethodSpec.Builder methodSpec, FieldModel fieldModel, ParameterSpec.Builder param);

}
