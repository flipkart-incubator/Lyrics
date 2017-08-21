package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.model.NameFormatArgs;
import com.flipkart.lyrics.interfaces.typenames.ClassName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class AnnotationSpec {
    public final Class<?> clazz;
    public final ClassName className;
    public final List<NameFormatArgs> members = new ArrayList<>();

    public AnnotationSpec(Builder builder) {
        this.clazz = builder.clazz;
        this.className = builder.className;
        this.members.addAll(builder.members);
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
        private Class<?> clazz;
        private ClassName className;
        private final List<NameFormatArgs> members = new ArrayList<>();

        protected Builder(Class<?> clazz) {
            this.clazz = clazz;
        }

        protected Builder(ClassName className) {
            this.className = className;
        }

        public AnnotationSpec.Builder addMember(String name, String format, Object... args) {
            this.members.add(new NameFormatArgs(name, format, args));
            return this;
        }

        public abstract AnnotationSpec build();
    }
}
