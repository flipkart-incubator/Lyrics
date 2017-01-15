package com.flipkart.lyrics.android.creator;

import com.flipkart.lyrics.android.config.AndroidTune;
import com.flipkart.lyrics.creator.EnumCreator;
import com.flipkart.lyrics.creator.TypeCreator;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.Type;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.sets.HandlerSet;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by shrey.garg on 15/01/17.
 */
public class AndroidEnumCreator extends TypeCreator {

    @Override
    public TypeSpec.Builder process(HandlerSet handlerSet, TypeModel typeModel) {
        MetaInfo metaInfo = handlerSet.getMetaInfo();
        AndroidTune tune = (AndroidTune) handlerSet.getTune();
        if (!tune.createStringDefsFor().contains(metaInfo.getFullPackage() + "." + metaInfo.getClassName())) {
            return new EnumCreator().process(handlerSet, typeModel);
        }

        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(handlerSet.getMetaInfo().getClassName());
        handlerSet.getTypeAnnotationHandler().process(typeBuilder, typeModel);
        handlerSet.getModifiersHandler().process(typeBuilder, typeModel);
        handlerSet.getEnumValuesHandler().process(typeBuilder, typeModel);
        return typeBuilder;
    }

}
