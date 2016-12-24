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
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.annotations.ClassAnnotationHandler;
import com.flipkart.lyrics.rules.type.SubTypesRule;
import com.flipkart.lyrics.processor.constructors.NoArgsConstructorHandler;
import com.flipkart.lyrics.processor.fields.FieldsHandler;
import com.flipkart.lyrics.processor.generics.GenericsHandler;
import com.flipkart.lyrics.processor.methods.EqualsAndHashCodeHandler;
import com.flipkart.lyrics.processor.methods.ToStringHandler;
import com.flipkart.lyrics.processor.modifiers.ModifiersHandler;
import com.flipkart.lyrics.processor.supertypes.InterfaceHandler;
import com.flipkart.lyrics.processor.supertypes.SpecialInterfaceHandler;
import com.flipkart.lyrics.processor.supertypes.SuperClassHandler;
import com.flipkart.lyrics.rules.type.GlobalInclusionRule;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.Map;

/**
 * Created by shrey.garg on 27/11/16.
 */
public class ClassCreator implements TypeCreator {

    @Override
    public TypeSpec.Builder process(String name, String fullPackage, TypeModel typeModel, Tune configuration) {
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(name);
        MetaInfo metaInfo = new MetaInfo(name, fullPackage);

        new GlobalInclusionRule().process(typeBuilder, typeModel, configuration);
        new ClassAnnotationHandler().process(typeBuilder, typeModel, configuration);
        new SubTypesRule().process(typeBuilder, typeModel, configuration);
        Map<String, TypeVariableName> typeVariableNames = new GenericsHandler().process(typeBuilder, typeModel);
        new ModifiersHandler().process(typeBuilder, typeModel, configuration);
        new SuperClassHandler().process(typeBuilder, typeModel, typeVariableNames);
        new InterfaceHandler().process(typeBuilder, typeModel, typeVariableNames, configuration);
        new FieldsHandler().process(typeBuilder, typeModel, configuration, typeVariableNames);
        new EqualsAndHashCodeHandler().process(typeBuilder, name, typeModel.getFields(), configuration);
        new ToStringHandler().process(typeBuilder, name, typeModel.getFields(), configuration);
        new NoArgsConstructorHandler().process(typeBuilder, typeModel);
        new SpecialInterfaceHandler().process(typeBuilder, typeModel, typeVariableNames, configuration, metaInfo);

        return typeBuilder;
    }



}
