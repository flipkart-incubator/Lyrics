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

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.GenericVariableModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.model.VariableModel;
import com.flipkart.lyrics.specs.ClassName;
import com.flipkart.lyrics.specs.ParameterizedTypeName;
import com.flipkart.lyrics.specs.TypeName;
import com.flipkart.lyrics.specs.TypeSpec;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import static com.flipkart.lyrics.helper.Helper.getTypeVariables;
import static com.flipkart.lyrics.test.extensions.ConfigurationExtension.INTERFACES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 05/12/16.
 */
@ExtendWith(ConfigurationExtension.class)
public class InterfaceHandlerTest {

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testNoInterfaces(@TuneProvider Tune tune) {
        TypeModel model = new TypeModel();
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        new InterfaceHandler(tune, null, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        List<TypeName> interfaces = spec.superinterfaces;
        assertEquals(0, interfaces.size(), "Interfaces found");
    }

    @Test
    public void testSingleInterface(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");

        TypeModel model = mock(TypeModel.class);
        Set<VariableModel> interfaceSet = new HashSet<>();
        interfaceSet.add(new VariableModel("java.io.Serializable"));
        when(model.getInterfaces()).thenReturn(interfaceSet);

        MetaInfo metaInfo = new MetaInfo(null, null, new HashMap<>());
        new InterfaceHandler(tune, metaInfo, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        List<TypeName> interfaces = spec.superinterfaces;
        assertEquals(1, interfaces.size(), "No interfaces found");

        TypeName interfaceType = interfaces.get(0);
        assertTrue(interfaceType instanceof ClassName, "Wrong type of TypeName found");

        ClassName interfaceClassName = (ClassName) interfaceType;
        assertAll("Class and package name assertions",
                () -> assertEquals("java.io", interfaceClassName.packageName()),
                () -> assertEquals("Serializable", interfaceClassName.simpleName()));
    }

    @Test
    public void testMultipleInterfaces(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");

        TypeModel model = mock(TypeModel.class);
        Set<VariableModel> interfaceSet = new HashSet<>();
        interfaceSet.add(new VariableModel("java.io.Serializable"));
        interfaceSet.add(new VariableModel("java.lang.Cloneable"));
        when(model.getInterfaces()).thenReturn(interfaceSet);

        MetaInfo metaInfo = new MetaInfo(null, null, new HashMap<>());
        new InterfaceHandler(tune, metaInfo, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        List<TypeName> interfaces = spec.superinterfaces;
        assertEquals(2, interfaces.size(), "No interfaces found");

        TypeName interfaceType = interfaces.get(0);
        assertTrue(interfaceType instanceof ClassName, "Wrong type of TypeName found");

        ClassName interfaceClassName = (ClassName) interfaceType;
        assertAll("Class and package name assertions",
                () -> assertEquals("java.io", interfaceClassName.packageName()),
                () -> assertEquals("Serializable", interfaceClassName.simpleName()));

        TypeName interfaceTypeTwo = interfaces.get(1);
        assertTrue(interfaceTypeTwo instanceof ClassName, "Wrong type of TypeName found");

        ClassName interfaceTwoClassName = (ClassName) interfaceTypeTwo;
        assertAll("Class and package name assertions",
                () -> assertEquals("java.lang", interfaceTwoClassName.packageName()),
                () -> assertEquals("Cloneable", interfaceTwoClassName.simpleName()));
    }

    @Test
    public void testTuneInterfaces(@TuneProvider(INTERFACES) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");

        TypeModel model = new TypeModel();

        List<GenericVariableModel> genericVariables = Collections.singletonList(new GenericVariableModel("T"));
        MetaInfo metaInfo = new MetaInfo(null, null, getTypeVariables(genericVariables));
        new InterfaceHandler(tune, metaInfo, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        List<TypeName> interfaces = spec.superinterfaces;
        assertEquals(2, interfaces.size(), "No interfaces found");

        TypeName interfaceType = interfaces.get(0);
        assertTrue(interfaceType instanceof ClassName, "Wrong type of TypeName found");

        ClassName interfaceClassName = (ClassName) interfaceType;
        assertAll("Class and package name assertions",
                () -> assertEquals("java.io", interfaceClassName.packageName()),
                () -> assertEquals("Serializable", interfaceClassName.simpleName()));

        TypeName interfaceTypeThree = interfaces.get(1);
        assertTrue(interfaceTypeThree instanceof ParameterizedTypeName);

        ParameterizedTypeName interfaceThreeClassName = (ParameterizedTypeName) interfaceTypeThree;
        assertAll("Class and package name assertions",
                () -> assertEquals("com.flipkart.lyrics.test.classes", interfaceThreeClassName.rawType.packageName()),
                () -> assertEquals("GenericInterface", interfaceThreeClassName.rawType.simpleName()));

        assertEquals(1, interfaceThreeClassName.typeArguments.size(), "Mismatch in number of generic variables");
    }

    @Test
    public void testTuneAndModelConflictingInterfaces(@TuneProvider(INTERFACES) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");

        TypeModel model = mock(TypeModel.class);
        Set<VariableModel> interfaceSet = new HashSet<>();
        interfaceSet.add(new VariableModel("java.io.Serializable"));
        when(model.getInterfaces()).thenReturn(interfaceSet);

        List<GenericVariableModel> genericVariables = Collections.singletonList(new GenericVariableModel("T"));
        MetaInfo metaInfo = new MetaInfo(null, null, getTypeVariables(genericVariables));
        new InterfaceHandler(tune, metaInfo, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        List<TypeName> interfaces = spec.superinterfaces;
        assertEquals(2, interfaces.size(), "No interfaces found");

        TypeName interfaceType = interfaces.get(0);
        assertTrue(interfaceType instanceof ClassName, "Wrong type of TypeName found");

        ClassName interfaceClassName = (ClassName) interfaceType;
        assertAll("Class and package name assertions",
                () -> assertEquals("java.io", interfaceClassName.packageName()),
                () -> assertEquals("Serializable", interfaceClassName.simpleName()));

        TypeName interfaceTypeThree = interfaces.get(1);
        assertTrue(interfaceTypeThree instanceof ParameterizedTypeName);

        ParameterizedTypeName interfaceThreeClassName = (ParameterizedTypeName) interfaceTypeThree;
        assertAll("Class and package name assertions",
                () -> assertEquals("com.flipkart.lyrics.test.classes", interfaceThreeClassName.rawType.packageName()),
                () -> assertEquals("GenericInterface", interfaceThreeClassName.rawType.simpleName()));

        assertEquals(1, interfaceThreeClassName.typeArguments.size(), "Mismatch in number of generic variables");
    }

    @Test
    public void testTuneAndModelMixInterfaces(@TuneProvider(INTERFACES) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");

        TypeModel model = mock(TypeModel.class);
        Set<VariableModel> interfaceSet = new HashSet<>();
        interfaceSet.add(new VariableModel("java.io.Serializable"));
        interfaceSet.add(new VariableModel("java.lang.Cloneable"));
        when(model.getInterfaces()).thenReturn(interfaceSet);

        List<GenericVariableModel> genericVariables = Collections.singletonList(new GenericVariableModel("T"));
        MetaInfo metaInfo = new MetaInfo(null, null, getTypeVariables(genericVariables));
        new InterfaceHandler(tune, metaInfo, null).process(builder, model);
        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class name found");

        List<TypeName> interfaces = spec.superinterfaces;
        assertEquals(3, interfaces.size(), "No interfaces found");

        TypeName interfaceType = interfaces.get(0);
        assertTrue(interfaceType instanceof ClassName, "Wrong type of TypeName found");

        ClassName interfaceClassName = (ClassName) interfaceType;
        assertAll("Class and package name assertions",
                () -> assertEquals("java.io", interfaceClassName.packageName()),
                () -> assertEquals("Serializable", interfaceClassName.simpleName()));

        TypeName interfaceTypeThree = interfaces.get(1);
        assertTrue(interfaceTypeThree instanceof ParameterizedTypeName);

        ParameterizedTypeName interfaceThreeClassName = (ParameterizedTypeName) interfaceTypeThree;
        assertAll("Class and package name assertions",
                () -> assertEquals("com.flipkart.lyrics.test.classes", interfaceThreeClassName.rawType.packageName()),
                () -> assertEquals("GenericInterface", interfaceThreeClassName.rawType.simpleName()));

        assertEquals(1, interfaceThreeClassName.typeArguments.size(), "Mismatch in number of generic variables");

        TypeName interfaceTypeTwo = interfaces.get(2);
        assertTrue(interfaceTypeTwo instanceof ClassName, "Wrong type of TypeName found");

        ClassName interfaceTwoClassName = (ClassName) interfaceTypeTwo;
        assertAll("Class and package name assertions",
                () -> assertEquals("java.lang", interfaceTwoClassName.packageName()),
                () -> assertEquals("Cloneable", interfaceTwoClassName.simpleName()));
    }
}