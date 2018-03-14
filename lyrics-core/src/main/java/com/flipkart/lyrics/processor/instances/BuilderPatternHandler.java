/*
 * Copyright 2017 Flipkart Internet, pvt ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flipkart.lyrics.processor.instances;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.helper.Helper;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.FieldType;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.processor.Handler;
import com.flipkart.lyrics.sets.RuleSet;
import com.flipkart.lyrics.specs.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.*;

/**
 * Created by shrey.garg on 13/03/18.
 */
public class BuilderPatternHandler extends Handler {
    public BuilderPatternHandler(Tune tune, MetaInfo metaInfo, RuleSet ruleSet) {
        super(tune, metaInfo, ruleSet);
    }

    @Override
    public void process(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        if (!typeModel.isCreateBuilder() || Arrays.stream(typeModel.getModifiers()).anyMatch(m -> m == Modifier.ABSTRACT)) {
            return;
        }

        TypeSpec.Builder builderBuilder = TypeSpec.classBuilder("Builder").addModifiers(Modifier.PUBLIC, Modifier.STATIC);

        processBuilderClass(builderBuilder, typeModel);
        createBuilderMethod(typeSpec, typeModel);

        typeSpec.addType(builderBuilder.build());
    }

    private void processBuilderClass(TypeSpec.Builder builderBuilder, TypeModel typeModel) {
        metaInfo.setOverrideAccessorGeneration(true);
        tune.getHandlerSet().getFieldsHandler().process(builderBuilder, typeModel);
        tune.getHandlerSet().getRequiredFieldsConstructorHandler().process(builderBuilder, typeModel);
        metaInfo.setOverrideAccessorGeneration(false);
        createBuilderAccessors(builderBuilder, typeModel);
        createBuildMethod(builderBuilder, typeModel);
    }

    private void createBuilderAccessors(TypeSpec.Builder builderBuilder, TypeModel typeModel) {
        Map<String, FieldModel> fields = typeModel.getFields();
        List<String> requiredFields = getRequiredFields(fields, tune.excludeInitializedFieldsFromConstructor());
        List<String> notRequiredFields = new ArrayList<>(fields.keySet());
        notRequiredFields.removeAll(requiredFields);

        final ClassName builderClass = ClassName.get(metaInfo.getFullPackage(), metaInfo.getClassName(), "Builder");
        for (String field : notRequiredFields) {
            MethodSpec.Builder builder = MethodSpec.methodBuilder(field);
            builder.returns(builderClass);

            FieldModel fieldModel = fields.get(field);
            ParameterSpec.Builder parameterSpec = getParameterTypeHandler(fieldModel.getFieldType(), tune.getParameterTypeHandlerSet())
                    .process(builderBuilder, field, fieldModel);

            if (!fieldModel.isPrimitive()) {
                tune.getValidationAnnotatorStyles().forEach(style -> requiredParameterConstructorParadigm.accept(style, fieldModel, parameterSpec));
            }

            builder.addParameter(parameterSpec.build());
            builder.addStatement("this.$L = $L", field, field);
            builder.addStatement("return this");

            builderBuilder.addMethod(builder.build());
        }
    }

    private void createBuildMethod(TypeSpec.Builder builderBuilder, TypeModel typeModel) {
        Map<String, FieldModel> fields = typeModel.getFields();
        List<String> requiredFields = getRequiredFields(fields, tune.excludeInitializedFieldsFromConstructor());
        if (!tune.isRequiredFieldConstructorNeeded()) {
            requiredFields = new ArrayList<>();
        }

        List<String> notRequiredFields = new ArrayList<>(fields.keySet());
        notRequiredFields.removeAll(requiredFields);

        List<String> placeholders = new ArrayList<>();
        List<Object> placeholderValues = new ArrayList<>();

        for (String requiredField : requiredFields) {
            placeholders.add("$L");
            placeholderValues.add(requiredField);
        }

        ClassName builtClass = ClassName.get(metaInfo.getFullPackage(), metaInfo.getClassName());
        MethodSpec.Builder builder = MethodSpec.methodBuilder("build");
        builder.addModifiers(Modifier.PUBLIC);
        builder.returns(builtClass);

        placeholderValues.add(0, builtClass);
        placeholderValues.add(0, "returning");
        placeholderValues.add(0, builtClass);
        builder.addStatement("$T $L = new $T(" + StringUtils.join(placeholders, ", ") + ")", placeholderValues.toArray());

        for (String field : notRequiredFields) {
            FieldModel fieldModel = fields.get(field);
            String setterName = Helper.getGetterSetterName(field, true, fieldModel.getFieldType() == FieldType.BOOLEAN, fieldModel.isPrimitive());
            builder.addStatement("$L.$L($L)", "returning", setterName, field);
        }

        builder.addStatement("return returning");

        builderBuilder.addMethod(builder.build());
    }

    private void createBuilderMethod(TypeSpec.Builder typeSpec, TypeModel typeModel) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("builder");
        builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        builder.returns(ClassName.get(metaInfo.getFullPackage(), metaInfo.getClassName(), "Builder"));

        Map<String, FieldModel> fields = typeModel.getFields();
        final List<String> requiredFields = getRequiredFields(fields, tune.excludeInitializedFieldsFromConstructor());

        List<String> placeholders = new ArrayList<>();
        List<Object> placeholderValues = new ArrayList<>();
        for (String field : requiredFields) {
            ParameterSpec.Builder parameterSpec = getParameterTypeHandler(fields.get(field).getFieldType(), tune.getParameterTypeHandlerSet())
                    .process(null, field, fields.get(field));

            if (!fields.get(field).isPrimitive()) {
                tune.getValidationAnnotatorStyles().forEach(style -> requiredParameterConstructorParadigm.accept(style, fields.get(field), parameterSpec));
            }

            builder.addParameter(parameterSpec.build());

            placeholders.add("$L");
            placeholderValues.add(field);
        }

        placeholderValues.add(0, ClassName.get(metaInfo.getFullPackage(), metaInfo.getClassName()));
        builder.addStatement("return new $T.Builder(" + StringUtils.join(placeholders, ", ") + ")", placeholderValues.toArray());

        typeSpec.addMethod(builder.build());
    }
}
