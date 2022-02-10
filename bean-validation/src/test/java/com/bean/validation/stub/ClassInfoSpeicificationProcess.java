package com.bean.validation.stub;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.validation.ConstraintValidatorContext;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.validation.ValidationSpec;

public class ClassInfoSpeicificationProcess implements ClassInfoSpeicification{

    @Override
    public void initialize(ValidationSpec constraintAnnotation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isValid(ClassSymbolImpl value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean enclosedElements(List<ExecutableElement> methods, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean modifiers(Set<Modifier> value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
