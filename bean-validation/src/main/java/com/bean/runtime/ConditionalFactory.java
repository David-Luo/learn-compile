package com.bean.runtime;

import java.lang.reflect.InvocationTargetException;

import com.bean.validation.ValidCondition;

public class ConditionalFactory {
    
    public static ValidCondition getValidCondition(Class<?> clazz) {
        try {
            return (ValidCondition)clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean isValidationCondition(Class<?> clazz) {
        return ValidCondition.class.isAssignableFrom(clazz);
    }
}
