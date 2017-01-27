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

package com.flipkart.lyrics.test.extensions;

import com.flipkart.lyrics.annotators.GsonStyle;
import com.flipkart.lyrics.annotators.JacksonStyle;
import com.flipkart.lyrics.annotators.validations.Jsr303Style;
import com.flipkart.lyrics.annotators.validations.Jsr305Style;
import com.flipkart.lyrics.config.DefaultTune;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.AnnotationModel;
import com.flipkart.lyrics.model.VariableModel;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import javax.annotation.Resource;
import java.util.*;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 05/12/16.
 */
public class ConfigurationExtension implements ParameterResolver {
    public static final String DEFAULT = "default";
    public static final String JACKSON = "jackson";
    public static final String GSON = "gson";
    public static final String INTERFACES = "interfaces";
    public static final String NO_MODIFIERS = "no-modifiers";
    public static final String REQUIRED = "required";
    public static final String JSR_303 = "jsr-303";
    public static final String JSR_305 = "jsr-305";
    public static final String ANDROID_SUPPORT = "android-support";
    public static final String ANNOTATIONS = "annotations";

    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(TuneProvider.class);
    }

    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
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
            case GSON:
                return getGsonTune();
            case ANNOTATIONS:
                return getAnnotationsTune();
            default:
                return null;
        }
    }

    private Tune getDefaultTune() {
        return new DefaultTune();
    }

    private Tune getAnnotationsTune() {
        Tune jacksonTune = spy(DefaultTune.class);
        List<AnnotationModel> annotationModels = Arrays.asList(new AnnotationModel(Deprecated.class.getName()), new AnnotationModel(Resource.class.getName()));
        when(jacksonTune.getClassLevelAnnotations()).thenReturn(annotationModels);
        return jacksonTune;
    }

    private Tune getJacksonTune() {
        Tune jacksonTune = spy(DefaultTune.class);
        when(jacksonTune.getAnnotatorStyles()).thenReturn(Collections.singletonList(new JacksonStyle()));
        return jacksonTune;
    }

    private Tune getGsonTune() {
        Tune gsonTune = spy(DefaultTune.class);
        when(gsonTune.getAnnotatorStyles()).thenReturn(Collections.singletonList(new GsonStyle()));
        return gsonTune;
    }

    private Tune getRequiredTune() {
        Tune requireTune = spy(DefaultTune.class);
        when(requireTune.getValidationAnnotatorStyles()).thenReturn(Collections.singletonList(new Jsr303Style()));
        return requireTune;
    }

    private Tune getJsr303Tune() {
        Tune requireTune = spy(DefaultTune.class);
        when(requireTune.getValidationAnnotatorStyles()).thenReturn(Collections.singletonList(new Jsr303Style()));
        return requireTune;
    }

    private Tune getJsr305Tune() {
        Tune requireTune = spy(DefaultTune.class);
        when(requireTune.getValidationAnnotatorStyles()).thenReturn(Collections.singletonList(new Jsr305Style()));
        return requireTune;
    }

    private Tune getInterfacesTune() {
        Tune interfacesTune = spy(DefaultTune.class);
        Set<VariableModel> interfaces = new HashSet<>();
        interfaces.add(new VariableModel("java.io.Serializable"));
        interfaces.add(
                new VariableModel("com.flipkart.lyrics.test.classes.GenericInterface",
                new VariableModel[] { new VariableModel("T") }));
        when(interfacesTune.interfaces()).thenReturn(interfaces);
        return interfacesTune;
    }

    private Tune getNoModifiersTune() {
        Tune jacksonTune = spy(DefaultTune.class);
        when(jacksonTune.getDefaultClassModifier()).thenReturn(null);
        return jacksonTune;
    }

}
