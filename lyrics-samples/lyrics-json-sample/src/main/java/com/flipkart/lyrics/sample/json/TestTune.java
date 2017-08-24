package com.flipkart.lyrics.sample.json;

import com.flipkart.lyrics.config.DefaultTune;
import com.flipkart.lyrics.helper.TriConsumer;
import com.flipkart.lyrics.specs.TypeSpec;
import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.model.TypeModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kushal.sharma on 18/08/17.
 */
public class TestTune extends DefaultTune {
    private static final Map<String, TriConsumer<TypeSpec.Builder, TypeModel, MetaInfo>> specialHandlers = new HashMap<>();

    static {
        specialHandlers.put("com.flipkart.lyrics.sample.userdb.people.TestInterface2", SpecialInterfaces::processTestInterface2);
    }

    @Override
    public Map<String, TriConsumer<TypeSpec.Builder, TypeModel, MetaInfo>> getSpecialInterfacesHandler() {
        return specialHandlers;
    }
}
