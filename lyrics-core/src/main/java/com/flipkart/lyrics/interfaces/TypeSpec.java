package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Modifier;
import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.model.Kind;
import com.flipkart.lyrics.interfaces.model.TypeName;

import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class TypeSpec {
    protected TypeSpec() { }

    public Object getTypeSpec() {
        return null;
    }

    public static Builder classBuilder(String name) {
        return Song.factory.createClassBuilder(Kind.CLASS, name);
    }

    public static Builder annotationBuilder(String name) {
        return Song.factory.createAnnotationBuilder(Kind.ANNOTATION, name);
    }

    public static Builder interfaceBuilder(String name) {
        return Song.factory.createInterfaceBuilder(Kind.INTERFACE, name);
    }

    public static Builder enumBuilder(String name) {
        return Song.factory.createEnumBuilder(Kind.ENUM, name);
    }

    public static Builder anonymousClassBuilder(String name) {
        return Song.factory.createAnonymousClassBuilder(name);
    }

    public static abstract class Builder {
        private Kind kind;
        private String name;

        public Builder(Kind kind, String name) {
            this.kind = kind;
            this.name = name;
        }

        public abstract Builder addField(FieldSpec fieldSpec);

        public abstract Builder addAnnotation(AnnotationSpec annotationSpec);

        public abstract Builder addAnnotation(Class<?> clazz);

        public abstract Builder addMethod(MethodSpec methodSpec);

        public abstract Builder addModifiers(Modifier... modifiers);

        public abstract Builder superclass(TypeName extendsType);

        public abstract Builder addEnumConstant(String key);

        public abstract Builder addEnumConstant(String key, TypeSpec typeSpec);

        public abstract Builder addSuperinterfaces(List<TypeName> interfaces);

        public abstract Builder addType(TypeSpec typeSpec);

        public abstract TypeSpec build();
    }
}
