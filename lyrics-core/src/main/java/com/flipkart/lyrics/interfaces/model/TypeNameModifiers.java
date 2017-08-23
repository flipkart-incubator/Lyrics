package com.flipkart.lyrics.interfaces.model;

import com.flipkart.lyrics.interfaces.typenames.Modifier;

import java.lang.reflect.Type;

/**
 * @author kushal.sharma on 17/08/17.
 */
public class TypeNameModifiers extends NameModifiers {
    private Type type;

    public TypeNameModifiers(Type type, String name, Modifier[] modifiers) {
        super(name, modifiers);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
