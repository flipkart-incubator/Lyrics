package com.flipkart.lyrics.processor.instances;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.util.Map;

/**
 * Created by anshul.garg on 11/01/17.
 */
public class EnumToStringDefValuesHandler extends Handler {

    public EnumToStringDefValuesHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeBuilder, TypeModel typeModel) {
        Map<String, Object[]> values = typeModel.getValues();
        String valuesStr = "";
        for (String key : values.keySet()) {
            valuesStr += key + ",";
            typeBuilder.addField(FieldSpec.builder(ClassName.get("java.lang","String"), key)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", key)
                    .build());
        }
        AnnotationSpec.Builder retentionAnnotation = AnnotationSpec.builder(ClassName.get("java.lang.annotation", "Retention")).
                addMember("value", "$T.SOURCE", ClassName.get("java.lang.annotation", "RetentionPolicy"));
        AnnotationSpec.Builder intDefAnnotation = AnnotationSpec.builder(ClassName.get("android.support.annotation", "StringDef"))
                .addMember("value", "{$L}", valuesStr.substring(0, valuesStr.length() - 1));

        typeBuilder.addType(TypeSpec.annotationBuilder(metaInfo.getClassName() + "Def").
                addModifiers(Modifier.PUBLIC).
                addAnnotation(retentionAnnotation.build()).
                addAnnotation(intDefAnnotation.build()).
                build());
    }
}
