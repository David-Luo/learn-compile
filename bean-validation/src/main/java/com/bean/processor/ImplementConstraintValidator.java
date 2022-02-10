package com.bean.processor;
import java.io.IOException;
import java.util.List;

import static javax.lang.model.util.ElementFilter.methodsIn;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import com.bean.generator.ConstraintValidatorGenerator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class ImplementConstraintValidator {
    private Elements elementUtils;
    private Filer filer;
    private ConstraintValidatorGenerator generator;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.elementUtils = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
        this.generator = new ConstraintValidatorGenerator();
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
        generator.createDataMethod(methods, classBuilder);

        // System.out.println(classBuilder.build().toString());
        return classBuilder.build();
    }

}
