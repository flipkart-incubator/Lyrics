package com.flipkart.lyrics.interfaces.model;

/**
 * @author kushal.sharma on 16/08/17.
 */
public class TypeArgsFormatArgs {
    private String typeArgsFormat;
    private Object[] args;

    public TypeArgsFormatArgs(String typeArgsFormat, Object[] args) {
        this.typeArgsFormat = typeArgsFormat;
        this.args = args;
    }

    public String getTypeArgsFormat() {
        return typeArgsFormat;
    }

    public Object[] getArgs() {
        return args;
    }
}
