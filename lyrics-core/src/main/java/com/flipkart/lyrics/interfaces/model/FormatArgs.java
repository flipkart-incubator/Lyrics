package com.flipkart.lyrics.interfaces.model;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class FormatArgs {
    private String format;
    private Object[] args;

    public FormatArgs(String format, Object... args) {
        this.format = format;
        this.args = args;
    }

    public String getFormat() {
        return format;
    }

    public Object[] getArgs() {
        return args;
    }
}
