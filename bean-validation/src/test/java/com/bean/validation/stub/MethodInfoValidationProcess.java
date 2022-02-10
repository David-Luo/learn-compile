package com.bean.validation.stub;

import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.validation.ConstraintValidatorContext;
import com.bean.model.element.MethodSymbolImpl;
import com.bean.validation.ValidationSpec;

public class MethodInfoValidationProcess implements MethodInfoValidationSpec{

    @Override
    public void initialize(ValidationSpec constraintAnnotation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isValid(MethodSymbolImpl value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean modifiers(Set<Modifier> value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        return false;
    }

}
