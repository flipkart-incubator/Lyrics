package com.flipkart.lyrics.model;

/**
 * Created by shrey.garg on 23/12/16.
 */
public class MetaInfo {
    private String className;
    private String fullPackage;

    public MetaInfo() {
    }

    public MetaInfo(String className, String fullPackage) {
        this.className = className;
        this.fullPackage = fullPackage;
    }

    public String getClassName() {
        return className;
    }

    public String getFullPackage() {
        return fullPackage;
    }
}
