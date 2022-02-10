package com.bean.validation.stub;

import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.validation.ValidationSpec;

public class InterfaceInfoSpecificationProcess implements InterfaceInfoSpecification{

    @Override
    public void initialize(ValidationSpec constraintAnnotation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isValid(ClassSymbolImpl value, ConstraintValidatorContext context) {
        boolean result = modifiers(value.getModifiers(), context);
        return result;
    }

    @Override
    public boolean modifiers(Set<Modifier> value, ConstraintValidatorContext context) {
        Class<InterfaceInfoSpecification> clazz = InterfaceInfoSpecification.class;
        SubsetOf constraintAnnotation=null;
        try {
            ConstraintValidator validator = ModifierValidation.class.newInstance();
            validator.initialize(constraintAnnotation);
            return validator.isValid(value, context);
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
        
        return true;
    }
    
}
