package com.bean.compare.meta;

import com.bean.compare.runtime.ChangeChecker;
import com.bean.service.ServiceDefinitionRegistry;
import com.google.auto.service.AutoService;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
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

    public Class<? extends ChangeChecker> lookup(String className){
        try {
            return lookup( Class.forName( className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private List<ChangeCheckerDefinition> init() {
        return ServiceLoader.load(ChangeChecker.class)
                .stream().map(ServiceLoader.Provider::type)
                .map(this::map)
                .sorted()
                .collect(Collectors.toList());
    }

    private <S> ChangeCheckerDefinition map(Class<? extends ChangeChecker> clazz) {
        Class<?> targetClass=null;
        TypeVariable<? extends Class<?>>[] typeParameters =  clazz.getTypeParameters();
        if(typeParameters.length>0){
            TypeVariable<? extends Class<?>> typeParameter = typeParameters[0];
            Type[] bounds = typeParameter.getBounds();
            if (bounds.length==0){
                targetClass = (Class)typeParameter.getGenericDeclaration();
            }else {
                targetClass = (Class<?>)bounds[0];
            }
        }
        if(targetClass == null) {
            ParameterizedType type = (ParameterizedType) findType(clazz, ChangeChecker.class);
            if (type == null) {
                throw new IllegalArgumentException("ChangeChecker must be parameterized");
            }
            Type[] types = type.getActualTypeArguments();

            if (types[0] instanceof Class) {
                targetClass = (Class<?>) types[0];
            } else if (types[0] instanceof TypeVariable) {
                TypeVariable<?> typeVariable = (TypeVariable<?>) types[0];
                targetClass = (Class<?>) typeVariable.getBounds()[0];
            } else {
                throw new IllegalArgumentException("ChangeChecker must be parameterized");
            }
        }
        int order = 0;
        return new ChangeCheckerDefinition(
            targetClass,
                clazz,
                order);
    }

    //FixMe 无法处理多重继承时的类型匹配
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
