package com.bean.validation.stub;

import com.bean.validation.BeanConstraintValidator;
import com.google.auto.service.AutoService;


@AutoService(BeanConstraintValidator.class)
public class NotNullValidator implements BeanConstraintValidator<Object>{
    @Override
    public boolean isValid(Object object) {
        return object != null;
    }
    
}
