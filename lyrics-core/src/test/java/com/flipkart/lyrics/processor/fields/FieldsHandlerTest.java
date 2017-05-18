package com.flipkart.lyrics.processor.fields;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.FieldType;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;
import com.flipkart.lyrics.sets.DefaultRuleSet;
import com.flipkart.lyrics.sets.RuleSet;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import com.squareup.javapoet.TypeSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.lang.model.element.Modifier;
import java.util.HashMap;
import java.util.Map;

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
    }

}