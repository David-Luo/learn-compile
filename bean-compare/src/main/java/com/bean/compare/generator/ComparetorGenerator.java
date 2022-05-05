package com.bean.compare.generator;

import javax.lang.model.element.TypeElement;

import com.bean.compare.runtime.ChangeChecker;
import com.bean.generator.ServiceGenerator;
import com.google.auto.service.AutoService;

@AutoService(ServiceGenerator.class)
public class ComparetorGenerator implements ServiceGenerator<ChangeChecker>{

    @Override
    public void processing(TypeElement classElement) {
        // TODO Auto-generated method stub
        System.out.println("hello world");
    }
    
}
