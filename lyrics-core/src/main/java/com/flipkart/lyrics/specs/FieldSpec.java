/*
 * Copyright 2017 Flipkart Internet, pvt ltd.
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

import com.flipkart.lyrics.helper.Util;

import java.util.*;

import static com.flipkart.lyrics.helper.Util.*;

public class FieldSpec {
    public final TypeName type;
    public final String name;
    public final String namedAs;
    public final CodeBlock doc;
    public final List<AnnotationSpec> annotations;
    public final Set<Modifier> modifiers;
    public final CodeBlock initializer;
    public final boolean required;

    private FieldSpec(Builder builder) {
        this.type = checkNotNull(builder.type, "type == null");
        this.name = checkNotNull(builder.name, "name == null");
        this.namedAs = builder.namedAs;
        this.doc = builder.doc.build();
        this.annotations = Util.immutableList(builder.annotations);
        this.modifiers = Util.immutableSet(builder.modifiers);
        this.initializer = (builder.initializer == null)
                ? CodeBlock.builder().build()
                : builder.initializer;
        this.required = builder.required;
    }

    public static Builder builder(TypeName typeName, String name, Modifier... modifiers) {
        return new Builder(typeName, name, modifiers);
    }

    public static Builder builder(Class<?> clazz, String name, Modifier... modifiers) {
        return new Builder(clazz, name, modifiers);
    }

    public boolean hasModifier(Modifier modifier) {
        return modifiers.contains(modifier);
    }

    public Builder toBuilder() {
        Builder builder = new Builder(type, name);
        builder.namedAs = namedAs;
        builder.doc.add(doc);
        builder.annotations.addAll(annotations);
        builder.modifiers.addAll(modifiers);
        builder.initializer = initializer.formats.isEmpty() ? null : initializer;
        builder.required = required;
        return builder;
    }

    public static final class Builder {
        private final TypeName type;
        private final String name;
        private String namedAs;
        private final CodeBlock.Builder doc = CodeBlock.builder();
        private final List<AnnotationSpec> annotations = new ArrayList<>();
        private final Set<Modifier> modifiers = new HashSet<>();
        private CodeBlock initializer = null;
        private boolean required;

        protected Builder(TypeName type, String name, Modifier... modifiers) {
            this.type = type;
            this.name = name;
            this.modifiers.addAll(Arrays.asList(modifiers));
        }

        protected Builder(Class<?> clazz, String name, Modifier... modifiers) {
            this.type = TypeName.get(clazz);
            this.name = name;
            this.modifiers.addAll(Arrays.asList(modifiers));
        }

        public Builder namedAs(String namedAs) {
            this.namedAs = namedAs;
            return this;
        }

        public Builder required(boolean required) {
            this.required = required;
            return this;
        }

        public Builder addDoc(String format, Object... args) {
            doc.add(format, args);
            return this;
        }

        public Builder addDoc(CodeBlock block) {
            doc.add(block);
            return this;
        }

        public Builder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
            checkArgument(annotationSpecs != null, "annotationSpecs == null");
            for (AnnotationSpec annotationSpec : annotationSpecs) {
                this.annotations.add(annotationSpec);
            }
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.annotations.add(annotationSpec);
            return this;
        }

        public Builder addAnnotation(ClassName annotation) {
            this.annotations.add(AnnotationSpec.builder(annotation).build());
            return this;
        }

        public Builder addAnnotation(Class<?> annotation) {
            return addAnnotation(ClassName.get(annotation));
        }

        public Builder addModifiers(Modifier... modifiers) {
            Collections.addAll(this.modifiers, modifiers);
            return this;
        }

        public Builder initializer(String format, Object... args) {
            return initializer(CodeBlock.of(format, args));
        }

        public Builder initializer(CodeBlock codeBlock) {
            checkState(this.initializer == null, "initializer was already set");
            this.initializer = checkNotNull(codeBlock, "codeBlock == null");
            return this;
        }

        public FieldSpec build() {
            return new FieldSpec(this);
        }
    }
}
