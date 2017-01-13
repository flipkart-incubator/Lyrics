package com.flipkart.lyrics;

import com.flipkart.lyrics.creator.TypeCreator;
import com.flipkart.lyrics.sets.DefaultCreatorSet;

/**
 * Created by anshul.garg on 13/01/17.
 */
public class AndroidCreator extends DefaultCreatorSet {

    @Override
    public TypeCreator getEnumCreator() {
        if(handlerSet.getTune().isEnumToClassConversionNeeded()) {
            return
        }else {
            return super.getEnumCreator();
        }
    }
}
