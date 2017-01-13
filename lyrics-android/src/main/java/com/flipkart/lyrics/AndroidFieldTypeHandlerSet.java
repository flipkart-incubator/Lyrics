package com.flipkart.lyrics;

import com.flipkart.lyrics.handlers.AndroidEnumTypeHanlder;
import com.flipkart.lyrics.processor.fields.FieldTypeHandler;
import com.flipkart.lyrics.sets.DefaultFieldTypeHandlerSet;
import com.flipkart.lyrics.sets.FieldTypeHandlerSet;

/**
 * Created by anshul.garg on 13/01/17.
 */
public class AndroidFieldTypeHandlerSet extends DefaultFieldTypeHandlerSet {

    @Override
    public FieldTypeHandler getEnumTypeHandler() {
        return new AndroidEnumTypeHanlder();
    }
}
