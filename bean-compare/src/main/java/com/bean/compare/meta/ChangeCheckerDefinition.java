package com.bean.compare.meta;

public class ChangeCheckerDefinition implements Comparable<ChangeCheckerDefinition>{
    private Class<?> targetClass;
    private Class<?> checker;
    private int order;

    public ChangeCheckerDefinition(Class<?> targetClass, Class<?> checker, int order) {
        this.targetClass = targetClass;
        this.checker = checker;
        this.order = order;
    }

    public boolean  acceptType(Class<?> type) {
        return targetClass.isAssignableFrom(type);
    }

    public Class<?> getCheckerClass() {
        return checker;
    }

    @Override
    public int compareTo(ChangeCheckerDefinition o) {
        return order - o.order;
    }
}
