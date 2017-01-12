package com.flipkart.lyrics.sets;

import com.flipkart.lyrics.creator.AnnotationCreator;
import com.flipkart.lyrics.creator.ClassCreator;
import com.flipkart.lyrics.creator.EnumCreator;
import com.flipkart.lyrics.creator.TypeCreator;

/**
 * Created by shrey.garg on 12/01/17.
 */
public class DefaultCreatorSet extends CreatorSet {

    @Override
    public TypeCreator getClassCreator() {
        return new ClassCreator();
    }

    @Override
    public TypeCreator getAnnotationCreator() {
        return new AnnotationCreator();
    }

    @Override
    public TypeCreator getEnumCreator() {
        return new EnumCreator();
    }

}
