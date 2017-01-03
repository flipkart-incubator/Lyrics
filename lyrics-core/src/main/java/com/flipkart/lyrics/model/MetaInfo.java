package com.flipkart.lyrics.model;

import com.flipkart.lyrics.annotators.AnnotationStyle;
import com.flipkart.lyrics.annotators.validations.ValidationAnnotatorStyle;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.HashMap;
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
    private final List<ValidationAnnotatorStyle> validationAnnotatorStyles;

    public MetaInfo(String className, String fullPackage, Map<String, TypeVariableName> genericVariables, List<AnnotationStyle> annotationStyles, List<ValidationAnnotatorStyle> validationAnnotatorStyles) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = genericVariables;
        this.annotationStyles = annotationStyles;
        this.validationAnnotatorStyles = validationAnnotatorStyles;
    }

    public MetaInfo(String className, String fullPackage, Map<String, TypeVariableName> genericVariables, List<AnnotationStyle> annotationStyles) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = genericVariables;
        this.annotationStyles = annotationStyles;
        this.validationAnnotatorStyles = new ArrayList<>();
    }

    public MetaInfo(String className, String fullPackage, List<AnnotationStyle> annotationStyles, List<ValidationAnnotatorStyle> validationAnnotatorStyles) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = new HashMap<>();
        this.annotationStyles = annotationStyles;
        this.validationAnnotatorStyles = validationAnnotatorStyles;
    }

    public MetaInfo(String className, String fullPackage, List<ValidationAnnotatorStyle> validationAnnotatorStyles) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = new HashMap<>();
        this.annotationStyles = new ArrayList<>();
        this.validationAnnotatorStyles = validationAnnotatorStyles;
    }

    public MetaInfo(String className, String fullPackage) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = new HashMap<>();
        this.annotationStyles = new ArrayList<>();
        this.validationAnnotatorStyles = new ArrayList<>();
    }

    public MetaInfo(String className, String fullPackage, Map<String, TypeVariableName> genericVariables) {
        this.className = className;
        this.fullPackage = fullPackage;
        this.genericVariables = genericVariables;
        this.annotationStyles = new ArrayList<>();
        this.validationAnnotatorStyles = new ArrayList<>();
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

    public List<ValidationAnnotatorStyle> getValidationAnnotatorStyles() {
        return validationAnnotatorStyles;
    }
}
