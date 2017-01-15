package com.flipkart.lyrics.android.sets;

import com.flipkart.lyrics.android.creator.AndroidEnumCreator;
import com.flipkart.lyrics.creator.TypeCreator;
import com.flipkart.lyrics.sets.DefaultCreatorSet;

/**
 * Created by shrey.garg on 15/01/17.
 */
public class AndroidCreatorSet extends DefaultCreatorSet {

    @Override
    public TypeCreator getEnumCreator() {
        return new AndroidEnumCreator();
    }
}
