package com.bean.validation;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidationSpecProcess.class)
public @interface ValidationSpec {
    Class<? extends IValidationSpecProcess>[] value() default VoidSpec.class;
    Class<? extends IValidationSpecProcess>[] element() default VoidSpec.class;

    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
    String message() default "{forbidden.word}";
    static public class VoidSpec implements IValidationSpecProcess{

        @Override
        public void initialize(Annotation constraintAnnotation) {
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            return false;
        }

    } 
}
