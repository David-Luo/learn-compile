package com.bean.model.element;

import javax.validation.constraints.*;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import com.bean.annotation.AutoPojoValue;
import com.bean.annotation.Mandatory;

import java.lang.annotation.Annotation;

@AutoPojoValue
public interface FieldSymbol extends VariableElement{

    @Mandatory
    @NotNull
    public Name getSimpleName();
    
    default TypeMirror asType() {
        return getType();
    }

    
    default <A extends Annotation> A getAnnotation(Class<A> arg0) {
        return null;
    }

    default <A extends Annotation> A[] getAnnotationsByType(Class<A> arg0) {
        return null;
    }

    default <R, P> R accept(ElementVisitor<R, P> visitor, P arg) {
        return visitor.visitVariable(this, arg);
    }

    public TypeMirror getType();
}