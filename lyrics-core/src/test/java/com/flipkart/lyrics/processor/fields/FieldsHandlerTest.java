package com.flipkart.lyrics.processor.fields;

import com.flipkart.lyrics.config.Tune;
import com.flipkart.lyrics.test.annotation.TuneProvider;
import com.flipkart.lyrics.test.extensions.ConfigurationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by shrey.garg on 17/05/17.
 */
@ExtendWith(ConfigurationExtension.class)
public class FieldsHandlerTest {
    @Test
    public void process(@TuneProvider Tune tune) {
    }

}