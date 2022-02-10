package com.bean.processing;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.SourceVersion;

import com.bean.annotation.BeanInfoCollector;
import com.bean.model.element.ClassSymbol;
import com.bean.model.element.ClassSymbolImpl;
import com.bean.model.element.SimpleName;
import com.bean.model.reader.ImplementInterfaceReader;

@SupportedAnnotationTypes(
        {"com.bean.annotation.BeanInfoCollector"})
@SupportedSourceVersion(SourceVersion.RELEASE_14)
public class BeanInfoCollectorProcesser extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            return false;
        }

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BeanInfoCollector.class);
        if (elements == null||elements.isEmpty()) {
            return false;
        }
        ClassSymbol symbol = buildClassDefine((TypeElement)elements.iterator().next());
        System.out.println(symbol);
        return false;
    }
    public ClassSymbol buildClassDefine(TypeElement element) {
        Name className =new SimpleName(element.getSimpleName().toString()+"Impl");
        Set<Modifier> md = new HashSet<>();
        md.add(Modifier.PUBLIC);
        ClassSymbolImpl.Builder builder= ClassSymbolImpl.builder();
        builder.simpleName(className)
            .interfaces(List.of(element.asType()))
            .modifiers(md)
            .enclosingElement(element.getEnclosingElement())
            .enclosedElements(new LinkedList<>())
            .annotationMirrors(new LinkedList<>())
            .typeParameters(new LinkedList<>());
        
        ClassSymbol defineImpl = builder.build();
        //添加所有需要实现的方法
        ImplementInterfaceReader reader = new ImplementInterfaceReader(defineImpl, processingEnv.getElementUtils());
        reader.visitType(element, null);
        List<Element> enclosedElements = defineImpl.getEnclosedElements().stream().filter(e -> !e.getModifiers().contains(Modifier.DEFAULT)).collect(Collectors.toList());
        builder.enclosedElements(enclosedElements);

        return defineImpl;
    }



}
