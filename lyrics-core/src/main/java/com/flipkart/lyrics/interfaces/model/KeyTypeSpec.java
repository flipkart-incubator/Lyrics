package com.flipkart.lyrics.interfaces.model;

import com.flipkart.lyrics.interfaces.TypeSpec;

/**
 * @author kushal.sharma on 16/08/17.
 */
public class KeyTypeSpec {
    private String key;
    private TypeSpec typeSpec;

    public KeyTypeSpec(String key, TypeSpec typeSpec) {
        this.key = key;
        this.typeSpec = typeSpec;
    }

    public String getKey() {
        return key;
    }

    public TypeSpec getTypeSpec() {
        return typeSpec;
    }
}
