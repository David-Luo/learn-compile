package com.bean.validation.stub;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.validation.ConstraintValidatorContext;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.validation.IValidationSpecProcess;
import com.bean.validation.ValidationSpec;

import org.hibernate.validator.constraints.NotEmpty;


public interface ClassInfoSpeicification  extends IValidationSpecProcess<ClassSymbolImpl> {
    
    @ValidationSpec(element = ClassElementsValidateSpec.class)
    public boolean enclosedElements(List<ExecutableElement> methods, ConstraintValidatorContext context);


    @NotEmpty
    @SubsetOf({Modifier.FINAL,Modifier.PUBLIC})
    public boolean modifiers(Set<Modifier> value, ConstraintValidatorContext context);
}
