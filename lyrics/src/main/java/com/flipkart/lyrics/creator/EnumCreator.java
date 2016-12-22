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

package com.flipkart.lyrics.creator;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.annotations.ClassAnnotationHandler;
import com.flipkart.lyrics.processor.constructors.OrderedConstructorHandler;
import com.flipkart.lyrics.processor.fields.FieldsHandler;
import com.flipkart.lyrics.processor.instances.EnumValuesHandler;
import com.flipkart.lyrics.processor.modifiers.ModifiersHandler;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shrey.garg on 27/11/16.
 */
public class EnumCreator implements TypeCreator {
    @Override
    public TypeSpec.Builder process(String name, String fullPackage, TypeModel typeModel, Tune configuration) {
        TypeSpec.Builder typeBuilder = TypeSpec.enumBuilder(name);

        new ClassAnnotationHandler().process(typeBuilder, typeModel, configuration);
        new ModifiersHandler().process(typeBuilder, typeModel, configuration);
        new EnumValuesHandler().process(typeBuilder, typeModel);
        Map<String, FieldSpec> fields = new FieldsHandler().process(typeBuilder, typeModel, configuration, new HashMap<>());
        new OrderedConstructorHandler().process(typeBuilder, fields, typeModel.getFieldOrder());

        return typeBuilder;
    }
}
