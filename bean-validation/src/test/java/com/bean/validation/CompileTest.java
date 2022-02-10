package com.bean.validation;


import javax.tools.ToolProvider;

import com.bean.processor.AutoImplementServiceProceccor;
import com.bean.validation.stub.MethodInfoValidationSpec;

import org.junit.Test;

public class CompileTest {
    @Test
    public void testCompile(){
        compile(MethodInfoValidationSpec.class);
    }



    private void compile(Class<?> clazz){
        String className = clazz.getName().replaceAll("\\.","/");
        // 获取java编译器
        javax.tools.JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        String[] ops = {
                "-processor", AutoImplementServiceProceccor.class.getName(),
                "-d", "target/compile",
                "src/test/java/"+className+".java"};
        int i = javaCompiler.run(null, null, null, ops);
        System.out.println(className+"编译完成："+i);
    }

}
