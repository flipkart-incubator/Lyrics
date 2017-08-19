package com.flipkart.lyrics.implementations;

import com.flipkart.lyrics.interfaces.AnnotationSpec;
import com.flipkart.lyrics.interfaces.model.NameFormatArgs;
import com.flipkart.lyrics.interfaces.typenames.ClassName;

import java.util.ArrayList;
import java.util.List;

import static com.flipkart.lyrics.helper.JavaHelper.getJavaClassName;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class JavaAnnotationSpec extends AnnotationSpec {
    private com.squareup.javapoet.AnnotationSpec.Builder builder;

    public JavaAnnotationSpec(Builder builder) {
        if (builder.clazz != null) this.builder = com.squareup.javapoet.AnnotationSpec.builder(builder.clazz);
        if (builder.className != null)
            this.builder = com.squareup.javapoet.AnnotationSpec.builder(getJavaClassName(builder.className));
        for (NameFormatArgs member : builder.members) {
            Object[] newArgs = new Object[member.getArgs().length];
            for (int i = 0; i< member.getArgs().length; i++){
                if (member.getArgs()[i] instanceof ClassName) {
                    ClassName className = (ClassName) member.getArgs()[i];
                    newArgs[i] = getJavaClassName(className);
                } else {
                    newArgs[i] = member.getArgs()[i];
                }
            }
            this.builder.addMember(member.getName(), member.getFormat(), newArgs);
        }
    }

    public Object getAnnotationSpec() {
        return builder.build();
    }

    public static final class Builder extends AnnotationSpec.Builder {
        private List<NameFormatArgs> members = new ArrayList<>();

        public Builder(Class clazz) {
            super(clazz);
        }

        public Builder(ClassName className) {
            super(className);
        }

        @Override
        public AnnotationSpec.Builder addMember(String name, String format, Object... args) {
            this.members.add(new NameFormatArgs(name, format, args));
            return this;
        }

        @Override
        public AnnotationSpec build() {
            return new JavaAnnotationSpec(this);
        }
    }
}
