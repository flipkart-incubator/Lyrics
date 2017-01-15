package com.flipkart.lyrics.android.handlers;

import com.flipkart.lyrics.android.config.AndroidTune;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.Type;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.processor.instances.EnumValuesHandler;
import com.flipkart.lyrics.sets.RuleSet;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

/**
 * Created by anshul.garg on 11/01/17.
 */
public class StringDefValuesHandler extends Handler {

    public StringDefValuesHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        AndroidTune androidTune = (AndroidTune) tune;
        if (!androidTune.createStringDefsFor().contains(metaInfo.getFullPackage() + "." + metaInfo.getClassName())) {
            new EnumValuesHandler(tune, metaInfo, ruleSet).process(typeBuilder, typeModel);
            return;
        }

        Map<String, Object[]> values = typeModel.getValues();
        String valuesStr = "";
        for (String key : values.keySet()) {
            valuesStr += key + ", ";
            typeBuilder.addField(FieldSpec.builder(ClassName.get(String.class), key)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", key)
                    .build());
        }

        AnnotationSpec.Builder retentionAnnotation = AnnotationSpec.builder(ClassName.get(Retention.class)).
                addMember("value", "$T.SOURCE", ClassName.get(RetentionPolicy.class));
        AnnotationSpec.Builder intDefAnnotation = AnnotationSpec.builder(ClassName.get("android.support.annotation", "StringDef"))
                .addMember("value", "{ $L }", valuesStr.substring(0, valuesStr.length() - 2));

        typeBuilder.addType(TypeSpec.annotationBuilder(metaInfo.getClassName() + "Def").
                addModifiers(Modifier.PUBLIC).
                addAnnotation(retentionAnnotation.build()).
                addAnnotation(intDefAnnotation.build()).
                build());
    }
}