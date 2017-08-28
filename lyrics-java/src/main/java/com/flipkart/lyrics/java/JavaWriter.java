package com.flipkart.lyrics.java;

import com.flipkart.lyrics.specs.FileWriter;
import com.flipkart.lyrics.specs.TypeSpec;
import com.squareup.javapoet.JavaFile;

import java.io.File;
import java.io.IOException;

import static com.flipkart.lyrics.java.Util.getTypeSpec;

/**
 * @author kushal.sharma on 28/08/17.
 */
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
