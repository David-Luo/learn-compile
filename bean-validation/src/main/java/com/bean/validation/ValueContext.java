package com.bean.validation;

import com.bean.runtime.Point;

public class ValueContext<V> {
    /**
     * The bean which validator started
     */
    private final Object root;
    /**
	 * The current bean which gets validated. This is the bean hosting the constraints which get validated.
	 */
	private final Object currentBean;

	/**
	 * The current property path we are validating.
	 */
	private Point path;

	/**
	 * The current group we are validating.
	 */
	private Class<?> currentGroup;

	/**
	 * The value which gets currently evaluated.
	 */
	private V currentValue;

    public ValueContext(Object root, Object currentBean, Point path, Class<?> currentGroup, V currentValue) {
        this.root = root;
        this.currentBean = currentBean;
        this.path = path;
        this.currentGroup = currentGroup;
        this.currentValue = currentValue;
    }

    public Object getCurrentBean() {
        return currentBean;
    }

    public Point getPath() {
        return path;
    }

    // public void setPath(Path propertyPath) {
    //     this.propertyPath = propertyPath;
    // }

    public Class<?> getCurrentGroup() {
        return currentGroup;
    }

    // public void setCurrentGroup(Class<?> currentGroup) {
    //     this.currentGroup = currentGroup;
    // }

    public V getCurrentValue() {
        return currentValue;
    }

    public Object getRoot(){
        return root;
    }
    // public void setCurrentValue(V currentValue) {
    //     this.currentValue = currentValue;
    // }

}
