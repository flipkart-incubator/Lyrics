package com.flipkart.lyrics.processor.types;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.specs.TypeSpec;
import com.flipkart.lyrics.model.MetaInfo;

/**
 * Created by shrey.garg on 06/06/17.
 */
public abstract class TypeAdditionalHandler {
    protected Tune tune;
    protected MetaInfo metaInfo;

    /**
     * @param typeBuilder Builder for the current type being processed, after all the other handlers and rules are applied.
     * @param key         The additional property name for which the handler is called.
     * @param value       The non-null value of the additional property.
     */
    public abstract void process(TypeSpec.Builder typeBuilder, String key, Object value);

    public final void setTune(Tune tune) {
        this.tune = tune;
    }

    public final void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
