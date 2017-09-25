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
package com.flipkart.lyrics.java;

import com.flipkart.lyrics.specs.FileWriter;
import com.flipkart.lyrics.specs.TypeSpec;
import com.squareup.javapoet.JavaFile;

import java.io.File;
import java.io.IOException;

import static com.flipkart.lyrics.java.Util.getTypeSpec;

public class JavaWriter implements FileWriter {

    @Override
    public void writeToFile(TypeSpec typeSpec, String fullPackage, File targetFolder) {
        JavaFile javaFile = JavaFile.builder(fullPackage, getTypeSpec(typeSpec))
                .indent("    ")
                .skipJavaLangImports(true)
                .build();
        try {
            javaFile.writeTo(targetFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
