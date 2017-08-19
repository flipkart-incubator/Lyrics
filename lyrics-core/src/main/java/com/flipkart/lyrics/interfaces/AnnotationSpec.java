package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.typenames.ClassName;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class AnnotationSpec {
    protected AnnotationSpec() {
    }

    public static Builder builder(Class<?> clazz) {
        return Song.factory.createAnnotationBuilder(clazz);
    }

    public static Builder builder(ClassName className) {
        return Song.factory.createAnnotationBuilder(className);
    }

    public Object getAnnotationSpec() {
        return null;
    }

    public static abstract class Builder {
        public Class<?> clazz;
        public ClassName className;

        protected Builder(Class<?> clazz) {
            this.clazz = clazz;
        }

        protected Builder(ClassName className) {
            this.className = className;
        }

        public abstract Builder addMember(String name, String format, Object... args);

        public abstract AnnotationSpec build();
    }
}
