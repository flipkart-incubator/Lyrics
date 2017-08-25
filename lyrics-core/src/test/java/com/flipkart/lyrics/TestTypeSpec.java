package com.flipkart.lyrics;

import com.flipkart.lyrics.specs.Kind;
import com.flipkart.lyrics.specs.TypeSpec;

/**
 * @author kushal.sharma on 22/08/17.
 */
public class TestTypeSpec extends TypeSpec {
    public TestTypeSpec(Builder builder) {
        super(builder);
    }

    public static final class Builder extends TypeSpec.Builder {
        public Builder(Kind kind, String name) {
            super(kind, name);
        }

        public Builder(Kind kind, String typeArgumentsFormat, Object... args) {
            super(kind, typeArgumentsFormat, args);
        }

        @Override
        public TypeSpec build() {
            return new TestTypeSpec(this);
        }
    }
}
