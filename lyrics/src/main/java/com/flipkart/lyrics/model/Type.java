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

package com.flipkart.lyrics.model;

import com.flipkart.lyrics.creator.AnnotationCreator;
import com.flipkart.lyrics.creator.ClassCreator;
import com.flipkart.lyrics.creator.EnumCreator;
import com.flipkart.lyrics.creator.TypeCreator;

/**
 * Created by shrey.garg on 25/11/16.
 */
public enum Type {
    CLASS(new ClassCreator()),
    ENUM(new EnumCreator()),
    ANNOTATION(new AnnotationCreator());

    private TypeCreator creator;

    Type(TypeCreator creator) {
        this.creator = creator;
    }

    public TypeCreator getCreator() {
        return creator;
    }
}
