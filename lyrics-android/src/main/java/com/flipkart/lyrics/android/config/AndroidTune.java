package com.flipkart.lyrics.android.config;

import com.flipkart.lyrics.android.annotators.validations.AndroidValidationStyle;
import com.flipkart.lyrics.android.sets.AndroidCreatorSet;
import com.flipkart.lyrics.android.sets.AndroidFieldTypeHandlerSet;
import com.flipkart.lyrics.android.sets.AndroidHandlerSet;
import com.flipkart.lyrics.annotators.validations.ValidationAnnotatorStyle;
import com.flipkart.lyrics.config.DefaultTune;
import com.flipkart.lyrics.sets.CreatorSet;
import com.flipkart.lyrics.sets.FieldTypeHandlerSet;
import com.flipkart.lyrics.sets.HandlerSet;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shrey.garg on 15/01/17.
 */
public abstract class AndroidTune extends DefaultTune {

    public abstract List<String> createStringDefsFor();

    @Override
    public CreatorSet getCreatorSet() {
        return new AndroidCreatorSet();
    }

    @Override
    public HandlerSet getHandlerSet() {
        return new AndroidHandlerSet();
    }

    @Override
    public FieldTypeHandlerSet getFieldTypeHandlerSet() {
        return new AndroidFieldTypeHandlerSet();
    }

    @Override
    public List<ValidationAnnotatorStyle> getValidationAnnotatorStyles() {
        return Arrays.asList(
                new AndroidValidationStyle()
        );
    }
}
