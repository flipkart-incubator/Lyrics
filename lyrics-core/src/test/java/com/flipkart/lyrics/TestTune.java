package com.flipkart.lyrics;

import com.flipkart.lyrics.annotators.AnnotatorStyle;
import com.flipkart.lyrics.annotators.validations.ValidationAnnotatorStyle;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.helper.TriConsumer;
import com.flipkart.lyrics.interfaces.TypeSpec;
import com.flipkart.lyrics.interfaces.contract.Factory;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.model.AnnotationModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.model.VariableModel;
import com.flipkart.lyrics.processor.fields.FieldAdditionalHandler;
import com.flipkart.lyrics.processor.fields.FieldModificationHandler;
import com.flipkart.lyrics.processor.types.TypeAdditionalHandler;
import com.flipkart.lyrics.sets.*;
import com.flipkart.lyrics.styles.objectmethods.ObjectMethodsStyle;

import java.util.*;

/**
 * @author kushal.sharma on 23/08/17.
 */
public class TestTune implements Tune {

    private final FieldTypeHandlerSet fieldTypeHandlerSet = new DefaultFieldTypeHandlerSet();
    private final ParameterTypeHandlerSet parameterTypeHandlerSet = new DefaultParameterTypeHandlerSet();
    private final CreatorSet creatorSet = new DefaultCreatorSet();
    private final HandlerSet handlerSet = new DefaultHandlerSet();
    private final RuleSet ruleSet = new DefaultRuleSet();
    private final List<AnnotatorStyle> annotatorStyles = new ArrayList<>();
    private final List<ValidationAnnotatorStyle> validationAnnotatorStyles = new ArrayList<>();
    private final ObjectMethodsStyle objectMethodsStyle = null;
    private final Set<VariableModel> interfaces = new HashSet<>();
    private final Map<String, FieldAdditionalHandler> fieldAdditionalHandlerMap = new HashMap<>();
    private final Map<String, TypeAdditionalHandler> typeAdditionalHandlerMap = new HashMap<>();
    private final Map<String, FieldModificationHandler> fieldModificationHandlers = new HashMap<>();

    @Override
    public Modifier getDefaultFieldModifier() {
        return Modifier.PRIVATE;
    }

    @Override
    public Modifier getDefaultClassModifier() {
        return Modifier.PUBLIC;
    }

    @Override
    public List<AnnotationModel> getClassLevelAnnotations() {
        return null;
    }

    @Override
    public List<AnnotatorStyle> getAnnotatorStyles() {
        return annotatorStyles;
    }

    @Override
    public List<ValidationAnnotatorStyle> getValidationAnnotatorStyles() {
        return validationAnnotatorStyles;
    }

    @Override
    public boolean areAccessorsNeeded() {
        return true;
    }

    @Override
    public boolean isToStringNeeded() {
        return false;
    }

    @Override
    public boolean areHashCodeAndEqualsNeeded() {
        return false;
    }

    @Override
    public boolean isRequiredFieldConstructorNeeded() {
        return true;
    }

    @Override
    public boolean excludeInitializedFieldsFromConstructor() {
        return false;
    }

    @Override
    public boolean isCustomConstructorNeeded() {
        return false;
    }

    @Override
    public ObjectMethodsStyle getObjectMethodsStyle() {
        return objectMethodsStyle;
    }

    @Override
    public Set<VariableModel> interfaces() {
        return interfaces;
    }

    @Override
    public boolean forceDefaultFieldModifiers() {
        return false;
    }

    @Override
    public Map<String, TriConsumer<TypeSpec.Builder, TypeModel, MetaInfo>> getSpecialInterfacesHandler() {
        return null;
    }

    @Override
    public Map<String, FieldAdditionalHandler> getFieldsAdditionalPropertiesHandler() {
        return fieldAdditionalHandlerMap;
    }

    @Override
    public Map<String, FieldModificationHandler> getFieldModificationHandlers() {
        return fieldModificationHandlers;
    }

    @Override
    public Map<String, TypeAdditionalHandler> getTypeAdditionalPropertiesHandler() {
        return typeAdditionalHandlerMap;
    }

    @Override
    public CreatorSet getCreatorSet() {
        return creatorSet;
    }

    @Override
    public HandlerSet getHandlerSet() {
        return handlerSet;
    }

    @Override
    public RuleSet getRuleSet() {
        return ruleSet;
    }

    @Override
    public FieldTypeHandlerSet getFieldTypeHandlerSet() {
        return fieldTypeHandlerSet;
    }

    @Override
    public ParameterTypeHandlerSet getParameterTypeHandlerSet() {
        return parameterTypeHandlerSet;
    }

    @Override
    public Factory createFactory() {
        return new TestFactory();
    }
}

