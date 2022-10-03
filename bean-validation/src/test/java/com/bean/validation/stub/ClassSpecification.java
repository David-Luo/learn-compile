package com.bean.validation.stub;
import javax.lang.model.element.Modifier;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.validation.ValidCondition;
import com.bean.validation.ValidationSpec;
import com.bean.validation.SpecificationValidator;
import com.bean.validation.stub.ClassSpecification.AbstractClassCondition;

import jakarta.validation.constraints.NotBlank;

@ValidationSpec(value = AbstractClassSpecification.class, groups = AbstractClassCondition.class)
public interface ClassSpecification extends SpecificationValidator<ClassSymbolImpl>{

    @NotBlank
    void simpleName();


    public static class AbstractClassCondition implements ValidCondition<ClassSymbolImpl>{
        public boolean test(ClassSymbolImpl t){
            return t.getModifiers().contains(Modifier.ABSTRACT);
        }
    }
}
