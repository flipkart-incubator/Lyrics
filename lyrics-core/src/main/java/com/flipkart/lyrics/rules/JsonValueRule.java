package com.flipkart.lyrics.rules;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.rules.method.MethodRule;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

/**
 * Created by prasad.krishna on 05/04/17.
 */
public class JsonValueRule extends MethodRule {

    public JsonValueRule(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    public void process(MethodSpec.Builder methodSpec, FieldModel fieldModel, ParameterSpec.Builder parameterSpec) {
        if (!fieldModel.isJsonValue()) {
            return;
        }
        tune.getAnnotatorStyles().forEach(style -> style.processJsonValueRule(methodSpec));
    }
}
