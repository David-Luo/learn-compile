package com.bean.generator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public interface ServiceGenerator<T> {
    default void init(ProcessingEnvironment processingEnv) {}
    public void processing(TypeElement classElement);
    public static class DefaultGenerator implements ServiceGenerator<Object> {
        @Override
        public void processing(TypeElement classElement) {
        }
    }
}
