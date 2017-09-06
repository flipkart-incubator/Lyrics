/*
 * Copyright 2016 Flipkart Internet, pvt ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.lyrics.specs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationSpec {
    public final TypeName type;
    public final Map<String, List<CodeBlock>> members = new HashMap<>();

    private AnnotationSpec(Builder builder) {
        this.type = builder.type;
        this.members.putAll(builder.members);
    }

    public static Builder builder(Class<?> clazz) {
        return new AnnotationSpec.Builder(ClassName.get(clazz));
    }

    public static Builder builder(ClassName className) {
        return new AnnotationSpec.Builder(className);
    }

    public Builder toBuilder() {
        Builder builder = new Builder(type);
        for (Map.Entry<String, List<CodeBlock>> entry : members.entrySet()) {
            builder.members.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return builder;
    }

    public static final class Builder {
        private final Map<String, List<CodeBlock>> members = new HashMap<>();
        private TypeName type;

        protected Builder(TypeName typeName) {
            this.type = typeName;
        }

        public AnnotationSpec.Builder addMember(String name, String format, Object... args) {
            List<CodeBlock> values = this.members.computeIfAbsent(name, k -> new ArrayList<>());
            values.add(CodeBlock.of(format, args));
            return this;
        }

        public AnnotationSpec build() {
            return new AnnotationSpec(this);
        }
    }
}
