package com.flipkart.lyrics.interfaces.typenames;

import com.flipkart.lyrics.helper.Util;
import com.flipkart.lyrics.interfaces.AnnotationSpec;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.flipkart.lyrics.helper.Util.checkArgument;

/**
 * @author kushal.sharma on 17/08/17.
 */
public final class WildcardTypeName extends TypeName {
    public final List<TypeName> upperBounds;
    public final List<TypeName> lowerBounds;

    private WildcardTypeName(List<TypeName> upperBounds, List<TypeName> lowerBounds) {
        this(upperBounds, lowerBounds, new ArrayList<AnnotationSpec>());
    }

    private WildcardTypeName(List<TypeName> upperBounds, List<TypeName> lowerBounds,
                             List<AnnotationSpec> annotations) {
        super(annotations);
        this.upperBounds = Util.immutableList(upperBounds);
        this.lowerBounds = Util.immutableList(lowerBounds);

        checkArgument(this.upperBounds.size() == 1, "unexpected extends bounds: %s", upperBounds);
        for (TypeName upperBound : this.upperBounds) {
            checkArgument(!upperBound.isPrimitive() && upperBound != VOID,
                    "invalid upper bound: %s", upperBound);
        }
        for (TypeName lowerBound : this.lowerBounds) {
            checkArgument(!lowerBound.isPrimitive() && lowerBound != VOID,
                    "invalid lower bound: %s", lowerBound);
        }
    }

    /**
     * Returns a type that represents an unknown type that extends {@code bound}. For example, if
     * {@code bound} is {@code CharSequence.class}, this returns {@code ? extends CharSequence}. If
     * {@code bound} is {@code Object.class}, this returns {@code ?}, which is shorthand for {@code
     * ? extends Object}.
     */
    public static WildcardTypeName subtypeOf(TypeName upperBound) {
        return new WildcardTypeName(Arrays.asList(upperBound), Collections.emptyList());
    }

    public static WildcardTypeName subtypeOf(Type upperBound) {
        return subtypeOf(TypeName.get(upperBound));
    }

    /**
     * Returns a type that represents an unknown supertype of {@code bound}. For example, if {@code
     * bound} is {@code String.class}, this returns {@code ? super String}.
     */
    public static WildcardTypeName supertypeOf(TypeName lowerBound) {
        return new WildcardTypeName(Arrays.asList(OBJECT), Arrays.asList(lowerBound));
    }

    public static WildcardTypeName supertypeOf(Type lowerBound) {
        return supertypeOf(TypeName.get(lowerBound));
    }

    @Override
    public WildcardTypeName annotated(List<AnnotationSpec> annotations) {
        return new WildcardTypeName(upperBounds, lowerBounds, concatAnnotations(annotations));
    }

    @Override
    public TypeName withoutAnnotations() {
        return new WildcardTypeName(upperBounds, lowerBounds);
    }
}
