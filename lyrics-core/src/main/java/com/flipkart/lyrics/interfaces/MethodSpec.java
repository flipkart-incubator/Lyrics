package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.typenames.ClassName;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

import java.lang.reflect.Type;

/**
 * @author kushal.sharma on 09/08/17.
 */
public class MethodSpec {
    protected MethodSpec() {
    }

    public static Builder methodBuilder(String name) {
        return Song.factory.createMethodBuilder(name);
    }

    public static Builder constructorBuilder() {
        return Song.factory.createConstructorBuilder();
    }

    public Object getMethodSpec() {
        return null;
    }

    public static abstract class Builder {
        public String name;

        protected Builder(String name) {
            this.name = name;
        }

        public abstract Builder addModifiers(Modifier... modifiers);

        public abstract Builder returns(TypeName typeName);

        public abstract Builder returns(Type type);

        public abstract Builder addStatement(String format, Object... args);

        public abstract Builder addParameter(ParameterSpec parameterSpec);

        public abstract Builder addAnnotation(Class<?> clazz);

        public abstract Builder addAnnotation(ClassName className);

        public abstract Builder addCode(String format, Object... args);

        public abstract Builder addComment(String format, Object... args);

        public abstract Builder addParameter(TypeName typeName, String args, Modifier... modifiers);

        public abstract Builder addParameter(Type type, String name, Modifier... modifiers);

        public abstract Builder addParameter(Type type, String name);

        public abstract Builder defaultValue(String format, Object... args);

        public abstract MethodSpec build();
    }
}