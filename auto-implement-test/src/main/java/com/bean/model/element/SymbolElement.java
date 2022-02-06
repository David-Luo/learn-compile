package com.bean.model.element;

import java.lang.annotation.Annotation;
import java.util.Optional;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.type.TypeMirror;

public interface SymbolElement extends Element{

    default TypeMirror asType() {
        return null;
    }
    // TypeMirror getType();
    default <A extends Annotation> A getAnnotation(Class<A> arg0) {
        return null;
    }
    
    default <A extends Annotation> A[] getAnnotationsByType(Class<A> arg0) {
        return null;
    }
    
    default <R, P> R accept(ElementVisitor<R, P> arg0, P arg1) {
        return null;
    }

    default boolean remove(Element child){
       return getEnclosedElements().remove(child);
    }

    default Optional<? extends Element> getBySimpleName(String name){
        return getEnclosedElements().stream().filter(e -> e.getSimpleName().toString().equals(name)).findAny();
    }
}
