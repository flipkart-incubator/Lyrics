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

package com.flipkart.lyrics.sets;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.processor.annotations.ClassAnnotationHandler;
import com.flipkart.lyrics.processor.constructors.NoArgsConstructorHandler;
import com.flipkart.lyrics.processor.constructors.OrderedConstructorHandler;
import com.flipkart.lyrics.processor.fields.FieldsHandler;
import com.flipkart.lyrics.processor.generics.GenericsHandler;
import com.flipkart.lyrics.processor.instances.EnumValuesHandler;
import com.flipkart.lyrics.processor.methods.AnnotationMethodsHandler;
import com.flipkart.lyrics.processor.methods.EqualsAndHashCodeHandler;
import com.flipkart.lyrics.processor.methods.InterfaceMethodsHandler;
import com.flipkart.lyrics.processor.methods.ToStringHandler;
import com.flipkart.lyrics.processor.modifiers.ModifiersHandler;
import com.flipkart.lyrics.processor.supertypes.InterfaceHandler;
import com.flipkart.lyrics.processor.supertypes.SpecialInterfaceHandler;
import com.flipkart.lyrics.processor.supertypes.SuperClassHandler;

/**
 * Created by shrey.garg on 01/01/17.
 */
public class DefaultHandlerSet extends HandlerSet {

    public DefaultHandlerSet() {
        super();
    }

    public DefaultHandlerSet(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getTypeAnnotationHandler() {
        return new ClassAnnotationHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getGenericsHandler() {
        return new GenericsHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getModifiersHandler() {
        return new ModifiersHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getSuperClassHandler() {
        return new SuperClassHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getInterfacesHandler() {
        return new InterfaceHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getFieldsHandler() {
        return new FieldsHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getEqualsAndHashCodeHandler() {
        return new EqualsAndHashCodeHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getToStringHandler() {
        return new ToStringHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getNoArgsConstructorHandler() {
        return new NoArgsConstructorHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getSpecialInterfacesHandler() {
        return new SpecialInterfaceHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getAnnotationMethodsHandler() {
        return new AnnotationMethodsHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getEnumValuesHandler() {
        return new EnumValuesHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getOrderedConstructorHandler() {
        return new OrderedConstructorHandler(tune, metaInfo, ruleSet);
    }

    @Override
    public Handler getInterfaceMethodsHandler() {
        return new InterfaceMethodsHandler(tune, metaInfo, ruleSet);
    }

}
