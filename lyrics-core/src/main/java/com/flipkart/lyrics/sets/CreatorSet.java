package com.flipkart.lyrics.sets;

import com.flipkart.lyrics.creator.TypeCreator;

/**
 * Created by shrey.garg on 12/01/17.
 */
public abstract class CreatorSet {

    public abstract TypeCreator getClassCreator();

    public abstract TypeCreator getAnnotationCreator();

    public abstract TypeCreator getEnumCreator();

    public abstract TypeCreator getInterfaceCreator();

}
