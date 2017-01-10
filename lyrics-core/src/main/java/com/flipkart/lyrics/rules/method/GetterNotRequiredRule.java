package com.flipkart.lyrics.rules.method;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.rules.method.MethodRule;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

/**
 * Created by shrey.garg on 10/01/17.
 */
public class GetterNotRequiredRule extends MethodRule {

    public GetterNotRequiredRule(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    public void process(MethodSpec.Builder methodSpec, FieldModel fieldModel, ParameterSpec.Builder parameterSpec) {
        if (fieldModel.isRequired() || fieldModel.isPrimitive()) {
            return;
        }

        metaInfo.getValidationAnnotatorStyles().forEach(style -> style.processNotRequiredRuleForGetters(methodSpec, fieldModel));
    }

}
