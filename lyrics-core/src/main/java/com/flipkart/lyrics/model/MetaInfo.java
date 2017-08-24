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

    public MetaInfo(String className, String fullPackage, Map<String, TypeVariableName> genericVariables) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = genericVariables;
    }

    public MetaInfo(String className, String fullPackage) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = new HashMap<>();
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
}
