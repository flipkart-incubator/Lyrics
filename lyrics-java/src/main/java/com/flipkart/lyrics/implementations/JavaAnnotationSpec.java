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

    JavaAnnotationSpec(Builder builder) {
        super(builder);

        if (this.clazz != null) this.builder = com.squareup.javapoet.AnnotationSpec.builder(this.clazz);
        if (this.className != null) this.builder = com.squareup.javapoet.AnnotationSpec.builder(getJavaClassName(this.className));

        for (NameFormatArgs member : this.members) {
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
        public Builder(Class clazz) {
            super(clazz);
        }

        public Builder(ClassName className) {
            super(className);
        }

        @Override
        public AnnotationSpec build() {
            return new JavaAnnotationSpec(this);
        }
    }
}
