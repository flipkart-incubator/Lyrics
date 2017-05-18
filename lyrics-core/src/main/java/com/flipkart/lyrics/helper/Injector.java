package com.flipkart.lyrics.helper;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
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
}
