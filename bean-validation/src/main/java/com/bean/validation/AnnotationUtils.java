package com.bean.validation;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.validation.ConstraintValidator;

public class AnnotationUtils {
    
    public static ConstraintValidator initConstraintValidator(String constraintValidatorClassName,String specificationClassName,String annotationFiledName){
        
        try {
            Annotation constraintAnnotation = Class.forName(constraintValidatorClassName).getField(annotationFiledName).getAnnotations()[0];
            ConstraintValidator validator = (ConstraintValidator)Class.forName(constraintValidatorClassName).newInstance();
            validator.initialize(constraintAnnotation);
            return validator;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        };
        return null;
    }


    public static Optional<AnnotationValue> getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet() ) {
            if(entry.getKey().getSimpleName().toString().equals(key)) {
                return Optional.ofNullable(entry.getValue());
            }
        }
        return Optional.empty();
    }

    public static Optional<AnnotationMirror> getAnnotationMirror(Element element, Class<?> clazz) {
        String clazzName = clazz.getName();
        for(AnnotationMirror m : element.getAnnotationMirrors()) {
            if(m.getAnnotationType().toString().equals(clazzName)) {
                return Optional.ofNullable(m);
            }
        }
        return Optional.empty();
    }
}
