package com.bean.validation;

public interface SpecificationValidator<T> {
    void validateSpecification(ValidationContext<?> validationContext, ValueContext<T> beanContext);
}
