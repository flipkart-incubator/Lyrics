package com.flipkart.lyrics.interfaces.model;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class NameFormatArgs extends FormatArgs {
    private String name;

    public NameFormatArgs(String name, String format, Object... args) {
        super(format, args);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
