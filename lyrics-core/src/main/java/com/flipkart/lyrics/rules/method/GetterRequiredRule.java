package com.flipkart.lyrics.rules.method;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

/**
 * Created by shrey.garg on 10/01/17.
 */
public class GetterRequiredRule extends MethodRule {

    public GetterRequiredRule(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    public void process(MethodSpec.Builder methodSpec, FieldModel fieldModel, ParameterSpec.Builder parameter) {
        if (!fieldModel.isRequired() || (fieldModel.isPrimitive() && !fieldModel.isArray())) {
            return;
        }

        tune.getValidationAnnotatorStyles().forEach(style -> style.processRequiredRuleForGetters(methodSpec, fieldModel));
    }

}
