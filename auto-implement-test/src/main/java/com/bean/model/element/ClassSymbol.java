package com.bean.model.element;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import com.bean.annotation.AutoPojoValue;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AutoPojoValue
public interface ClassSymbol extends TypeElement{
    
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
        return visitor.visitType(this, arg);
    }

    default List<MethodSymbol> getMethods() {
        return getEnclosedElements().stream().filter(e -> e.getKind().equals(ElementKind.METHOD))
                .map(o -> (MethodSymbol) o).collect(Collectors.toList());
    }

    default List<FieldSymbol> getFields() {
        return getEnclosedElements().stream().filter(e -> e.getKind().isField()).map(o -> (FieldSymbol) o)
                .collect(Collectors.toList());
    }

    default Optional<FieldSymbol> getField(String name) {
        return getEnclosedElements().stream().filter(e -> e.getKind().isField())
                .filter(e -> e.getSimpleName().toString().equals(name)).map(o -> (FieldSymbol) o).findAny();
    }

    default Optional<MethodSymbol> getMethod(String name) {
        return getEnclosedElements().stream().filter(e -> e.getKind().equals(ElementKind.METHOD))
                .filter(e -> e.getSimpleName().toString().equals(name)).map(o -> (MethodSymbol) o).findAny();
    }

    @Override
    List<Element> getEnclosedElements();

    default String getPackageName(){
        PackageElement p = (PackageElement)getEnclosingElement();
        if (p != null) {
            return p.getQualifiedName().toString();
        }
        return null;
    }
}