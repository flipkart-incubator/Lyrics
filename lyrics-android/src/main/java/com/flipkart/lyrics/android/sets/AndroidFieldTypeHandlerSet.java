package com.flipkart.lyrics.android.sets;

import com.flipkart.lyrics.android.handlers.AndroidEnumTypeHandler;
import com.flipkart.lyrics.processor.fields.FieldTypeHandler;
import com.flipkart.lyrics.sets.DefaultFieldTypeHandlerSet;

/**
 * Created by anshul.garg on 13/01/17.
 */
public class AndroidFieldTypeHandlerSet extends DefaultFieldTypeHandlerSet {

    @Override
    public FieldTypeHandler getEnumTypeHandler() {
        return new AndroidEnumTypeHandler();
    }
}
