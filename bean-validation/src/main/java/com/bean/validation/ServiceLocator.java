package com.bean.validation;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<String, Class> cache = new HashMap<>();
    static{
        init();
    }

    public static IValidationSpecProcess get(String service) {
        try {
            return (IValidationSpecProcess)cache.get(service).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void init(){
        try {
            cache.put("com.bean.validation.stub.ClassInfoSpeicification", Class.forName("com.bean.validation.stub.ClassInfoSpeicificationProcess"));
            cache.put("com.bean.validation.stub.InterfaceInfoSpecification", Class.forName("com.bean.validation.stub.InterfaceInfoSpecificationProcess"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
