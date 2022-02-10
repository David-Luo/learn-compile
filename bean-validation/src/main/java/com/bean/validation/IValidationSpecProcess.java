package com.bean.validation;

import javax.validation.ConstraintValidator;

public interface IValidationSpecProcess<T>  extends ConstraintValidator<ValidationSpec, T> {
}
