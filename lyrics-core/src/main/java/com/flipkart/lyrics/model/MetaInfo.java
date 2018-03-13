package com.flipkart.lyrics.model;

import com.flipkart.lyrics.specs.TypeVariableName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shrey.garg on 23/12/16.
 */
public class MetaInfo {
    private final String className;
    private final String fullPackage;
    private final Map<String, TypeVariableName> genericVariables;
    private boolean overrideAccessorGeneration;

    public MetaInfo(String className, String fullPackage, Map<String, TypeVariableName> genericVariables) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = genericVariables;
        this.overrideAccessorGeneration = false;
    }

    public MetaInfo(String className, String fullPackage) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = new HashMap<>();
        this.overrideAccessorGeneration = false;
    }

    public String getClassName() {
        return className;
    }

    public String getFullPackage() {
        return fullPackage;
    }

    public Map<String, TypeVariableName> getGenericVariables() {
        return genericVariables;
    }

    public boolean isOverrideAccessorGeneration() {
        return overrideAccessorGeneration;
    }

    public void setOverrideAccessorGeneration(boolean overrideAccessorGeneration) {
        this.overrideAccessorGeneration = overrideAccessorGeneration;
    }
}
