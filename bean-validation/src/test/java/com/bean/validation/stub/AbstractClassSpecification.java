package com.bean.validation.stub;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.validation.IterableElementSpec;
import com.bean.validation.SpecificationValidator;
import com.bean.validation.stub.ElementSpecification.FieldCondition;
import com.bean.validation.stub.ElementSpecification.MethodCondition;

import jakarta.validation.constraints.NotNull;

public interface AbstractClassSpecification extends SpecificationValidator<ClassSymbolImpl>{
    @NotNull
    void simpleName();

   
    @IterableElementSpec(value = MethodSpecification.class, filter = MethodCondition.class)
    @IterableElementSpec(value = FieldSpecification.class, filter = FieldCondition.class)
    void enclosedElements();

}
