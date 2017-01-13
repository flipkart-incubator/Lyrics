package com.flipkart.lyrics.sets;

import com.flipkart.lyrics.processor.fields.FieldTypeHandler;

/**
 * Created by shrey.garg on 12/01/17.
 */
public abstract class FieldTypeHandlerSet {

    public abstract FieldTypeHandler getObjectTypeHandler();

    public abstract FieldTypeHandler getIntegerTypeHandler();

    public abstract FieldTypeHandler getBooleanTypeHandler();

    public abstract FieldTypeHandler getLongTypeHandler();

    public abstract FieldTypeHandler getDoubleTypeHandler();

    public abstract FieldTypeHandler getCharacterTypeHandler();

    public abstract FieldTypeHandler getShortTypeHandler();

    public abstract FieldTypeHandler getByteTypeHandler();

    public abstract FieldTypeHandler getFloatTypeHandler();

    public abstract FieldTypeHandler getStringTypeHandler();

    public abstract FieldTypeHandler getEnumTypeHandler();

}
