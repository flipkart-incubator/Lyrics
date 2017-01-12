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
        return new ObjectTypeHandler();
    }

    @Override
    public FieldTypeHandler getIntegerTypeHandler() {
        return new PrimitiveTypeHandler(Primitive.INTEGER);
    }

    @Override
    public FieldTypeHandler getBooleanTypeHandler() {
        return new PrimitiveTypeHandler(Primitive.BOOLEAN);
    }

    @Override
    public FieldTypeHandler getLongTypeHandler() {
        return new PrimitiveTypeHandler(Primitive.LONG);
    }

    @Override
    public FieldTypeHandler getDoubleTypeHandler() {
        return new PrimitiveTypeHandler(Primitive.DOUBLE);
    }

    @Override
    public FieldTypeHandler getCharacterTypeHandler() {
        return new PrimitiveTypeHandler(Primitive.CHARACTER);
    }

    @Override
    public FieldTypeHandler getShortTypeHandler() {
        return new PrimitiveTypeHandler(Primitive.SHORT);
    }

    @Override
    public FieldTypeHandler getByteTypeHandler() {
        return new PrimitiveTypeHandler(Primitive.BYTE);
    }

    @Override
    public FieldTypeHandler getFloatTypeHandler() {
        return new PrimitiveTypeHandler(Primitive.FLOAT);
    }

    @Override
    public FieldTypeHandler getStringTypeHandler() {
        return new StringTypeHandler();
    }

}
