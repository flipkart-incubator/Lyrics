package com.flipkart.lyrics.interfaces.model;

import com.flipkart.lyrics.interfaces.typenames.Modifier;

/**
 * @author kushal.sharma on 17/08/17.
 */
public class NameModifiers {
    private String name;
    private Modifier[] modifiers;

    public NameModifiers(String name, Modifier[] modifiers) {
        this.name = name;
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public Modifier[] getModifiers() {
        return modifiers;
    }
}
