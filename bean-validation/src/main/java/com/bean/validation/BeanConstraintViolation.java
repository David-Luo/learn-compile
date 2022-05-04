package com.bean.validation;

import com.bean.runtime.Point;

public class BeanConstraintViolation {
    private final String message;
    private final Object invalidValue;
    private final Point path;
    private final ConstraintDefine<?> constraintDefine;
    
    public BeanConstraintViolation(ConstraintDefine<?> constraintDefine, 
        String message, 
        Point path,
        Object invalidValue) {
        this.message = message;
        this.invalidValue = invalidValue;
        this.path = path;
        this.constraintDefine = constraintDefine;
    }

    public String getMessage() {
        return message;
    }
    public Object getInvalidValue() {
        return invalidValue;
    }
    public Point getPath() {
        return path;
    }
    public ConstraintDefine<?> getConstraintDefine() {
        return constraintDefine;
    }
}
