package com.flipkart.lyrics.sets;

import com.flipkart.lyrics.creator.*;

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

    @Override
    public TypeCreator getEnumWithFieldsCreator() {
        return new EnumWithFieldsCreator();
    }

    @Override
    public TypeCreator getInterfaceCreator() {
        return new InterfaceCreator();
    }

}
