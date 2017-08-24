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

package com.flipkart.lyrics.processor.annotations;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.specs.TypeSpec;
import com.flipkart.lyrics.model.AnnotationModel;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.flipkart.lyrics.test.extensions.ConfigurationExtension.ANNOTATIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 12/12/16.
 */
@ExtendWith(ConfigurationExtension.class)
public class ClassAnnotationHandlerTest {

    @Test
    public void testNoClassAnnotations(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = new TypeModel();

        new ClassAnnotationHandler(tune, null, null).process(builder, model);

        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class found");
        assertEquals(0, spec.annotationSpecs.size(), "Annotations found");
    }

    @Test
    public void testTuneAnnotations(@TuneProvider(ANNOTATIONS) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = new TypeModel();

        new ClassAnnotationHandler(tune, null, null).process(builder, model);

        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class found");
        assertEquals(2, spec.annotationSpecs.size(), "Annotations found");

        //assertEquals(Deprecated.class.getName(), spec.annotationSpecs.get(0).clazz.toString(), "Wrong annotation found");
        //assertEquals(Resource.class.getName(), spec.annotationSpecs.get(1).clazz.toString(), "Wrong annotation found");
    }

    @Test
    public void testModelAnnotations(@TuneProvider Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        when(model.getAnnotations()).thenReturn(Arrays.asList(new AnnotationModel(Deprecated.class.getName()), new AnnotationModel(Resource.class.getName())));

        new ClassAnnotationHandler(tune, null, null).process(builder, model);

        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class found");
        assertEquals(2, spec.annotationSpecs.size(), "Annotations found");

       //assertEquals(Deprecated.class.getName(), spec.annotationSpecs.get(0).clazz.getName(), "Wrong annotation found");
       //assertEquals(Resource.class.getName(), spec.annotationSpecs.get(1).clazz.getName(), "Wrong annotation found");
    }

    @Test
    public void testModelAndTuneAnnotations(@TuneProvider(ANNOTATIONS) Tune tune) {
        TypeSpec.Builder builder = TypeSpec.classBuilder("Test");
        TypeModel model = mock(TypeModel.class);
        Map<String, String> members = new HashMap<>();
        members.put("value", "something");
        when(model.getAnnotations()).thenReturn(Arrays.asList(new AnnotationModel("com.xyz.jkl", members)));

        new ClassAnnotationHandler(tune, null, null).process(builder, model);

        TypeSpec spec = builder.build();

        assertEquals("Test", spec.name, "Wrong class found");
        assertEquals(3, spec.annotationSpecs.size(), "Annotations found");

        //assertEquals(Deprecated.class.getName(), spec.annotationSpecs.get(0).clazz.getName(), "Wrong annotation found");
        //assertEquals(Resource.class.getName(), spec.annotationSpecs.get(1).clazz.getName(), "Wrong annotation found");
        //assertEquals("com.xyz.jkl", spec.annotationSpecs.get(2).clazz.getName(), "Wrong annotation found");
        assertEquals(1, spec.annotationSpecs.get(2).members.size());
    }
}