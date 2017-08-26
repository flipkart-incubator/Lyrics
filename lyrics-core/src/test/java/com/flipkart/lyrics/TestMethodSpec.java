package com.flipkart.lyrics;

import com.flipkart.lyrics.java.specs.MethodSpec;

/**
 * @author kushal.sharma on 22/08/17.
 */
public class TestMethodSpec extends MethodSpec {
    protected TestMethodSpec(Builder builder) {
        super(builder);
    }

    @Override
    public Object getMethodSpec() {
        return super.getMethodSpec();
    }

    public static final class Builder extends MethodSpec.Builder {
        protected Builder(String name) {
            super(name);
        }

        public Builder() {
            super("<init>");
        }

        @Override
        public MethodSpec build() {
            return new TestMethodSpec(this);
        }
    }
}
