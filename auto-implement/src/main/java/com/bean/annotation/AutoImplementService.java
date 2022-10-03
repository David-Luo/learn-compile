package com.bean.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bean.generator.ServiceGenerator;
import com.bean.generator.ServiceGenerator.DefaultGenerator;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface AutoImplementService {
    Class<? extends ServiceGenerator> value() default DefaultGenerator.class;
}
