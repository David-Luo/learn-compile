package com.bean.runtime;

import java.util.Map;

import com.bean.validation.BeanConstraintValidator;
import com.bean.validation.ConstraintDefine;

public class ValidatorFactory {
    private Map<String, BeanConstraintValidator<?>> validatorMap;

    public <T> T getBean(Class<T> clazz){
        return null;
    }
}
