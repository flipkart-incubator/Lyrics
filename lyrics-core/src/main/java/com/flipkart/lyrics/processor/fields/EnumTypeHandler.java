package com.flipkart.lyrics.processor.fields;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.squareup.javapoet.*;

import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getResolvedTypeName;
import static com.flipkart.lyrics.helper.Helper.resolveModifiers;

/**
 * Created by anshul.garg on 11/01/17.
 */
public class EnumTypeHandler extends FieldTypeHandler {
    @Override
    public FieldSpec.Builder process(TypeSpec.Builder typeSpec, String key, Tune configuration, FieldModel fieldModel, Map<String, TypeVariableName> typeVariableNames) {
        if(configuration.isEnumToClassConversionNeeded()) {
            TypeName typeName = fieldModel.isArray() ? ArrayTypeName.of(String.class) : TypeName.get(String.class);
            String annotationName = getResolvedTypeName(fieldModel.getType(), typeVariableNames).toString();
            int index = annotationName.lastIndexOf(".");
            String name = annotationName.substring(index + 1);
            AnnotationSpec.Builder className = AnnotationSpec.builder(ClassName.get(annotationName, name + "Def"));
            return FieldSpec.builder(typeName, key, resolveModifiers(configuration, fieldModel)).addAnnotation(className.build());
        }else {
            TypeName typeName;
            if (fieldModel.getType() == null) {
                typeName = TypeName.OBJECT;
            } else {
                typeName = getResolvedTypeName(fieldModel.getType(), typeVariableNames);
            }
            typeName = fieldModel.isArray() ? ArrayTypeName.of(typeName) : typeName;
            return FieldSpec.builder(typeName, key, resolveModifiers(configuration, fieldModel));
        }
    }
}
