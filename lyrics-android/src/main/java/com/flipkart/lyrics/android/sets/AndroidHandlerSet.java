package com.flipkart.lyrics.android.sets;

import com.flipkart.lyrics.android.handlers.StringDefValuesHandler;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.DefaultHandlerSet;

/**
 * Created by anshul.garg on 13/01/17.
 */
public class AndroidHandlerSet extends DefaultHandlerSet {
    @Override
    public Handler getEnumValuesHandler() {
        return new StringDefValuesHandler(tune, metaInfo, ruleSet);
    }
}
