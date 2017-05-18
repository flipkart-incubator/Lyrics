package com.flipkart.lyrics.processor.fields;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.helper.Helper;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.FieldType;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.sets.DefaultRuleSet;
import com.flipkart.lyrics.sets.RuleSet;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.lang.model.element.Modifier;
import java.util.HashMap;
import java.util.Map;

import static com.flipkart.lyrics.helper.Helper.getGetterSetterName;
import static com.flipkart.lyrics.test.extensions.ConfigurationExtension.ADDITIONAL_FIELD_PROPERTY_HANDLER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 17/05/17.
 */
@ExtendWith(ConfigurationExtension.class)
public class FieldsHandlerTest {
    private final MetaInfo metaInfo = new MetaInfo("Sample", "com.test");

    @Test
    public void testSingleField(@TuneProvider Tune tune) {
        RuleSet ruleSet = new DefaultRuleSet(tune, metaInfo);
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder("Sample");

        Map<String, FieldModel> fieldModels = new HashMap<>();
        FieldModel fieldModel = mock(FieldModel.class);
        when(fieldModel.getFieldType()).thenReturn(FieldType.STRING);
        when(fieldModel.getModifiers()).thenReturn(new Modifier[] { Modifier.PUBLIC });
        fieldModels.put("one", fieldModel);

        TypeModel typeModel = mock(TypeModel.class);
        Mockito.when(typeModel.getFields()).thenReturn(fieldModels);

        FieldsHandler handler = new FieldsHandler(tune, metaInfo, ruleSet);
        handler.process(typeBuilder, typeModel);

        TypeSpec typeSpec = typeBuilder.build();

        assertEquals(1, typeSpec.fieldSpecs.size(), "Only one field should be present");
        assertEquals("one", typeSpec.fieldSpecs.get(0).name, "Field should have the expected name");

        assertEquals(2, typeSpec.methodSpecs.size(), "Getters and setters should be present");
        assertEquals(getGetterSetterName("one", false, false, false), typeSpec.methodSpecs.get(0).name, "Getter should be first");
        assertEquals(getGetterSetterName("one", true, false, false), typeSpec.methodSpecs.get(1).name, "Setter should be second");
    }

    @Test
    public void testMultipleFields(@TuneProvider Tune tune) {
        RuleSet ruleSet = new DefaultRuleSet(tune, metaInfo);
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder("Sample");

        Map<String, FieldModel> fieldModels = new HashMap<>();
        FieldModel fieldModel = mock(FieldModel.class);
        when(fieldModel.getFieldType()).thenReturn(FieldType.STRING);
        when(fieldModel.getModifiers()).thenReturn(new Modifier[] { Modifier.PUBLIC });

        FieldModel fieldModelTwo = mock(FieldModel.class);
        when(fieldModelTwo.getFieldType()).thenReturn(FieldType.STRING);
        when(fieldModelTwo.getModifiers()).thenReturn(new Modifier[] { Modifier.PUBLIC });

        fieldModels.put("one", fieldModel);
        fieldModels.put("two", fieldModelTwo);

        TypeModel typeModel = mock(TypeModel.class);
        Mockito.when(typeModel.getFields()).thenReturn(fieldModels);

        FieldsHandler handler = new FieldsHandler(tune, metaInfo, ruleSet);
        handler.process(typeBuilder, typeModel);

        TypeSpec typeSpec = typeBuilder.build();

        assertEquals(2, typeSpec.fieldSpecs.size(), "Only two fields should be present");
        assertEquals("one", typeSpec.fieldSpecs.get(0).name, "First field should have the expected name");
        assertEquals("two", typeSpec.fieldSpecs.get(1).name, "Second field should have the expected name");

        assertEquals(4, typeSpec.methodSpecs.size(), "Getters and setters should be present");
        assertEquals(getGetterSetterName("one", false, false, false), typeSpec.methodSpecs.get(0).name, "Getter should be first");
        assertEquals(getGetterSetterName("one", true, false, false), typeSpec.methodSpecs.get(1).name, "Setter should be second");

        assertEquals(getGetterSetterName("two", false, false, false), typeSpec.methodSpecs.get(2).name, "Getter should be first");
        assertEquals(getGetterSetterName("two", true, false, false), typeSpec.methodSpecs.get(3).name, "Setter should be second");
    }

    @Test
    public void testAdditionalFieldProperties(@TuneProvider(ADDITIONAL_FIELD_PROPERTY_HANDLER) Tune tune) {
        RuleSet ruleSet = new DefaultRuleSet(tune, metaInfo);
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder("Sample");

        Map<String, FieldModel> fieldModels = new HashMap<>();
        FieldModel fieldModel = mock(FieldModel.class);
        when(fieldModel.getFieldType()).thenReturn(FieldType.STRING);
        when(fieldModel.getModifiers()).thenReturn(new Modifier[] { Modifier.PUBLIC });

        Map<String, Object> additionalFields = new HashMap<>();
        additionalFields.put("abc", "something");
        when(fieldModel.getAdditionalFields()).thenReturn(additionalFields);

        FieldModel fieldModelTwo = mock(FieldModel.class);
        when(fieldModelTwo.getFieldType()).thenReturn(FieldType.STRING);
        when(fieldModelTwo.getModifiers()).thenReturn(new Modifier[] { Modifier.PUBLIC });

        Map<String, Object> additionalFieldsTwo = new HashMap<>();
        additionalFieldsTwo.put("xyz", "something");
        when(fieldModelTwo.getAdditionalFields()).thenReturn(additionalFieldsTwo);

        fieldModels.put("one", fieldModel);
        fieldModels.put("two", fieldModelTwo);

        TypeModel typeModel = mock(TypeModel.class);
        Mockito.when(typeModel.getFields()).thenReturn(fieldModels);

        FieldsHandler handler = new FieldsHandler(tune, metaInfo, ruleSet);
        handler.process(typeBuilder, typeModel);

        TypeSpec typeSpec = typeBuilder.build();

        assertEquals(1, typeSpec.fieldSpecs.size(), "Only one field should be present");
        assertEquals("one", typeSpec.fieldSpecs.get(0).name, "Field should have the expected name");

        assertEquals(2, typeSpec.methodSpecs.size(), "Getters and setters should be present");
        assertEquals(getGetterSetterName("one", false, false, false), typeSpec.methodSpecs.get(0).name, "Getter should be first");
        assertEquals(getGetterSetterName("one", true, false, false), typeSpec.methodSpecs.get(1).name, "Setter should be second");
    }
}