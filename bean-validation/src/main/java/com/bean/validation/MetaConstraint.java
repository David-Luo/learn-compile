package com.bean.validation;

public interface MetaConstraint<R,T> {
    ValidationContext<R> getValidationContext();
    ConstraintDefine<T> getConstraintDefine();
    ValueContext<T> getValueContext();

    void process();
    
}
