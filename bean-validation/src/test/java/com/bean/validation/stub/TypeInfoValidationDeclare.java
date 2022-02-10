package com.bean.validation.stub;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.model.element.MethodSymbol;
import com.bean.validation.ValidationSpec;

public interface TypeInfoValidationDeclare {

    /**
     * 校验interface接口信息合法性
     * @param methodInfo
     * @return
     */
    Set<ConstraintViolation<MethodSymbol>> validateInterfaceInfo(
            @NotNull 
            @ValidationSpec(ClassInfoSpeicification.class) 
                ClassSymbolImpl methodInfo);

    /**
     * 校验class类信息合法性
     * @param methodInfo
     * @return
     */
    Set<ConstraintViolation<MethodSymbol>> validateClassInfo(
            @NotNull 
            @ValidationSpec(InterfaceInfoSpecification.class) 
                ClassSymbolImpl methodInfo);

}
