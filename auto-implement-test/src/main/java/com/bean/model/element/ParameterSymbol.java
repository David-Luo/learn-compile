package com.bean.model.element;

import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import com.bean.annotation.AutoPojoValue;

import java.lang.annotation.Annotation;

@AutoPojoValue
public interface ParameterSymbol extends VariableElement{
    default TypeMirror asType() {
        return getType();
    }

    public TypeMirror getType();
    default <A extends Annotation> A getAnnotation(Class<A> arg0) {
        return null;
    }

    default <A extends Annotation> A[] getAnnotationsByType(Class<A> arg0) {
        return null;
    }

    default <R, P> R accept(ElementVisitor<R, P> visitor, P arg) {
        return visitor.visitVariable(this, arg);
    }

}