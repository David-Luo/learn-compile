package com.bean.validation;

public interface ValidCondition<T> {
 boolean test(T containerBean);

    default boolean test(ValueContext<T> context){
        return test(context.getCurrentValue());
    }

    public static class TrueCondition<T> implements ValidCondition<T>{
        @Override
        public boolean test(T conditionBean) {
            return true;
        }
    }

    public static class FalseCondition<T> implements ValidCondition<T>{
        @Override
        public boolean test(T conditionBean) {
            return false;
        }
    }
}
