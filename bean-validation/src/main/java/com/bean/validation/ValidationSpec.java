package com.bean.validation;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.bean.validation.ValidationSpec.List;

@Target({ TYPE, FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@Repeatable(List.class)
public @interface ValidationSpec {
    Class<? extends SpecificationValidator<?>>[] value();
    Conditional condition() default @Conditional;
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
    String message() default "{forbidden.word}";
    @Documented
	@Target({ TYPE, FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	public @interface List {
		ValidationSpec[] value();
	}  

    public class ValidationSpecDefinex  implements Annotation{
        private final Class<? extends SpecificationValidator<?>>[] value;
        private final Conditional condition;
        private final Class<? extends Payload>[] payload;
        private final Class<?>[] groups;
        private final String message;

        public ValidationSpecDefinex(Class<? extends SpecificationValidator<?>>[] value, Conditional condition,
                Class<? extends Payload>[] payload, Class<?>[] groups, String message) {
            this.value = value;
            this.condition = condition;
            this.payload = payload;
            this.groups = groups;
            this.message = message;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return ValidationSpec.class;
        }
    public Class<? extends SpecificationValidator<?>>[] value(){
        return value;
    }
    public Conditional condition(){
        return condition;
    }
    public Class<? extends Payload>[] payload(){
        return payload;
    }
    public Class<?>[] groups(){
        return groups;
    }
    public String message(){
        return message;
    }

    }

}