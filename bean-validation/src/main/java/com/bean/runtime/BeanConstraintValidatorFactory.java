package com.bean.runtime;

import com.bean.validation.BeanConstraintValidator;

public interface BeanConstraintValidatorFactory {
    <T extends BeanConstraintValidator<?>> T getInstance(Class<T> key);
    void releaseInstance(BeanConstraintValidator<?> instance);
}
