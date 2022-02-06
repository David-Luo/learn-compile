package com.bean.model.reader;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor8;

import com.bean.model.element.ClassSymbol;

public class ImplementInterfaceReader extends SimpleElementVisitor8<Element,Void> {
    private ClassSymbol classSymbolImpl;
    private Elements elementUtils;
    private ElementReader reader = new ElementReader();

    public ImplementInterfaceReader(ClassSymbol classSymbolImpl, Elements elementUtils) {
        this.classSymbolImpl = classSymbolImpl;
        this.elementUtils = elementUtils;
    }

    @Override
    public Element visitType(TypeElement e, Void v) {
        //将当前接口所有方法添加到实现类中
        List<Element> enclosedElements = classSymbolImpl.getEnclosedElements();
        for (Element element : e.getEnclosedElements()) {
           Element method = element.accept(this, v);
           if(method != null){
            enclosedElements.add(method);
           }
        }
        
        //递归所有上级节点,添加方法
        for (TypeMirror type : e.getInterfaces()) {
            TypeElement element = elementUtils.getTypeElement(type.toString());
            this.visitType(element,v);
        }
        return classSymbolImpl;
    }

    @Override
    public Element visitExecutable(ExecutableElement e, Void p) {
        if (classSymbolImpl.getMethod(e.getSimpleName().toString()).isPresent()) {
            return null;
        }
        if (e.getModifiers().contains(Modifier.STATIC)) {
            return null;
        }
        // if (e.isDefault()) {
        //     return null;
        // }
        Element method = reader.visitExecutable(e, p);
        removeAbstractModifier(method.getModifiers());
        return method;
    }

    private void removeAbstractModifier(Set<Modifier> modifiers) {
        modifiers.remove(Modifier.ABSTRACT);
    }
    @Override
    public Element visitVariable(VariableElement e, Void p) {
        return null;
    }
}
