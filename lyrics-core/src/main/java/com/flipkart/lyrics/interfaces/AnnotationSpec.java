package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class AnnotationSpec {
    protected AnnotationSpec() { }

    public Object getAnnotationSpec() {
        return null;
    }

    public static Builder builder(Class<?> clazz) {
        return Song.factory.createAnnotation(clazz);
    }

    public static abstract class Builder {
        public Class<?> annotationClass;

        protected Builder(Class<?> annotationClass) {
            this.annotationClass = annotationClass;
        }

        public abstract Builder addMember(String name, String format, Object... args);

        public abstract AnnotationSpec build();
    }
}
