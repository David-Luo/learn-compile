package com.bean;

import javax.lang.model.element.TypeElement;

import com.bean.generator.ServiceGenerator;

public class ComparetorGenerator implements ServiceGenerator<Comparetor>{

    @Override
    public void processing(TypeElement classElement) {
        // TODO Auto-generated method stub
        System.out.println("hello world");
    }
    
}
