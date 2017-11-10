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
package com.flipkart.lyrics.helper;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.sets.FieldTypeHandlerSet;
import com.flipkart.lyrics.sets.HandlerSet;
import com.flipkart.lyrics.sets.ParameterTypeHandlerSet;
import com.flipkart.lyrics.sets.RuleSet;

/**
 * Created by shrey.garg on 18/05/17.
 */
public class Injector {
    public static HandlerSet processHandlerSet(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        HandlerSet handlerSet = tune.getHandlerSet();
        handlerSet.setMetaInfo(metaInfo);
        handlerSet.setRuleSet(ruleSet);
        handlerSet.setTune(tune);
        return handlerSet;
    }

    public static RuleSet processRuleSet(Tune tune, MetaInfo metaInfo) {
        RuleSet ruleSet = tune.getRuleSet();
        ruleSet.setTune(tune);
        ruleSet.setMetaInfo(metaInfo);
        return ruleSet;
    }

    public static void processFieldTypeHandlerSet(Tune tune, MetaInfo metaInfo) {
        FieldTypeHandlerSet fieldTypeHandlerSet = tune.getFieldTypeHandlerSet();
        fieldTypeHandlerSet.setMetaInfo(metaInfo);
        fieldTypeHandlerSet.setTune(tune);
    }

    public static void processParameterTypeHandlerSet(Tune tune, MetaInfo metaInfo) {
        ParameterTypeHandlerSet parameterTypeHandlerSet = tune.getParameterTypeHandlerSet();
        parameterTypeHandlerSet.setMetaInfo(metaInfo);
        parameterTypeHandlerSet.setTune(tune);
    }

    public static void processFieldAdditionalHandlers(Tune tune, MetaInfo metaInfo) {
        tune.getFieldsAdditionalPropertiesHandler().values().forEach(h -> {
            h.setTune(tune);
            h.setMetaInfo(metaInfo);
        });
    }

    public static void processTypeAdditionalHandlers(Tune tune, MetaInfo metaInfo) {
        tune.getTypeAdditionalPropertiesHandler().values().forEach(h -> {
            h.setTune(tune);
            h.setMetaInfo(metaInfo);
        });
    }

    public static void processFieldModificationHandlers(Tune tune, MetaInfo metaInfo) {
        tune.getFieldModificationHandlers().values().forEach(h -> {
            h.setTune(tune);
            h.setMetaInfo(metaInfo);
        });
    }

    public static void processSuperClassFields(Tune tune, MetaInfo metaInfo, TypeModel extendsTypeModel) {
        if (extendsTypeModel != null) {
            metaInfo.setSuperClassFields(extendsTypeModel.getFields());
        }
    }
}
