package com.flipkart.lyrics.sets;

import com.flipkart.lyrics.model.Primitive;
import com.flipkart.lyrics.processor.fields.FieldTypeHandler;
import com.flipkart.lyrics.processor.fields.ObjectTypeHandler;
import com.flipkart.lyrics.processor.fields.PrimitiveTypeHandler;
import com.flipkart.lyrics.processor.fields.StringTypeHandler;

/**
 * Created by shrey.garg on 12/01/17.
 */
public class DefaultFieldTypeHandlerSet extends FieldTypeHandlerSet {

    @Override
    public FieldTypeHandler getObjectTypeHandler() {
        return new ObjectTypeHandler(tune, metaInfo);
    }

    @Override
    public FieldTypeHandler getIntegerTypeHandler() {
        return new PrimitiveTypeHandler(tune, metaInfo, Primitive.INTEGER);
    }

    @Override
    public FieldTypeHandler getBooleanTypeHandler() {
        return new PrimitiveTypeHandler(tune, metaInfo, Primitive.BOOLEAN);
    }

    @Override
    public FieldTypeHandler getLongTypeHandler() {
        return new PrimitiveTypeHandler(tune, metaInfo, Primitive.LONG);
    }

    @Override
    public FieldTypeHandler getDoubleTypeHandler() {
        return new PrimitiveTypeHandler(tune, metaInfo, Primitive.DOUBLE);
    }

    @Override
    public FieldTypeHandler getCharacterTypeHandler() {
        return new PrimitiveTypeHandler(tune, metaInfo, Primitive.CHARACTER);
    }

    @Override
    public FieldTypeHandler getShortTypeHandler() {
        return new PrimitiveTypeHandler(tune, metaInfo, Primitive.SHORT);
    }

    @Override
    public FieldTypeHandler getByteTypeHandler() {
        return new PrimitiveTypeHandler(tune, metaInfo, Primitive.BYTE);
    }

    @Override
    public FieldTypeHandler getFloatTypeHandler() {
        return new PrimitiveTypeHandler(tune, metaInfo, Primitive.FLOAT);
    }

    @Override
    public FieldTypeHandler getStringTypeHandler() {
        return new StringTypeHandler(tune, metaInfo);
    }

    @Override
    public FieldTypeHandler getEnumTypeHandler() {
        return new ObjectTypeHandler(tune, metaInfo);
    }
}
