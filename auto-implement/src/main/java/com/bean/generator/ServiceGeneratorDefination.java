package com.bean.generator;

public class ServiceGeneratorDefination {
    private String interfaceClass;
    private Class<? extends ServiceGenerator> generator;
    public ServiceGeneratorDefination(String interfaceClass, Class<? extends ServiceGenerator> generator) {
        this.interfaceClass = interfaceClass;
        this.generator = generator;
    }
    public Class<? extends ServiceGenerator> getGenerator() {
        return generator;
    }

    public boolean acceptType(String type) {
        return interfaceClass.equals(type);
    }
    
}
