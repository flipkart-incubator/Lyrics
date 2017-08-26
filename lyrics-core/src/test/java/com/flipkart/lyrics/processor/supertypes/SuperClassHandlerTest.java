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

package com.flipkart.lyrics.processor.supertypes;

import com.flipkart.lyrics.java.config.Tune;
import com.flipkart.lyrics.model.GenericVariableModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.model.VariableModel;
import com.flipkart.lyrics.java.specs.ClassName;
import com.flipkart.lyrics.java.specs.ParameterizedTypeName;
import com.flipkart.lyrics.java.specs.TypeName;
import com.flipkart.lyrics.java.specs.TypeSpec;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.flipkart.lyrics.java.helper.Helper.getTypeVariables;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 04/12/16.
 */
@ExtendWith(ConfigurationExtension.class)
public class SuperClassHandlerTest {

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testNoSuperClass(@TuneProvider Tune tune) {
        String fullPackage = "java.lang";
        String className = "Object";
        VariableModel variableModel = new VariableModel(fullPackage + "." + className);

        TypeModel model = mock(TypeModel.class);
        when(model.getExtendsType()).thenReturn(variableModel);

        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        MetaInfo metaInfo = new MetaInfo(null, null, new HashMap<>());

        new SuperClassHandler(tune, metaInfo, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        TypeName superclass = spec.superclass;
        assertTrue(superclass instanceof ClassName);

        ClassName superClassName = (ClassName) superclass;
        assertAll("Class and package name assertions",
                () -> assertEquals("java.lang", superClassName.packageName()),
                () -> assertEquals("Object", superClassName.simpleName()));
    }

    @Test
    public void testNormalSuperClass(@TuneProvider Tune tune) {
        String fullPackage = "com.flipkart.lyrics.test.classes";
        String className = "SuperClass";
        VariableModel variableModel = new VariableModel(fullPackage + "." + className);

        TypeModel model = mock(TypeModel.class);
        when(model.getExtendsType()).thenReturn(variableModel);

        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        MetaInfo metaInfo = new MetaInfo(null, null, new HashMap<>());

        new SuperClassHandler(tune, metaInfo, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        TypeName superclass = spec.superclass;
        assertTrue(superclass instanceof ClassName);

        ClassName superClassName = (ClassName) superclass;
        assertAll("Class and package name assertions",
                () -> assertEquals(fullPackage, superClassName.packageName()),
                () -> assertEquals(className, superClassName.simpleName()));
    }

    @Test
    public void testGenericSuperClass(@TuneProvider Tune tune) {
        String fullPackage = "com.flipkart.lyrics.test.classes";
        String className = "GenericSuperClass";

        VariableModel variableModel = mock(VariableModel.class);
        when(variableModel.getType()).thenReturn(fullPackage + "." + className);
        VariableModel[] variableModels = {new VariableModel("T"), new VariableModel("U"), new VariableModel("V")};
        when(variableModel.getTypes()).thenReturn(variableModels);

        TypeModel model = mock(TypeModel.class);
        when(model.getExtendsType()).thenReturn(variableModel);

        List<GenericVariableModel> genericVariables = Arrays.asList(new GenericVariableModel("T"), new GenericVariableModel("U"), new GenericVariableModel("V"));

        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        MetaInfo metaInfo = new MetaInfo(null, null, getTypeVariables(genericVariables));
        new SuperClassHandler(tune, metaInfo, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        TypeName superclass = spec.superclass;
        assertTrue(superclass instanceof ParameterizedTypeName);

        ParameterizedTypeName superClassName = (ParameterizedTypeName) superclass;
        assertAll("Class and package name assertions",
                () -> assertEquals(fullPackage, superClassName.rawType.packageName()),
                () -> assertEquals(className, superClassName.rawType.simpleName()));

        assertEquals(3, superClassName.typeArguments.size(), "Mismatch in number of generic variables");

        List<TypeName> typeArguments = superClassName.typeArguments;
        assertAll("Type variable validations",
                () -> assertEquals("T", typeArguments.get(0).toString()),
                () -> assertEquals("U", typeArguments.get(1).toString()),
                () -> assertEquals("V", typeArguments.get(2).toString()));
    }

    @Test
    public void testGenericSuperClassNoGenericVariables(@TuneProvider Tune tune) {
        String fullPackage = "com.flipkart.lyrics.test.classes";
        String className = "GenericSuperClass";
        VariableModel variableModel = new VariableModel(fullPackage + "." + className);

        TypeModel model = mock(TypeModel.class);
        when(model.getExtendsType()).thenReturn(variableModel);

        List<GenericVariableModel> genericVariables = Arrays.asList(new GenericVariableModel("T"), new GenericVariableModel("U"), new GenericVariableModel("V"));

        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        MetaInfo metaInfo = new MetaInfo(null, null, getTypeVariables(genericVariables));
        new SuperClassHandler(tune, metaInfo, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        TypeName superclass = spec.superclass;
        assertTrue(superclass instanceof ClassName);

        ClassName superClassName = (ClassName) superclass;
        assertAll("Class and package name assertions",
                () -> assertEquals(fullPackage, superClassName.packageName()),
                () -> assertEquals(className, superClassName.simpleName()));
    }
}