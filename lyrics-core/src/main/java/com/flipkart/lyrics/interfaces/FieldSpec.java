package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Modifier;
import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.model.TypeName;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class FieldSpec {
    protected FieldSpec() { }

    public Object getFieldSpec() {
        return null;
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return Song.factory.createFieldBuilder(typeName, name, modifiers);
    }

    public static abstract class Builder {
        public TypeName typeName;
        public String name;
        public Modifier[] modifier;

        public Builder(TypeName typeName, String name, Modifier... modifiers) {
            this.typeName = typeName;
            this.name = name;
            this.modifier = modifiers;
        }

        public abstract Builder initializer(String format, Object... args);

        public abstract Builder addAnnotation(Class<?> clazz);

        public abstract Builder addAnnotation(AnnotationSpec annotationSpec);

        public abstract Builder addModifiers(Modifier... modifiers);

        public abstract FieldSpec build();
    }
}
