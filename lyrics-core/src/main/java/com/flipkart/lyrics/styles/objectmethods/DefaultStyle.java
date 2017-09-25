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
package com.flipkart.lyrics.styles.objectmethods;

import com.flipkart.lyrics.model.MetaInfo;
import com.flipkart.lyrics.specs.MethodSpec;

import java.util.List;

/**
 * Created by shrey.garg on 25/01/17.
 */
public class DefaultStyle extends ObjectMethodsStyle {

    @Override
    public void processToString(MethodSpec.Builder toStringBuilder, List<String> nonStaticFields, MetaInfo metaInfo) {
        toStringBuilder.addStatement("final StringBuilder sb = new StringBuilder(\"$L{\")", metaInfo.getClassName());

        if (nonStaticFields.size() > 0) {
            String firstField = nonStaticFields.get(0);
            toStringBuilder.addStatement("sb.append(\"$L=\").append($L)", firstField, firstField);
            if (nonStaticFields.size() > 1) {
                for (String field : nonStaticFields.subList(1, nonStaticFields.size())) {
                    toStringBuilder.addStatement("sb.append(\", $L=\").append($L);", field, field);
                }
            }
        }

        toStringBuilder.addStatement("sb.append('}')");
        toStringBuilder.addStatement("return sb.toString()");
    }

    @Override
    public void processEqualsAndHashCode(MethodSpec.Builder equalsBuilder, MethodSpec.Builder hashCodeBuilder, List<String> nonStaticFields, MetaInfo metaInfo, boolean testSuperEquality) {
        equalsBuilder.addComment("Currently not supported.");
        equalsBuilder.addStatement("return false");

        hashCodeBuilder.addComment("Currently not supported.");
        hashCodeBuilder.addStatement("return 0");
    }
}
