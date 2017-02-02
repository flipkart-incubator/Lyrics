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
import com.flipkart.lyrics.model.*;
import com.flipkart.lyrics.processor.Handler;

import javax.lang.model.element.Modifier;

/**
 * Created by shrey.garg on 30/12/16.
 */
public abstract class HandlerSet {

    protected Tune tune;
    protected MetaInfo metaInfo;
    protected RuleSet ruleSet;

    public HandlerSet() {

    }

    public HandlerSet(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        this.tune = tune;
        this.metaInfo = metaInfo;
        this.ruleSet = ruleSet;
    }

    /**
     * @return An {@link Handler} that annotates the under construction type with the annotations supplied via a list of
     * {@link AnnotationModel} provided in {@link TypeModel} or the {@link Tune}.
     */
    public abstract Handler getTypeAnnotationHandler();

    /**
     * @return An {@link Handler} that adds the specified generic variables to the under construction classes.
     * Generic variables are provided by {@link TypeModel} as a list of {@link GenericVariableModel}.
     */
    public abstract Handler getGenericsHandler();

    /**
     * @return An {@link Handler} that adds the specified modifiers to the under construction classes.
     * Modifiers are provided by {@link TypeModel} as an array of {@link Modifier} or by the {@link Tune} as a
     * default {@link Modifier}.
     */
    public abstract Handler getModifiersHandler();

    /**
     * @return An {@link Handler} that adds the specified super class to the under construction classes.
     * Super class is provided by {@link TypeModel} as a {@link VariableModel}.
     */
    public abstract Handler getSuperClassHandler();

    /**
     * @return An {@link Handler} that adds the specified interfaces to the under construction classes.
     * Interfaces are provided by {@link TypeModel} and/or by the {@link Tune} as a set of {@link VariableModel}.
     */
    public abstract Handler getInterfacesHandler();

    /**
     * @return An {@link Handler} that adds the specified fields to the under construction classes.
     * Fields are provided by {@link TypeModel} as a map of String and {@link FieldModel}.
     */
    public abstract Handler getFieldsHandler();

    /**
     * @return An {@link Handler} that adds the equals and hashcode methods to the under construction classes.
     */
    public abstract Handler getEqualsAndHashCodeHandler();

    /**
     * @return An {@link Handler} that adds the toString method to the under construction classes.
     */
    public abstract Handler getToStringHandler();

    /**
     * @return An {@link Handler} that adds a no argument constructor to the under construction classes.
     */
    public abstract Handler getNoArgsConstructorHandler();

    /**
     * @return An {@link Handler} that adds a constructor initializing all the required fields to the under construction classes.
     */
    public abstract Handler getRequiredFieldsConstructorHandler();

    /**
     * @return An {@link Handler} that handles special interfaces, if any, for the under construction classes.
     */
    public abstract Handler getSpecialInterfacesHandler();

    /**
     * @return An {@link Handler} that adds the specified methods to the under construction annotations.
     * Methods are provided by {@link TypeModel} as a map of String and {@link MethodModel}.
     */
    public abstract Handler getAnnotationMethodsHandler();

    /**
     * @return An {@link Handler} that adds the specified enum values to the under construction enums.
     */
    public abstract Handler getEnumValuesHandler();

    /**
     * @return An {@link Handler} that adds the specified enum values to the under construction enums.
     * Fields are provided by {@link TypeModel} as a map of String and Object array.
     */
    public abstract Handler getEnumValuesWithFieldsHandler();

    /**
     * @return An {@link Handler} that adds a ordered constructor to the under construction enum.
     */
    public abstract Handler getOrderedConstructorHandler();

    /**
     * @return A {@link Handler} that adds methods to the under construction interfaces.
     */
    public abstract Handler getInterfaceMethodsHandler();

    public Tune getTune() {
        return tune;
    }

    public void setTune(Tune tune) {
        this.tune = tune;
    }

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }
}
