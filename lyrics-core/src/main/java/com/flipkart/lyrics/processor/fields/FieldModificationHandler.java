package com.flipkart.lyrics.processor.fields;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.specs.FieldSpec;

/**
 * Created by shrey.garg on 14/06/17.
 */
public abstract class FieldModificationHandler {
    protected Tune tune;
    protected MetaInfo metaInfo;

    public FieldModificationHandler() {
    }

    /**
     * @param fieldBuilder Builder for the current field being processed, before it is added to type.
     * @param key          The additional property name for which the handler is called.
     * @param value        The non-null value of the additional property.
     * @param fieldModel   An immutable instance of FieldModel
     * @return The new FieldSpec.Builder that will replace the builder being processed
     */
    public abstract FieldSpec.Builder process(FieldSpec.Builder fieldBuilder, String key, Object value, FieldModel fieldModel);

    public final void setTune(Tune tune) {
        this.tune = tune;
    }

    public final void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
