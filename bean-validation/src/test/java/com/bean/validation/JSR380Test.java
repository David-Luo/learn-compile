package com.bean.validation;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.bean.model.element.FieldSymbolImpl;
import com.bean.model.element.SimpleName;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class JSR380Test {

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void manufacturerIsNull() {
        FieldSymbolImpl field = new FieldSymbolImpl();

        Set<ConstraintViolation<FieldSymbolImpl>> constraintViolations =
                validator.validate( field );

        assertEquals( 2, constraintViolations.size() );
        ConstraintViolation<FieldSymbolImpl> violation = constraintViolations.iterator().next();
        assertEquals(violation.getPropertyPath()+"校验失败", "不能为null", violation.getMessage() );
        
        field = FieldSymbolImpl.builder().simpleName(new SimpleName("myMethod")).build();
        constraintViolations =validator.validate( field );
        
        assertEquals( 0, constraintViolations.size() );
    }
}
