package com.bean.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes(
        {"com.bean.annotation.AutoPojoValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_14)
public class AutoImplementServiceProceccor  extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : elements) {
                handle(annotation, element);
            }
        }
        return false;
    }
    
    public boolean handle(Element annotation, Element element) {
        ImplementConstraintValidator p = new ImplementConstraintValidator();
        p.init(processingEnv);
        p.processing((TypeElement)element);
        
        return false;
    }
}