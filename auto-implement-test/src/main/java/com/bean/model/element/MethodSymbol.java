package com.bean.model.element;

import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import com.bean.annotation.AutoPojoValue;

import java.lang.annotation.Annotation;

@AutoPojoValue
public interface MethodSymbol extends ExecutableElement, SymbolElement {

    default TypeMirror asType() {
        return SymbolElement.super.asType();
    }

    default <A extends Annotation> A getAnnotation(Class<A> arg0) {
        return SymbolElement.super.getAnnotation(arg0);
    }

    default <A extends Annotation> A[] getAnnotationsByType(Class<A> arg0) {
        return SymbolElement.super.getAnnotationsByType(arg0);
    }

    default <R, P> R accept(ElementVisitor<R, P> arg0, P arg1) {
        return SymbolElement.super.accept(arg0, arg1);
    }

    default boolean isVarArgs() {
        return false;
    }

    default boolean isDefault() {
        return false;
    }

}