package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class FieldSpec {
    public final String name;
    public final TypeName type;

    protected FieldSpec(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return Song.factory.createFieldBuilder(typeName, name, modifiers);
    }

    public Object getFieldSpec() {
        return null;
    }

    public static abstract class Builder {
        public TypeName type;
        public String name;
        public Modifier[] modifier;

        public Builder(TypeName type, String name, Modifier... modifiers) {
            this.type = type;
            this.name = name;
            this.modifier = modifiers;
        }

        public abstract Builder initializer(String format, Object... args);

        public abstract Builder addAnnotation(Class<?> clazz);

        public abstract Builder addAnnotation(ClassName className);

        public abstract Builder addAnnotation(AnnotationSpec annotationSpec);

        public abstract Builder addModifiers(Modifier... modifiers);

        public abstract FieldSpec build();
    }
}
