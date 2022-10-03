package com.bean;

import com.google.auto.service.AutoService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;

/**
 * Validate that the object is not {@code null}.
 *
 * @author Emmanuel Bernard
 */
@AutoService(ConstraintValidator.class)
public class NotNullValidator implements ConstraintValidator<NotNull, Object> {

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
		return object != null;
	}
}
