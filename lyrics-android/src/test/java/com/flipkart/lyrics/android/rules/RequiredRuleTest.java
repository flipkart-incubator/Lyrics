package com.flipkart.lyrics.android.rules;

import com.flipkart.lyrics.android.config.AndroidTune;
import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.model.FieldModel;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.rules.RequiredRule;
import com.squareup.javapoet.FieldSpec;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.flipkart.lyrics.helper.Helper.processValidationAnnotationStyles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shrey.garg on 15/01/17.
 */
public class RequiredRuleTest {

    @Test
    public void testAndroidRequiredRules() {
        FieldSpec.Builder builder = FieldSpec.builder(String.class, "test");
        FieldModel model = mock(FieldModel.class);
        when(model.isRequired()).thenReturn(true);

        Tune tune = new AndroidTune() {
            @Override
            public List<String> createStringDefsFor() {
                return new ArrayList<>();
            }
        };

        MetaInfo metaInfo = new MetaInfo(null, null, processValidationAnnotationStyles(tune));
        new RequiredRule(tune, metaInfo).process(builder, model);

        FieldSpec spec = builder.build();

        assertEquals("test", spec.name, "Wrong name found.");
        assertEquals(1, spec.annotations.size(), "Annotations not found.");

        assertEquals("android.support.annotation.NonNull", spec.annotations.get(0).type.toString(), "Android support annotation not found.");
    }

}
