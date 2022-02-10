package com.bean.validation.stub;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.validation.ConstraintValidatorContext;

import com.bean.validation.IValidationSpecProcess;


public interface ClassElementsValidateSpec extends IValidationSpecProcess<List<Element>>{
    default boolean isValid(List<Element> value, ConstraintValidatorContext context){
        if(value==null || value.isEmpty())
            return true;
        
        List<ExecutableElement> methods = value.stream()
            .filter(e -> e instanceof ExecutableElement)
            .map(e -> (ExecutableElement)e)
            .collect(Collectors.toList());
        
    
        return true;
    }

}
