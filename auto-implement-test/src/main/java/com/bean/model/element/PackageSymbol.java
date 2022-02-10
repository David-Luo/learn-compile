package com.bean.model.element;

import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;

import com.bean.annotation.AutoPojoValue;

import java.lang.annotation.Annotation;

@AutoPojoValue
public interface PackageSymbol extends PackageElement{
    default TypeMirror asType() {
        return null;
    }

    default <A extends Annotation> A getAnnotation(Class<A> arg0) {
        return null;
    }

    default <A extends Annotation> A[] getAnnotationsByType(Class<A> arg0) {
        return null;
    }

    default <R, P> R accept(ElementVisitor<R, P> visitor, P arg) {
        return visitor.visitPackage(this, arg);
    }
    default boolean isUnnamed() {
        return false;
    }
}