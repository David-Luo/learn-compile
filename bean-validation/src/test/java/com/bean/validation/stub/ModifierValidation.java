package com.bean.validation.stub;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class ModifierValidation implements ConstraintValidator<SubsetOf, Set<Modifier>> {
    private Set<Modifier> arrange = new HashSet<>();

    @Override
    public void initialize(SubsetOf constraintAnnotation) {
       this.arrange =  Stream.of(constraintAnnotation.value()).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Set<Modifier> value, ConstraintValidatorContext context) {
        for (Modifier modifier : value) {
            if(!arrange.contains(modifier))
            return false;
        }
        return true;
    }
    
}
