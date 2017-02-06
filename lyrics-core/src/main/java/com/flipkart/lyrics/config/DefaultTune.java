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

package com.flipkart.lyrics.config;

import com.flipkart.lyrics.annotators.AnnotatorStyle;
import com.flipkart.lyrics.annotators.validations.ValidationAnnotatorStyle;
import com.flipkart.lyrics.helper.TriConsumer;
import com.flipkart.lyrics.model.AnnotationModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.model.VariableModel;
import com.flipkart.lyrics.sets.*;
import com.flipkart.lyrics.styles.objectmethods.ObjectMethodsStyle;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.*;

/**
 * Created by shrey.garg on 26/11/16.
 */
public class DefaultTune implements Tune {

    private final FieldTypeHandlerSet fieldTypeHandlerSet = new DefaultFieldTypeHandlerSet();
    private final ParameterTypeHandlerSet parameterTypeHandlerSet = new DefaultParameterTypeHandlerSet();
    private final CreatorSet creatorSet = new DefaultCreatorSet();
    private final HandlerSet handlerSet = new DefaultHandlerSet();
    private final RuleSet ruleSet = new DefaultRuleSet();
    private final List<AnnotatorStyle> annotatorStyles = new ArrayList<>();
    private final List<ValidationAnnotatorStyle> validationAnnotatorStyles = new ArrayList<>();
    private final ObjectMethodsStyle objectMethodsStyle = null;
    private final Set<VariableModel> interfaces = new HashSet<>();

    @Override
    public Modifier getDefaultFieldModifier() {
        return Modifier.PRIVATE;
    }

    @Override
    public Modifier getDefaultClassModifier() {
        return Modifier.PUBLIC;
    }

    @Override
    public List<AnnotationModel> getClassLevelAnnotations() {
        return null;
    }

    @Override
    public List<AnnotatorStyle> getAnnotatorStyles() {
        return annotatorStyles;
    }

    @Override
    public List<ValidationAnnotatorStyle> getValidationAnnotatorStyles() {
        return validationAnnotatorStyles;
    }

    @Override
    public boolean areAccessorsNeeded() {
        return true;
    }

    @Override
    public boolean isToStringNeeded() {
        return false;
    }

    @Override
    public boolean areHashCodeAndEqualsNeeded() {
        return false;
    }

    @Override
    public boolean isRequiredFieldConstructorNeeded() {
        return true;
    }

    @Override
    public boolean excludeInitializedFieldsFromConstructor() {
        return false;
    }

    @Override
    public ObjectMethodsStyle getObjectMethodsStyle() {
        return objectMethodsStyle;
    }

    @Override
    public Set<VariableModel> interfaces() {
        return interfaces;
    }

    @Override
    public boolean forceDefaultFieldModifiers() {
        return false;
    }

    @Override
    public Map<String, TriConsumer<TypeSpec.Builder, TypeModel, MetaInfo>> getSpecialInterfacesHandler() {
        return null;
    }

    @Override
    public CreatorSet getCreatorSet() {
        return creatorSet;
    }

    @Override
    public HandlerSet getHandlerSet() {
        return handlerSet;
    }

    @Override
    public RuleSet getRuleSet() {
        return ruleSet;
    }

    @Override
    public FieldTypeHandlerSet getFieldTypeHandlerSet() {
        return fieldTypeHandlerSet;
    }

    @Override
    public ParameterTypeHandlerSet getParameterTypeHandlerSet() {
        return parameterTypeHandlerSet;
    }

}
