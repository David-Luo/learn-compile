package com.bean.validation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

public class ParameterValidationFactory implements InvocationHandler {
    private Object stub = new Object();

    private ExecutableValidator executableValidator;

    public ParameterValidationFactory() {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        executableValidator = factory.getValidator().forExecutables();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(!Set.class.isAssignableFrom(method.getReturnType())){
            return method.invoke(stub, args);
        }
        
        return executableValidator.validateParameters(proxy, method, args);
    }

    public static <T> T getInstance(Class<T> service) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] { service },
                new ParameterValidationFactory());
    }
}
