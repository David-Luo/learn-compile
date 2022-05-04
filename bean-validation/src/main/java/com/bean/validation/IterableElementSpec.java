package com.bean.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Payload;

import com.bean.validation.IterableElementSpec.List;

@Target({ TYPE, FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface IterableElementSpec {
    Class<? extends SpecificationValidator<?>>[] value();
    Class<? extends ValidCondition> filter() default ValidCondition.TrueCondition.class;
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
    String message() default "{forbidden.word}";

    @Documented
	@Target({ TYPE, FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	public @interface List {
		IterableElementSpec[] value();
	}  
    
}
