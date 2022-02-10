package com.bean.validation.stub;


import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.validation.ConstraintValidatorContext;

import com.bean.model.element.MethodSymbolImpl;
import com.bean.validation.IValidationSpecProcess;

import org.hibernate.validator.constraints.NotEmpty;

public interface MethodInfoValidationSpec extends IValidationSpecProcess<MethodSymbolImpl> {
    @NotEmpty
    @SubsetOf({Modifier.STATIC,Modifier.PUBLIC})
    public boolean modifiers(Set<Modifier> value, ConstraintValidatorContext context);

}
