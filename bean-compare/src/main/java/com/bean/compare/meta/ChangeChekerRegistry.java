package com.bean.compare.meta;

import com.bean.compare.runtime.ChangeChecker;
import com.bean.service.ServiceDefinitionRegistry;
import com.google.auto.service.AutoService;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@AutoService(ServiceDefinitionRegistry.class)
public class ChangeChekerRegistry implements ServiceDefinitionRegistry<ChangeChecker>{
    
    private List<ChangeCheckerDefinition> definations;

    public ChangeChekerRegistry() {
        this.definations = init();
    }

    public Class<? extends ChangeChecker> lookup(Class<?> target) {
        Optional<?> o = definations.stream()
                .filter(b -> b.acceptType(target))
                .map(ChangeCheckerDefinition::getCheckerClass)
                .findFirst();
        if (o.isPresent()) {
            return (Class<? extends ChangeChecker>) o.get();
        }
        return null;
    }

    private List<ChangeCheckerDefinition> init() {
        return ServiceLoader.load(ChangeChecker.class)
                .stream().map(ServiceLoader.Provider::type)
                .map(this::map)
                .sorted()
                .collect(Collectors.toList());
    }

    private <S> ChangeCheckerDefinition map(Class<? extends ChangeChecker> clazz) {
        ParameterizedType type = (ParameterizedType) findType(clazz, ChangeChecker.class);
        if (type == null) {
            throw new IllegalArgumentException("ChangeChecker must be parameterized");
        }
        Type[] types = type.getActualTypeArguments();
        int order = 0;
        return new ChangeCheckerDefinition(
                (Class<?>) types[0],
                (Class<?>) types[1],
                order);
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
