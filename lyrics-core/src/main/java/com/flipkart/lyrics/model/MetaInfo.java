package com.flipkart.lyrics.model;

import com.flipkart.lyrics.annotators.AnnotationStyle;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shrey.garg on 23/12/16.
 */
public class MetaInfo {
    private final String className;
    private final String fullPackage;
    private final Map<String, TypeVariableName> genericVariables;
    private final List<AnnotationStyle> annotationStyles;

    public MetaInfo(String className, String fullPackage, Map<String, TypeVariableName> genericVariables, List<AnnotationStyle> annotationStyles) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = genericVariables;
        this.annotationStyles = annotationStyles;
    }

    public MetaInfo(String className, String fullPackage, Map<String, TypeVariableName> genericVariables) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = genericVariables;
        this.annotationStyles = new ArrayList<>();
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

    public List<AnnotationStyle> getAnnotationStyles() {
        return annotationStyles;
    }
}
