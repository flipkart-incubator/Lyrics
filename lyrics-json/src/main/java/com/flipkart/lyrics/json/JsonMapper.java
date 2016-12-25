package com.flipkart.lyrics.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by shrey.garg on 25/12/16.
 */
public final class JsonMapper {
    private final static ObjectMapper mapper = new ObjectMapper();

    static {

    }

    public static ObjectMapper get() {
        return mapper;
    }
}
