package com.flipkart.lyrics.implementations;

import com.flipkart.lyrics.interfaces.AnnotationSpec;
import com.flipkart.lyrics.interfaces.model.NameFormatArgs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class JavaAnnotationSpec extends AnnotationSpec {
    private com.squareup.javapoet.AnnotationSpec.Builder builder;

    public JavaAnnotationSpec(Builder builder) {
        this.builder = com.squareup.javapoet.AnnotationSpec.builder(builder.annotationClass);
        for (NameFormatArgs nameFormatArg : builder.nameFormatArgs) {
            this.builder.addMember(nameFormatArg.getName(), nameFormatArg.getFormat(), nameFormatArg.getArgs());
        }
    }

    public Object getAnnotationSpec() {
        return builder.build();
    }

    public static final class Builder extends AnnotationSpec.Builder {
        private List<NameFormatArgs> nameFormatArgs = new ArrayList<>();

        public Builder(Class annotationClass) {
            super(annotationClass);
        }

        @Override
        public AnnotationSpec.Builder addMember(String name, String format, Object... args) {
            this.nameFormatArgs.add(new NameFormatArgs(name, format, args));
            return this;
        }

        @Override
        public AnnotationSpec build() {
            return new JavaAnnotationSpec(this);
        }
    }
}
