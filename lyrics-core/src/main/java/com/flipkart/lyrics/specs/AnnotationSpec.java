package com.flipkart.lyrics.specs;

import com.flipkart.lyrics.Song;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class AnnotationSpec {
    public final TypeName type;
    public final Map<String, CodeBlock> members = new HashMap<>();

    public AnnotationSpec(Builder builder) {
        this.type = builder.type;
        this.members.putAll(builder.members);
    }

    public static Builder builder(Class<?> clazz) {
        return Song.factory.createAnnotationBuilder(ClassName.get(clazz));
    }

    public static Builder builder(ClassName className) {
        return Song.factory.createAnnotationBuilder(className);
    }

    public Object getAnnotationSpec() {
        return null;
    }

    public static abstract class Builder {
        private TypeName type;
        private final Map<String, CodeBlock> members = new HashMap<>();

        protected Builder(TypeName typeName) {
            this.type = typeName;
        }

        public AnnotationSpec.Builder addMember(String name, String format, Object... args) {
            this.members.put(name, CodeBlock.of(format, args));
            return this;
        }

        public abstract AnnotationSpec build();
    }
}
