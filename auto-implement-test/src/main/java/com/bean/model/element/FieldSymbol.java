package com.bean.model.element;

import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import com.bean.annotation.AutoPojoValue;
import com.bean.annotation.Mandatory;

import java.lang.annotation.Annotation;

@AutoPojoValue
public interface FieldSymbol extends VariableElement , SymbolElement {

    @Mandatory
    public Name getSimpleName();
    
    default TypeMirror asType() {
        return getType();
    }

    
    default <A extends Annotation> A getAnnotation(Class<A> arg0) {
        return SymbolElement.super.getAnnotation(arg0);
    }

    default <A extends Annotation> A[] getAnnotationsByType(Class<A> arg0) {
        return SymbolElement.super.getAnnotationsByType(arg0);
    }

    default <R, P> R accept(ElementVisitor<R, P> arg0, P arg1) {
        return SymbolElement.super.accept(arg0,arg1);
    }

    public TypeMirror getType();
}