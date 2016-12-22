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

package com.flipkart.lyrics;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.TypeModel;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

/**
 * Created by shrey.garg on 27/11/16.
 */
public class Song {
    private Tune configuration;

    public Song(Tune configuration) {
        this.configuration = configuration;
    }

    public void createType(String name, String fullPackage, TypeModel typeModel, File targetFolder) throws IOException {
        TypeSpec.Builder typeBuilder = typeModel.getType().getCreator().process(name, typeModel, configuration);
        JavaFile javaFile = JavaFile.builder(fullPackage, typeBuilder.build())
                .indent("    ")
                .skipJavaLangImports(true)
                .build();
        javaFile.writeTo(targetFolder);
    }
}
