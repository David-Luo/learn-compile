package com.bean.model.reader;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor8;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.model.element.FieldSymbolImpl;
import com.bean.model.element.MethodSymbolImpl;

public class ElementReader extends SimpleElementVisitor8<Element,Void>{

    public ElementReader(){
        super();
    }
    
    @Override
    public Element visitType(TypeElement e, Void v) {
        List<Element> enclosedElements = e.getEnclosedElements().stream().map( ele -> ele.accept(this, v)).collect(Collectors.toList());
        ClassSymbolImpl.Builder builder= ClassSymbolImpl.builder();
        builder.enclosingElement(e.getEnclosingElement())
            .annotationMirrors(new LinkedList<>(e.getAnnotationMirrors()))
            .interfaces(new LinkedList<>(e.getInterfaces()))
            .kind(e.getKind())
            .modifiers(new HashSet<Modifier>(e.getModifiers()))
            .simpleName(e.getSimpleName())
            .superclass(e.getSuperclass())
            .typeParameters(new LinkedList<>(e.getTypeParameters()))
            .enclosedElements(enclosedElements);

        return builder.build();
    }


    @Override
    public Element visitExecutable(ExecutableElement e, Void p){
        MethodSymbolImpl.Builder builder = MethodSymbolImpl.builder();
        builder.simpleName(e.getSimpleName())
            .thrownTypes(new LinkedList<>(e.getThrownTypes()))
            .modifiers(new HashSet<Modifier>(e.getModifiers()))
            .parameters(new LinkedList<>(e.getParameters()))
            .returnType(e.getReturnType())
            .annotationMirrors(new LinkedList<>(e.getAnnotationMirrors()))
            .typeParameters(new LinkedList<>(e.getTypeParameters()))
            .kind(e.getKind());
        
        return builder.build();
    }

    @Override
    public Element visitVariable(VariableElement e, Void p) {
        FieldSymbolImpl.Builder builder = FieldSymbolImpl.builder();
        builder.annotationMirrors(new LinkedList<>(e.getAnnotationMirrors()))
            .modifiers(new HashSet<>(e.getModifiers()))
            .simpleName(e.getSimpleName())
            .kind(e.getKind())
            .constantValue(e.getConstantValue());
        return builder.build();
    }

}
    
