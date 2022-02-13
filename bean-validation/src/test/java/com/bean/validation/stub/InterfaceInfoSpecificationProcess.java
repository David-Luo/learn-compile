package com.bean.validation.stub;

import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.validation.AnnotationUtils;
import com.bean.validation.ValidationSpec;

public class InterfaceInfoSpecificationProcess implements InterfaceInfoSpecification {

    @SubsetOf({ Modifier.PRIVATE })
    private Object _modifiersSubset;

    private static ConstraintValidator modifiersSubset = AnnotationUtils.initConstraintValidator(
            "com.bean.validation.stub.ModifierValidation", "com.bean.validation.stub.InterfaceInfoSpecificationProcess",
            "_modifiersSubset");

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
        boolean result = true;
        boolean temp = true;
        temp = modifiersSubset.isValid(value, context);
        result = result && temp;
        return result;
    }

}
