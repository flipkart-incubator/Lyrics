package com.flipkart.lyrics.specs;

import java.io.File;

/**
 * @author kushal.sharma on 28/08/17.
 */
public interface FileWriter {
    void writeToFile(TypeSpec typeSpec, String fullPackage, File targetFolder);
}
