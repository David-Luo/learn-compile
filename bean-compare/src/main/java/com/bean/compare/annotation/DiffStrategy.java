package com.bean.compare.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

import com.bean.compare.runtime.ChangeChecker;
import com.bean.compare.runtime.Differance;

@Target({
        ElementType.METHOD,
        ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface DiffStrategy {
    Class<? extends ChangeChecker> value() default NullChecker.class;
    String key() default "";
    Class<? extends ChangeChecker> elementChecker() default NullChecker.class;

    class NullChecker implements ChangeChecker{
        @Override
        public Optional<Differance> check(Object left, Object right) {
            return Optional.empty();
        }
    }
}
