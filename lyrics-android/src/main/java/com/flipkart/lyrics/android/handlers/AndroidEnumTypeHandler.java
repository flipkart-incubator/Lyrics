package com.flipkart.lyrics.android.handlers;

import com.flipkart.lyrics.android.config.AndroidTune;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.FieldType;
import com.flipkart.lyrics.processor.fields.FieldTypeHandler;
import com.flipkart.lyrics.processor.fields.ObjectTypeHandler;
import com.squareup.javapoet.*;

import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getClassName;
import static com.flipkart.lyrics.helper.Helper.resolveModifiers;

/**
 * Created by anshul.garg on 13/01/17.
 */
public class AndroidEnumTypeHandler extends FieldTypeHandler {
    @Override
    public FieldSpec.Builder process(TypeSpec.Builder typeSpec, String key, Tune configuration, FieldModel fieldModel, Map<String, TypeVariableName> typeVariableNames) {
        AndroidTune tune = (AndroidTune) configuration;
        String unresolvedType = fieldModel.getType().getType();
        if (fieldModel.getFieldType() == FieldType.ENUM && tune.createStringDefsFor().contains(unresolvedType)) {
            String enumName = unresolvedType.substring(unresolvedType.lastIndexOf(".") + 1);
            AnnotationSpec annotation = AnnotationSpec.builder(getClassName(unresolvedType + "." + enumName + "Def")).build();
            return FieldSpec.builder(TypeName.get(String.class), key, resolveModifiers(configuration, fieldModel)).addAnnotation(annotation);
        } else {
            return new ObjectTypeHandler().process(typeSpec, key, configuration, fieldModel, typeVariableNames);
        }
    }
}
