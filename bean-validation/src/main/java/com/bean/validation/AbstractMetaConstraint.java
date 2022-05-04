package com.bean.validation;

import java.lang.annotation.Annotation;

import jakarta.validation.ConstraintValidator;

public abstract class AbstractMetaConstraint<R,T> implements MetaConstraint<R,T> {

    private final ValidationContext<R> validationContext;
    private final ConstraintDefine<T> constraintDefine;
    private final ValueContext<T> valueContext;
    private final String cache;

    public AbstractMetaConstraint(ValidationContext<R> validationContext, ConstraintDefine<T> constraintDefine, ValueContext<T> valueContext) {
        this.validationContext = validationContext;
        this.constraintDefine = constraintDefine;
        this.valueContext = valueContext;
        this.cache = getConstraintDefine().toString()+getValueContext().getPath().toString();
    }

    @Override
    public String toString(){
        return cache;
    }
    @Override
    public int hashCode(){
        return cache.hashCode();
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof AbstractMetaConstraint){
            return cache.equals(((AbstractMetaConstraint<R,T>)obj).cache);
        }
        return false;
    }
    @Override
    public ValidationContext<R> getValidationContext() {
        return (ValidationContext<R>) validationContext;
    }
    @Override
    public ConstraintDefine<T> getConstraintDefine() {
        return constraintDefine;
    }
    @Override
    public ValueContext<T> getValueContext() {
        return valueContext;
    }

    public static class SpecificationMetaConstraint<R,T> extends AbstractMetaConstraint<R,T>{
        private final SpecificationValidator<T> validator;
        public SpecificationMetaConstraint(ValidationContext<R> validationContext, ConstraintDefine<T> constraintDefine,
                ValueContext<T> valueContext,SpecificationValidator<T> validator) {
            super(validationContext, constraintDefine, valueContext);
         this.validator = validator;
        }

        @Override
        public void process() {
            if (getValidationContext().hasProcessed(this)) {
                //TODO log this
                return;
            }
            validator.validateSpecification(getValidationContext(), getValueContext());
            getValidationContext().markProcessed(this);
        }

    }
    public static class JakartaMetaConstraint<R,T> extends AbstractMetaConstraint<R,T>{
        private ConstraintValidator<? extends Annotation, T> validator;

        public JakartaMetaConstraint(ValidationContext<R> validationContext, ConstraintDefine<T> constraintDefine,
                ValueContext<T> valueContext,ConstraintValidator<? extends Annotation, T> validator) {
            super(validationContext, constraintDefine, valueContext);
           this.validator = validator;
        }

        @Override
        public void process() {
            // TODO Auto-generated method stub
            
        }
        
    }
    public static class RawMetaConstraint<R,T> extends AbstractMetaConstraint<R,T>{
        private final BeanConstraintValidator<T> validator;

        public RawMetaConstraint(ValidationContext<R> validationContext, ConstraintDefine<T> constraintDefine,
                ValueContext<T> valueContext,BeanConstraintValidator<T> validator) {
            super(validationContext, constraintDefine, valueContext);
            this.validator = validator;
        }

        @Override
        public void process() {
            if (getValidationContext().hasProcessed(this)) {
                //TODO log this
                return;
            }
            boolean valid = validator.isValid(getValueContext());
            if(!valid){
                createViolation();
            }
            getValidationContext().markProcessed(this);
        }
        
        private void createViolation(){
            String message=getConstraintDefine().message();
            BeanConstraintViolation failing=new BeanConstraintViolation(getConstraintDefine(), message, getValueContext().getPath(), getValueContext().getCurrentValue());
            getValidationContext().addFailingConstraint(failing);
        }
    }
}