package com.bean;


import javax.tools.ToolProvider;

import com.google.auto.service.processor.AutoServiceProcessor;

import org.junit.Test;

public class CompileTest {
    @Test
    public void testCompile(){
        compile(NotNullValidator.class);
    }



    private void compile(Class<?> clazz){
        String className = clazz.getName().replaceAll("\\.","/");
        // 获取java编译器
        javax.tools.JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        String[] ops = {
                "-processor", AutoServiceProcessor.class.getName(),
                "-d", "target/compile",
                "src/test/java/"+className+".java"};
        int i = javaCompiler.run(null, null, null, ops);
        System.out.println(className+"编译完成："+i);
    }

}
