package com.bean.validation;

import java.util.Set;
import java.util.TreeSet;

import com.bean.runtime.BeanConstraintValidatorFactory;

public class ValidationContext<R> {
    private R rootBean;
    private Set<BeanConstraintViolation> failingConstraints;
    private BeanConstraintValidatorFactory validatorFactory;
    private Set<MetaConstraint<R,?>> processed = new TreeSet<>();

    public ValidationContext(R rootBean) {
        this.rootBean = rootBean;
    }
    public void markProcessed(MetaConstraint<R,?> metaConstraint){
        processed.add(metaConstraint);
    }
	public boolean hasProcessed(MetaConstraint<R,?> metaConstraint){
        return processed.contains(metaConstraint);
    }

    public R getRootBean() {
        return rootBean;
    }
    public Set<BeanConstraintViolation> getFailingConstraints() {
        return failingConstraints;
    }
    public void addFailingConstraint(BeanConstraintViolation failing) {
        failingConstraints.add(failing);
    }
    public BeanConstraintValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

}
