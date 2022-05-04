package com.bean.validation;


public interface ConstraintDefine<T> {
    String code();
    String message();
    Class<T> getTargetType();
}
