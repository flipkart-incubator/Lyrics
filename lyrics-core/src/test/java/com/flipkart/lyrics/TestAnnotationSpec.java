package com.flipkart.lyrics;

import com.flipkart.lyrics.interfaces.AnnotationSpec;
import com.flipkart.lyrics.interfaces.typenames.ClassName;

/**
 * @author kushal.sharma on 23/08/17.
 */
public class TestAnnotationSpec extends AnnotationSpec {
    public TestAnnotationSpec(Builder builder) {
        super(builder);
    }

    public static final class Builder extends AnnotationSpec.Builder {
        protected Builder(Class<?> clazz) {
            super(clazz);
        }

        protected Builder(ClassName className) {
            super(className);
        }

        @Override
        public AnnotationSpec build() {
            return new TestAnnotationSpec(this);
        }
    }
}
