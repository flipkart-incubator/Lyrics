package com.flipkart.lyrics.sets;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.processor.fields.FieldTypeHandler;

/**
 * Created by shrey.garg on 12/01/17.
 */
public abstract class FieldTypeHandlerSet {

    protected Tune tune;
    protected MetaInfo metaInfo;

    public FieldTypeHandlerSet() {

    }

    public FieldTypeHandlerSet(Tune tune, MetaInfo metaInfo) {
        this.tune = tune;
        this.metaInfo = metaInfo;
    }

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

    public final void setTune(Tune tune) {
        this.tune = this.tune == null ? tune : this.tune;
    }

    public final void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = this.metaInfo == null ? metaInfo : this.metaInfo;
    }
}
