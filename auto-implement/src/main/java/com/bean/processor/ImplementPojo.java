package com.bean.processor;

import static javax.lang.model.util.ElementFilter.methodsIn;

import java.io.IOException;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import com.bean.generator.GetterSetterMethodGenerator;
import com.bean.generator.LangObjectMethodGenerator;
import com.bean.generator.PojoBuilderGenerator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class ImplementPojo {
    private Elements elementUtils;
    private Filer filer;
    private GetterSetterMethodGenerator pojoDataMethodBuilder = new GetterSetterMethodGenerator();
    private PojoBuilderGenerator pattern = new PojoBuilderGenerator();

    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.elementUtils = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
    }

    public void processing(TypeElement classElement) {
        String packageName=elementUtils.getPackageOf(classElement).getQualifiedName().toString();
        String simpleName=classElement.getSimpleName().toString()+"Impl";
        ClassName implementClassName = ClassName.get(packageName, simpleName);
        TypeSpec implementClass = buildImplementClass(implementClassName, classElement);
        
        try {
            JavaFile.builder(packageName,implementClass).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TypeSpec buildImplementClass(ClassName implementClassName, TypeElement classElement) {
        TypeSpec.Builder classBuilder = TypeSpec
                .classBuilder(implementClassName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(classElement.asType());

        List<ExecutableElement> methods = methodsIn(elementUtils.getAllMembers(classElement));
        pojoDataMethodBuilder.createDataMethod(methods, classBuilder);

        pattern.createBuilder(implementClassName,classBuilder);

        LangObjectMethodGenerator langObjectMethodBuilder = new LangObjectMethodGenerator(elementUtils,implementClassName,classBuilder);
        langObjectMethodBuilder.overrideLangObject();

        // System.out.println(classBuilder.build().toString());
        return classBuilder.build();
    }

}
