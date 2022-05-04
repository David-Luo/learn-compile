package com.bean.generator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import com.bean.service.ServiceDefinitionRegistry;
import com.google.auto.service.AutoService;

@AutoService(ServiceDefinitionRegistry.class)
public class ServiceGeneratorRegistry  implements ServiceDefinitionRegistry<ServiceGenerator>{
    private List<ServiceGeneratorDefination> definations;

    public ServiceGeneratorRegistry() {
        this.definations = init();
    }

    public Class<? extends ServiceGenerator> lookup(String target) {
        Optional<?> o = definations.stream()
                .filter(b -> b.acceptType(target))
                .map(ServiceGeneratorDefination::getGenerator)
                .findFirst();
        if (o.isPresent()) {
            return (Class<? extends ServiceGenerator>) o.get();
        }
        return null;
    }
    private List<ServiceGeneratorDefination> init() {
        return ServiceLoader.load(ServiceGenerator.class)
                .stream().map(ServiceLoader.Provider::type)
                .map(this::map)
                .collect(Collectors.toList());
    }

    private <S> ServiceGeneratorDefination map(Class<? extends ServiceGenerator> clazz) {
        ParameterizedType type = (ParameterizedType) findType(clazz, ServiceGenerator.class);
        if (type == null) {
            throw new IllegalArgumentException("ServiceGenerator must be parameterized");
        }
        Type[] types = type.getActualTypeArguments();
        return new ServiceGeneratorDefination(
                types[0].getTypeName(),
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

}
