package com.flipkart.lyrics;

import com.flipkart.lyrics.handlers.EnumToStringDefValuesHandler;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.processor.instances.EnumValuesHandler;
import com.flipkart.lyrics.sets.DefaultHandlerSet;

/**
 * Created by anshul.garg on 13/01/17.
 */
public class AndroidHandlerSet extends DefaultHandlerSet {
    @Override
    public Handler getEnumValuesHandler() {
        if(tune.isEnumToClassConversionNeeded()) {
            return new EnumValuesHandler(tune, metaInfo, ruleSet);
        }else{
            return new EnumToStringDefValuesHandler(tune, metaInfo, ruleSet);
        }
    }
}
