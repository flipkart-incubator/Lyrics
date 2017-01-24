package com.flipkart.lyrics.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by shrey.garg on 25/12/16.
 */
public final class JsonMapper {
    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static ObjectMapper get() {
        return mapper;
    }
}
