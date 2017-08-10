package com.flipkart.lyrics.interfaces.model;

import com.flipkart.lyrics.helper.Util;
import com.flipkart.lyrics.interfaces.AnnotationSpec;

import javax.lang.model.SourceVersion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public final class ClassName extends TypeName implements Comparable<ClassName> {
    public static final ClassName OBJECT = get(Object.class);
    final List<String> names;
    final String canonicalName;

    private ClassName(List<String> names) {
        this(names, new ArrayList<>());
    }

    private ClassName(List<String> names, List<AnnotationSpec> annotations) {
        super(annotations);

        for(int i = 1; i < names.size(); ++i) {
            Util.checkArgument(SourceVersion.isName(names.get(i)), "part '%s' is keyword", names.get(i));
        }

        this.names = names;
        this.canonicalName = names.get(0).isEmpty() ? Util.join(".", names.subList(1, names.size())) : Util.join(".", names);
    }

    public static ClassName get(String packageName, String simpleName, String... simpleNames) {
        List<String> result = new ArrayList<>();
        result.add(packageName);
        result.add(simpleName);
        Collections.addAll(result, simpleNames);
        return new ClassName(result);
    }

    @Override
    public int compareTo(ClassName o) {
        return this.canonicalName.compareTo(o.canonicalName);
    }

    public static ClassName get(Class<?> clazz) {
        Util.checkNotNull(clazz, "clazz == null");
        Util.checkArgument(!clazz.isPrimitive(), "primitive types cannot be represented as a ClassName");
        Util.checkArgument(!Void.TYPE.equals(clazz), "'void' type cannot be represented as a ClassName");
        Util.checkArgument(!clazz.isArray(), "array types cannot be represented as a ClassName");
        ArrayList names = new ArrayList();

        while(true) {
            names.add(clazz.getSimpleName());
            Class<?> enclosing = clazz.getEnclosingClass();
            if (enclosing == null) {
                int lastDot = clazz.getName().lastIndexOf(46);
                if (lastDot != -1) {
                    names.add(clazz.getName().substring(0, lastDot));
                }

                Collections.reverse(names);
                return new ClassName(names);
            }

            clazz = enclosing;
        }
    }
}
