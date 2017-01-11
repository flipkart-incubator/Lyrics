package com.flipkart.lyrics.rules.method;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

/**
 * Created by shrey.garg on 10/01/17.
 */
public abstract class MethodRule {

    protected Tune tune;
    protected MetaInfo metaInfo;

    public MethodRule(Tune tune, MetaInfo metaInfo) {
        this.tune = tune;
        this.metaInfo = metaInfo;
    }

    public abstract void process(MethodSpec.Builder methodSpec, FieldModel fieldModel, ParameterSpec.Builder parameterSpec);
}
