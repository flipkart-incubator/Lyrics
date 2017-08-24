package com.flipkart.lyrics.android;

import com.flipkart.lyrics.interfaces.FieldSpec;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;

/**
 * @author kushal.sharma on 22/08/17.
 */
public class TestFieldSpec extends FieldSpec {
    public TestFieldSpec(Builder builder) {
        super(builder);
    }

    public static final class Builder extends FieldSpec.Builder {
        public Builder(TypeName type, String name, Modifier... modifiers) {
            super(type, name, modifiers);
        }

        public Builder(Class<?> clazz, String name, Modifier... modifiers) {
            super(clazz, name, modifiers);
        }

        @Override
        public FieldSpec build() {
            return new TestFieldSpec(this);
        }
    }
}
