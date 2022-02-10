package com.bean.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidatorContext;

public class ValidationSpecProcess implements IValidationSpecProcess<Object>{
    private Set<IValidationSpecProcess> validationSpecGroup=new HashSet<>();

    public ValidationSpecProcess(){
        System.out.println("x");
    }

    public boolean isValid(Object value, ConstraintValidatorContext context){
        boolean result = true;
        boolean temp ;
        for (IValidationSpecProcess iValidationSpec : validationSpecGroup) {
            temp = iValidationSpec.isValid(value, context);
            result = temp && result;
        }
        
       return result;
    }

    @Override
    public void initialize(ValidationSpec constraintAnnotation) {
        for ( Class<? extends IValidationSpecProcess> spec:constraintAnnotation.value()) {
            IValidationSpecProcess temp = ServiceLocator.get(spec.getCanonicalName());
            if(temp != null){
                temp.initialize(constraintAnnotation);
                validationSpecGroup.add(temp);
            }
        }
    }
}
