package com.bean.validation;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.model.element.MethodSymbol;
import com.bean.validation.stub.TypeInfoValidationDeclare;

import org.junit.Test;

/**
 * 规范校验Specification使用案例
 */
public class TypeInfoValidationTest {
    /**
     * 检查接口定义是否合法
     */
    @Test
    public void testInterface() {
        TypeInfoValidationDeclare declare = ParameterValidationFactory.getInstance(TypeInfoValidationDeclare.class);
        ClassSymbolImpl interfaceDefine=null;
        Set<ConstraintViolation<MethodSymbol>> constraintViolations = declare.validateInterfaceInfo(interfaceDefine);

        assertEquals( 1, constraintViolations.size() );
        ConstraintViolation<MethodSymbol> violation = constraintViolations.iterator().next();
        assertEquals(violation.getPropertyPath()+"校验失败", "不能为null", violation.getMessage() );
    }

    /**
     * 检查类定义是否合法
     */
    @Test
    public void testClass() {
        TypeInfoValidationDeclare declare = ParameterValidationFactory.getInstance(TypeInfoValidationDeclare.class);
        ClassSymbolImpl classDefine=null;
        Set<ConstraintViolation<MethodSymbol>> constraintViolations = declare.validateClassInfo(classDefine);

        assertEquals( 1, constraintViolations.size() );
        ConstraintViolation<MethodSymbol> violation = constraintViolations.iterator().next();
        assertEquals(violation.getPropertyPath()+"校验失败", "不能为null", violation.getMessage() );
    }
}
