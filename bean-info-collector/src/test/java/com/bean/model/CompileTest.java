package com.bean.model;


import javax.tools.ToolProvider;

import com.bean.model.element.*;
import com.bean.processing.BeanInfoCollectorProcesser;

import org.junit.Test;

public class CompileTest {
    @Test
    public void testCompile(){
        compile(PackageSymbol.class);
        // compile(MethodSymbol.class);
        // compile(ClassSymbol.class);
        // compile(ParameterSymbol.class);
        // compile(FieldSymbol.class);
        // compile(TypeParameterSymbol.class);
        // compile(TestTypeSymbol.class);
    }



    private void compile(Class<?> clazz){
        String className = clazz.getName().replaceAll("\\.","/");
        // 获取java编译器
        javax.tools.JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        String[] ops = {
                "-processor", BeanInfoCollectorProcesser.class.getName(),
                "-d", "target/compile",
                "src/test/java/"+className+".java"};
        int i = javaCompiler.run(null, null, null, ops);
        System.out.println(className+"编译完成："+i);
    }

}
