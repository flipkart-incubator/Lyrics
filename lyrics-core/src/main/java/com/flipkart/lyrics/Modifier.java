package com.flipkart.lyrics;

/**
 * @author kushal.sharma on 09/08/17.
 */
public enum Modifier {

    // See JLS sections 8.1.1, 8.3.1, 8.4.3, 8.8.3, and 9.1.1.
    // java.lang.reflect.Modifier includes INTERFACE, but that's a VMism.

    /** The modifier {@code public} */          PUBLIC,
    /** The modifier {@code protected} */       PROTECTED,
    /** The modifier {@code private} */         PRIVATE,
    /** The modifier {@code abstract} */        ABSTRACT,
    /**
     * The modifier {@code default}
     * @since 1.8
     */
    DEFAULT,
    /** The modifier {@code static} */          STATIC,
    /** The modifier {@code final} */           FINAL,
    /** The modifier {@code transient} */       TRANSIENT,
    /** The modifier {@code volatile} */        VOLATILE,
    /** The modifier {@code synchronized} */    SYNCHRONIZED,
    /** The modifier {@code native} */          NATIVE,
    /** The modifier {@code strictfp} */        STRICTFP;

    /**
     * Returns this modifier's name in lowercase.
     */
    public String toString() {
        return name().toLowerCase(java.util.Locale.US);
    }
}

