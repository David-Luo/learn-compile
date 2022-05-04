package com.bean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import com.bean.service.ServiceDefinitionRegistry;
import com.google.auto.service.AutoService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.constraints.NotEmpty;

@AutoService(ServiceDefinitionRegistry.class)
public class ConstraintValidatorRegistry implements ServiceDefinitionRegistry<ConstraintValidator<?, ?>> {
    private List<ConstraintValidatorDefination> definations;

    public ConstraintValidatorRegistry() {
        this.definations = init();
    }

    public Class<? extends ConstraintValidator> lookup(Class<?> annotation, Class<?> target) {
        Optional<?> o = definations.stream()
                .filter(a -> a.acceptAnnotation(annotation))
                .filter(b -> b.acceptType(target))
                .map(ConstraintValidatorDefination::getValidatorClass)
                .findFirst();
        if (o.isPresent()) {
            return (Class<? extends ConstraintValidator>) o.get();
        }
        return null;
    }

    private List<ConstraintValidatorDefination> init() {
        return ServiceLoader.load(ConstraintValidator.class)
                .stream().map(ServiceLoader.Provider::type)
                .map(this::map)
                .collect(Collectors.toList());
    }

    private <S> ConstraintValidatorDefination map(Class<? extends ConstraintValidator> clazz) {
        ParameterizedType type = (ParameterizedType) findType(clazz, ConstraintValidator.class);
        if (type == null) {
            throw new IllegalArgumentException("ConstraintValidator must be parameterized");
        }
        Type[] types = type.getActualTypeArguments();
        return new ConstraintValidatorDefination(
                (Class<?>) types[0],
                (Class<?>) types[1],
                clazz);
    }

    private Type findType(Class<?> clazz, Class<?> interfaceClass) {
        Type[] types = clazz.getGenericInterfaces();
        for (Type type : types) {
            if (type.getTypeName().startsWith(interfaceClass.getCanonicalName())) {
                return type;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ConstraintValidatorRegistry registry = new ConstraintValidatorRegistry();
        Class v = registry.lookup(NotEmpty.class, Object[].class);
        System.out.println(v);
    }
}
