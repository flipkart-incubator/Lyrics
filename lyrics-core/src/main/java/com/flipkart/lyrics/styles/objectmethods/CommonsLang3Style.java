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
import com.flipkart.lyrics.specs.ClassName;
import com.flipkart.lyrics.specs.MethodSpec;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by shrey.garg on 25/01/17.
 */
public class CommonsLang3Style extends ObjectMethodsStyle {

    @Override
    public void processToString(MethodSpec.Builder toStringBuilder, List<String> nonStaticFields, MetaInfo metaInfo) {
        ClassName toStringBuilderClass = ClassName.get(ToStringBuilder.class);
        toStringBuilder.addCode("return new $T(this)\n", toStringBuilderClass);
        for (String field : nonStaticFields) {
            toStringBuilder.addCode("\t\t.append($S, $L)\n", field, field);
        }
        toStringBuilder.addCode("\t\t.toString();\n");
    }

    @Override
    public void processEqualsAndHashCode(MethodSpec.Builder equalsBuilder, MethodSpec.Builder hashCodeBuilder, List<String> nonStaticFields, MetaInfo metaInfo, boolean testSuperEquality) {
        String className = metaInfo.getClassName();

        equalsBuilder.addStatement("if (this == o) return true");
        equalsBuilder.addStatement("if (!(o instanceof $L)) return false", className);
        equalsBuilder.addCode("\n");
        equalsBuilder.addStatement("$L that = ($L) o", className, className);
        equalsBuilder.addCode("\n");

        ClassName equalsBuilderClass = ClassName.get(EqualsBuilder.class);
        equalsBuilder.addCode("return new $T()\n", equalsBuilderClass);

        if (testSuperEquality) {
            equalsBuilder.addCode("\t\t.appendSuper(super.equals(that))\n");
        }

        ClassName hashCodeBuilderClass = ClassName.get(HashCodeBuilder.class);
        hashCodeBuilder.addCode("return new $T()\n", hashCodeBuilderClass);

        if (testSuperEquality) {
            hashCodeBuilder.addCode("\t\t.appendSuper(super.hashCode())\n");
        }

        for (String field : nonStaticFields) {
            equalsBuilder.addCode("\t\t.append($L, that.$L)\n", field, field);
            hashCodeBuilder.addCode("\t\t.append($L)\n", field);
        }

        equalsBuilder.addCode("\t\t.isEquals();\n");
        hashCodeBuilder.addCode("\t\t.toHashCode();\n");
    }
}
