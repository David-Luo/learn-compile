package com.bean;

import javax.tools.ToolProvider;

import com.bean.compare.stub.diff.TeacherChangeChecker;
import com.bean.processor.AutoImplementServiceProcessor;

import org.junit.Test;

public class AutoImplementServiceProcessorTest {
    
    @Test
    public void testCompile(){
        compile(TeacherChangeChecker.class);
    }



    private void compile(Class<?> clazz){
        String className = clazz.getName().replaceAll("\\.","/");
        // 获取java编译器
        javax.tools.JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        String[] ops = {
                "-processor", AutoImplementServiceProcessor.class.getName(),
                "-d", "target/compile",
                "src/test/java/"+className+".java"};
        int i = javaCompiler.run(null, null, null, ops);
        System.out.println(className+"编译完成："+i);
    }

}
