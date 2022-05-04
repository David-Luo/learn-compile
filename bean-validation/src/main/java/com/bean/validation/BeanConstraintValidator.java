package com.bean.validation;

public interface BeanConstraintValidator<T>{
    default <D extends ConstraintDefine<? extends T>> void initialize(D define) {
	}

    default boolean isValid(ValueContext<T> context){
        return isValid(context.getCurrentValue());
    }

    boolean isValid(T undercheck);
}
