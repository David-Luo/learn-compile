package com.bean.validation;

import com.bean.runtime.Point;

public class SpecificationValidatorHelper {

    public <T> ValueContext<T> buildValueContext(ValidationContext<?> validationContext, 
        ValueContext<?> containerContext,
        Point path,
        T value){
        return null;
    }

    public <V> boolean isValidationRequired(ValueContext<V> valueContext, ValidCondition<V>[] conditions){
        boolean isRequired = false;
        for (ValidCondition<V> validCondition : conditions) {
            // isRequired = validCondition.test(conditionContext);
            if(isRequired)
                return isRequired;
        }
        return isRequired;
    }

    public <T> void validateMetaConstraint(
            ValidationContext<?> validationContext, 
            ValueContext<T> value, 
            ConstraintDefine<T> descriptor,
            BeanConstraintValidator<? super T> validator){
        //FIXME

    }
    public <T> void validateMetaConstraint(
        ValidationContext<?> validationContext, 
        ValueContext<T> valueContext, 
        SpecificationValidator<T> validator){
    validator.validateSpecification(validationContext, valueContext);
}

}
