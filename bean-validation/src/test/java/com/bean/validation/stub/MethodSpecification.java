package com.bean.validation.stub;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;

import com.bean.validation.ValidCondition;
import com.bean.validation.ValidationContext;
import com.bean.validation.ValueContext;
import com.bean.validation.SpecificationValidator;

import jakarta.validation.constraints.NotBlank;

public interface MethodSpecification extends SpecificationValidator<ExecutableElement>{

    @NotBlank
    boolean simpleName(ValidationContext validationContext, ValueContext<Name> valueContext);


    void modifiers(ValidationContext validationContext, ValueContext<ExecutableElement> beanContext);

    public class NotAbstractCondition implements ValidCondition<ExecutableElement>{
        public boolean test(ExecutableElement t){
            return !t.getModifiers().contains(Modifier.ABSTRACT);
        }
    }
}
