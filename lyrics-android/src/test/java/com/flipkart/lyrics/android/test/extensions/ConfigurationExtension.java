/*
 * Copyright 2016 Flipkart Internet, pvt ltd.
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

package com.flipkart.lyrics.android.test.extensions;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.android.TestTune;
import com.flipkart.lyrics.android.annotators.validations.AndroidValidationStyle;
import com.flipkart.lyrics.android.test.annotation.TuneProvider;
import com.flipkart.lyrics.annotators.GsonStyle;
import com.flipkart.lyrics.annotators.JacksonStyle;
import com.flipkart.lyrics.annotators.validations.Jsr303Style;
import com.flipkart.lyrics.annotators.validations.Jsr305Style;
import com.flipkart.lyrics.java.config.Tune;
import com.flipkart.lyrics.model.AnnotationModel;
import com.flipkart.lyrics.model.VariableModel;
import com.flipkart.lyrics.processor.fields.FieldAdditionalHandler;
import com.flipkart.lyrics.sets.RuleSet;
import com.flipkart.lyrics.java.specs.FieldSpec;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import javax.annotation.Resource;
import java.util.*;

import static com.flipkart.lyrics.java.helper.Injector.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 05/12/16.
 */
public class ConfigurationExtension implements ParameterResolver {
    public static final String DEFAULT = "default";
    public static final String JACKSON = "jackson";
    public static final String GSON = "gson";
    public static final String INTERFACES = "specs";
    public static final String NO_MODIFIERS = "no-modifiers";
    public static final String REQUIRED = "required";
    public static final String JSR_303 = "jsr-303";
    public static final String JSR_305 = "jsr-305";
    public static final String ANDROID_SUPPORT = "android-support";
    public static final String ANNOTATIONS = "annotations";
    public static final String ADDITIONAL_FIELD_PROPERTY_HANDLER = "addPropHandler";

    private Tune getDefaultTune() {
        Tune tune = new TestTune();
        RuleSet ruleSet = processRuleSet(tune, null);
        processHandlerSet(tune, null, ruleSet);
        processFieldTypeHandlerSet(tune, null);
        processParameterTypeHandlerSet(tune, null);
        processFieldAdditionalHandlers(tune, null);
        processTypeAdditionalHandlers(tune, null);
        processFieldModificationHandlers(tune, null);
        Song.factory = tune.createFactory();
        return tune;
    }

    private Tune getAnnotationsTune() {
        Tune jacksonTune = spy(TestTune.class);
        List<AnnotationModel> annotationModels = Arrays.asList(new AnnotationModel(Deprecated.class.getName()), new AnnotationModel(Resource.class.getName()));
        when(jacksonTune.getClassLevelAnnotations()).thenReturn(annotationModels);
        Song.factory = jacksonTune.createFactory();
        return jacksonTune;
    }

    private Tune getJacksonTune() {
        Tune jacksonTune = spy(TestTune.class);
        when(jacksonTune.getAnnotatorStyles()).thenReturn(Collections.singletonList(new JacksonStyle()));
        Song.factory = jacksonTune.createFactory();
        return jacksonTune;
    }

    private Tune getGsonTune() {
        Tune gsonTune = spy(TestTune.class);
        when(gsonTune.getAnnotatorStyles()).thenReturn(Collections.singletonList(new GsonStyle()));
        Song.factory = gsonTune.createFactory();
        return gsonTune;
    }

    private Tune getRequiredTune() {
        Tune requireTune = spy(TestTune.class);
        when(requireTune.getValidationAnnotatorStyles()).thenReturn(Collections.singletonList(new Jsr303Style()));
        Song.factory = requireTune.createFactory();
        return requireTune;
    }

    private Tune getJsr303Tune() {
        Tune requireTune = spy(TestTune.class);
        when(requireTune.getValidationAnnotatorStyles()).thenReturn(Collections.singletonList(new Jsr303Style()));
        Song.factory = requireTune.createFactory();
        return requireTune;
    }

    private Tune getJsr305Tune() {
        Tune requireTune = spy(TestTune.class);
        when(requireTune.getValidationAnnotatorStyles()).thenReturn(Collections.singletonList(new Jsr305Style()));
        Song.factory = requireTune.createFactory();
        return requireTune;
    }

    private Tune getInterfacesTune() {
        Tune interfacesTune = spy(TestTune.class);
        Set<VariableModel> interfaces = new HashSet<>();
        interfaces.add(new VariableModel("java.io.Serializable"));
        interfaces.add(
                new VariableModel("com.flipkart.lyrics.test.classes.GenericInterface",
                        new VariableModel[]{new VariableModel("T")}));
        when(interfacesTune.interfaces()).thenReturn(interfaces);
        Song.factory = interfacesTune.createFactory();
        return interfacesTune;
    }

    private Tune getNoModifiersTune() {
        Tune jacksonTune = spy(TestTune.class);
        when(jacksonTune.getDefaultClassModifier()).thenReturn(null);
        Song.factory = jacksonTune.createFactory();
        return jacksonTune;
    }


    private Tune getAndroidSpportTune() {
        Tune jacksonTune = spy(TestTune.class);
        when(jacksonTune.getValidationAnnotatorStyles()).thenReturn(Collections.singletonList(new AndroidValidationStyle()));
        Song.factory = jacksonTune.createFactory();
        return jacksonTune;
    }

    private Tune getAdditionalFieldPropertiesHandlerTune() {
        Tune additionalFieldPropertyHandlerTune = spy(getDefaultTune());
        Map<String, FieldAdditionalHandler> additionalHandlerMap = new HashMap<>();
        additionalHandlerMap.put("abc", new AdditionalFieldHandlerTestImpl());
        additionalHandlerMap.put("xyz", new NoOpAdditionalFieldHandlerTestImpl());
        when(additionalFieldPropertyHandlerTune.getFieldsAdditionalPropertiesHandler()).thenReturn(additionalHandlerMap);
        return additionalFieldPropertyHandlerTune;
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(TuneProvider.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        String tuneType = parameterContext.getParameter().getAnnotation(TuneProvider.class).value();
        switch (tuneType) {
            case DEFAULT:
                return getDefaultTune();
            case JACKSON:
                return getJacksonTune();
            case INTERFACES:
                return getInterfacesTune();
            case NO_MODIFIERS:
                return getNoModifiersTune();
            case REQUIRED:
                return getRequiredTune();
            case JSR_303:
                return getJsr303Tune();
            case JSR_305:
                return getJsr305Tune();
            case ANDROID_SUPPORT:
                return getAndroidSpportTune();
            case GSON:
                return getGsonTune();
            case ANNOTATIONS:
                return getAnnotationsTune();
            case ADDITIONAL_FIELD_PROPERTY_HANDLER:
                return getAdditionalFieldPropertiesHandlerTune();
            default:
                return null;
        }
    }
}

class AdditionalFieldHandlerTestImpl extends FieldAdditionalHandler {
    @Override
    public boolean process(FieldSpec.Builder fieldBuilder, String key, Object value) {
        return true;
    }
}

class NoOpAdditionalFieldHandlerTestImpl extends FieldAdditionalHandler {
    @Override
    public boolean process(FieldSpec.Builder fieldBuilder, String key, Object value) {
        return false;
    }
}