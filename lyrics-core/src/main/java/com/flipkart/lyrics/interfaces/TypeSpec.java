package com.flipkart.lyrics.interfaces;

import com.flipkart.lyrics.Song;
import com.flipkart.lyrics.interfaces.typenames.Kind;
import com.flipkart.lyrics.interfaces.typenames.Modifier;
import com.flipkart.lyrics.interfaces.typenames.TypeName;
import com.flipkart.lyrics.interfaces.typenames.TypeVariableName;

import java.io.File;
import java.util.List;

/**
 * @author kushal.sharma on 10/08/17.
 */
public class TypeSpec {
    protected TypeSpec() {
    }

    public static Builder classBuilder(String name) {
        return Song.factory.createClassBuilder(name);
    }

    public static Builder annotationBuilder(String name) {
        return Song.factory.createAnnotationBuilder(name);
    }

    public static Builder interfaceBuilder(String name) {
        return Song.factory.createInterfaceBuilder(name);
    }

    public static Builder enumBuilder(String name) {
        return Song.factory.createEnumBuilder(name);
    }

    public static Builder anonymousClassBuilder(String typeArgumentsFormat, Object... args) {
        return Song.factory.createAnonymousClassBuilder(typeArgumentsFormat, args);
    }

    public Object getTypeSpec() {
        return null;
    }

    public void writeToFile(String fullPackage, File targetFolder) {
        // do nothing
    }

    public static abstract class Builder {
        public Kind kind;
        public String name;

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

        public abstract Builder addTypeVariable(TypeVariableName typeVariableName);

        public abstract TypeSpec build();
    }
}
