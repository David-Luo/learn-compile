package com.bean.generator;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.bean.model.ObjectModelUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public abstract class AbstractServiceGenerator<T> implements ServiceGenerator<T>{
    
    protected Elements elementUtils;
    protected Types types;
    protected ObjectModelUtil objectModelUtil;
    private Filer filer;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.elementUtils = processingEnv.getElementUtils();
        this.types = processingEnv.getTypeUtils();
        this.objectModelUtil = new ObjectModelUtil(elementUtils);
        this.filer = processingEnv.getFiler();
    }

    public void processing(TypeElement classElement) {
        String packageName=elementUtils.getPackageOf(classElement).getQualifiedName().toString();
        String simpleName=classElement.getSimpleName().toString()+"Impl";
        ClassName implementClassName = ClassName.get(packageName, simpleName);
        TypeSpec.Builder classBuilder = createClassBuilder(implementClassName,classElement);
        buildClass(classBuilder, implementClassName, classElement);
        
        try {
            JavaFile.builder(packageName,classBuilder.build()).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected TypeSpec.Builder createClassBuilder(ClassName implementClassName, TypeElement classElement){
       return TypeSpec
                .classBuilder(implementClassName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(classElement.asType());
    }

    protected abstract void buildClass(TypeSpec.Builder classBuilder, ClassName implementClassName, TypeElement classElement);

}
