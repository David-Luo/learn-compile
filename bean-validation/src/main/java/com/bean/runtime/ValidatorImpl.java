package com.bean.runtime;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bean.validation.SpecificationValidator;
import com.bean.validation.ValidationContext;
import com.bean.validation.ValueContext;


import jakarta.validation.ConstraintViolation;

public class ValidatorImpl {
    
    public <T> Set<ConstraintViolation<T>> validateSpecification(Class<? extends SpecificationValidator> specification, T value) {
        
        ValidationContext validationContext = new ValidationContext(value);
        ValueContext<T> valueContext = new ValueContext(value, value, PathPoint.createRoot(), null, value);
        // return validateSpecification(validationContext,specification, valueContext);
        return null;
    }


}
