package com.bean;

import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;

import com.bean.annotation.AutoPojoValue;
import com.bean.annotation.Mandatory;

import java.lang.annotation.Annotation;

@AutoPojoValue
public interface PackageSymbol extends PackageElement{
    @Override
    // @Mandatory
    public Name getQualifiedName();
    default TypeMirror asType() {
        return null;
    }

    default <A extends Annotation> A getAnnotation(Class<A> arg0) {
        return null;
    }

    default <A extends Annotation> A[] getAnnotationsByType(Class<A> arg0) {
        return null;
    }

    default <R, P> R accept(ElementVisitor<R, P> arg0, P arg1) {
        return null;
    }
    default boolean isUnnamed() {
        return false;
    }
}