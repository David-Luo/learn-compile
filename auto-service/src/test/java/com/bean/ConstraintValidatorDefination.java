package com.bean;

import jakarta.validation.ConstraintValidator;

public class ConstraintValidatorDefination{
    private Class<?> annotation;
    private Class<?> target;
    private Class<? extends ConstraintValidator> validatorClass;
    

    public ConstraintValidatorDefination(Class<?> annotation, Class<?> target, Class<? extends ConstraintValidator> validatorClass) {
        this.annotation = annotation;
        this.target = target;
        this.validatorClass = validatorClass;
    }

    public boolean acceptAnnotation(Class<?> annotion) {
        return this.annotation.equals(annotion);
    }

    public boolean acceptType(Class<?> type){
        return type.isAssignableFrom(target);
    }
    public Class<? extends ConstraintValidator> getValidatorClass() {
        return validatorClass;
    }

}
