package com.flipkart.lyrics.interfaces.model;

import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

/**
 * @author kushal.sharma on 17/08/17.
 */
public class TypeNameNameModifiers extends NameModifiers {
    private TypeName typeName;

    public TypeNameNameModifiers(TypeName typeName, String name, Modifier[] modifiers) {
        super(name, modifiers);
        this.typeName = typeName;
    }

    public TypeName getTypeName() {
        return typeName;
    }
}
