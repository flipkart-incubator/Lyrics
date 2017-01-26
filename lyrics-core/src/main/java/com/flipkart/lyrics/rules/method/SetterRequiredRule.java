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
public class SetterRequiredRule extends MethodRule {

    public SetterRequiredRule(Tune tune, MetaInfo metaInfo) {
        super(tune, metaInfo);
    }

    @Override
    public void process(MethodSpec.Builder methodSpec, FieldModel fieldModel, ParameterSpec.Builder parameterSpec) {
        if (!fieldModel.isRequired() || (fieldModel.isPrimitive() && !fieldModel.isArray())) {
            return;
        }

        tune.getValidationAnnotatorStyles().forEach(style -> style.processRequiredRuleForSetters(methodSpec, fieldModel, parameterSpec));
    }

}
