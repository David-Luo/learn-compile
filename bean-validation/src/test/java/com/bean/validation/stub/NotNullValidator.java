package com.bean.validation.stub;

import com.bean.validation.BeanConstraintValidator;

public class NotNullValidator implements BeanConstraintValidator<Object>{
    @Override
    public boolean isValid(Object object) {
        return object != null;
    }
    
}
