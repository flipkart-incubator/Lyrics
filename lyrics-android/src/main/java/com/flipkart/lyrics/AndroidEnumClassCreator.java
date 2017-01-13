package com.flipkart.lyrics;

import com.flipkart.lyrics.creator.TypeCreator;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.sets.HandlerSet;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by anshul.garg on 13/01/17.
 */
public class AndroidEnumClassCreator extends TypeCreator {
    @Override
    public TypeSpec.Builder process(HandlerSet handlerSet, TypeModel typeModel) {
        TypeSpec.Builder typeBuilder;
        if(handlerSet.getTune().isEnumToClassConversionNeeded()){
            typeBuilder = TypeSpec.classBuilder(handlerSet.getMetaInfo().getClassName());
            handlerSet.getEnumToStringDefValuesHandler().process(typeBuilder, typeModel);

        }else {
            typeBuilder = TypeSpec.enumBuilder(handlerSet.getMetaInfo().getClassName());
            handlerSet.getEnumValuesHandler().process(typeBuilder, typeModel);
        }

        handlerSet.getTypeAnnotationHandler().process(typeBuilder, typeModel);
        handlerSet.getModifiersHandler().process(typeBuilder, typeModel);
        handlerSet.getFieldsHandler().process(typeBuilder, typeModel);
        handlerSet.getOrderedConstructorHandler().process(typeBuilder, typeModel);

        return typeBuilder;
    }
}
