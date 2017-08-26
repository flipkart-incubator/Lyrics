package com.flipkart.lyrics.java.specs;

import com.flipkart.lyrics.specs.AnnotationSpec;
import com.flipkart.lyrics.specs.ClassName;
import com.flipkart.lyrics.specs.CodeBlock;

import static com.flipkart.lyrics.java.helper.JavaHelper.getJavaClassName;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class JavaAnnotationSpec extends AnnotationSpec {
    private com.squareup.javapoet.AnnotationSpec.Builder builder;

    JavaAnnotationSpec(Builder builder) {
        super(builder);

        this.builder = com.squareup.javapoet.AnnotationSpec.builder(getJavaClassName(this.type));

        for (String name : this.members.keySet()) {
            CodeBlock codeBlock = this.members.get(name).get(0);
            Object[] newArgs = new Object[codeBlock.arguments.length];
            for (int i = 0; i < codeBlock.arguments.length; i++) {
                if (codeBlock.arguments[i] instanceof ClassName) {
                    ClassName className = (ClassName) codeBlock.arguments[i];
                    newArgs[i] = getJavaClassName(className);
                } else {
                    newArgs[i] = codeBlock.arguments[i];
                }
            }
            this.builder.addMember(name, com.squareup.javapoet.CodeBlock.of(codeBlock.format, newArgs));
        }
    }

    public Object getAnnotationSpec() {
        return builder.build();
    }

    public static final class Builder extends AnnotationSpec.Builder {
        public Builder(ClassName className) {
            super(className);
        }

        @Override
        public AnnotationSpec build() {
            return new JavaAnnotationSpec(this);
        }
    }
}
