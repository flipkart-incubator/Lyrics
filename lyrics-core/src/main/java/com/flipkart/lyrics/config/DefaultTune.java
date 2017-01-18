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

package com.flipkart.lyrics.config;

import com.flipkart.lyrics.helper.TriConsumer;
import com.flipkart.lyrics.model.AnnotationModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.model.VariableModel;
import com.flipkart.lyrics.sets.DefaultFieldTypeHandlerSet;
import com.flipkart.lyrics.sets.FieldTypeHandlerSet;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shrey.garg on 26/11/16.
 */
public class DefaultTune implements Tune {

    private final FieldTypeHandlerSet fieldTypeHandlerSet = new DefaultFieldTypeHandlerSet();

    @Override
    public boolean areJacksonStyleAnnotationsNeeded() {
        return false;
    }

    @Override
    public boolean areGsonStyleAnnotationsNeeded() {
        return false;
    }

    @Override
    public Modifier getDefaultFieldModifier() {
        return Modifier.PRIVATE;
    }

    @Override
    public Modifier getDefaultClassModifier() {
        return Modifier.PUBLIC;
    }

    @Override
    public List<AnnotationModel> getClassLevelAnnotations() {
        return null;
    }

    @Override
    public boolean areAndroidValidationAnnotationsNeeded() {
        return false;
    }

    @Override
    public boolean areJsr303AnnotationsNeeded() {
        return false;
    }

    @Override
    public boolean areJsr305AnnotationsNeeded() {
        return false;
    }

    @Override
    public boolean areAccessorsNeeded() {
        return true;
    }

    @Override
    public boolean isToStringNeeded() {
        return true;
    }

    @Override
    public boolean areHashCodeAndEqualsNeeded() {
        return true;
    }

    @Override
    public boolean useCommonsLang3() {
        return false;
    }

    @Override
    public Set<VariableModel> interfaces() {
        return new HashSet<>();
    }

    @Override
    public boolean forceDefaultFieldModifiers() {
        return false;
    }

    @Override
    public Map<String, TriConsumer<TypeSpec.Builder, TypeModel, MetaInfo>> getSpecialInterfacesHandler() {
        return null;
    }

    @Override
    public FieldTypeHandlerSet getFieldTypeHandlerSet() {
        return fieldTypeHandlerSet;
    }
}
