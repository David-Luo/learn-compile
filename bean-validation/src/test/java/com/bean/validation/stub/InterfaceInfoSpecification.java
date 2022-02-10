package com.bean.validation.stub;

import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.validation.ConstraintValidatorContext;

import com.bean.annotation.AutoImplementService;
import com.bean.model.element.ClassSymbolImpl;
import com.bean.validation.IValidationSpecProcess;

import org.hibernate.validator.constraints.NotEmpty;

@AutoImplementService
public interface InterfaceInfoSpecification   extends IValidationSpecProcess<ClassSymbolImpl> {
    
    @NotEmpty
    @SubsetOf({Modifier.DEFAULT,Modifier.PUBLIC})
    public boolean modifiers(Set<Modifier> value, ConstraintValidatorContext context);
    
}
