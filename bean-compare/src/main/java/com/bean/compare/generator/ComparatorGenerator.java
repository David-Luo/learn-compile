package com.bean.compare.generator;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import static javax.lang.model.util.ElementFilter.methodsIn;
import static javax.lang.model.util.ElementFilter.fieldsIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.bean.compare.meta.ChangeCheckerRegistry;

import com.bean.compare.runtime.ChangeChecker;
import com.bean.compare.runtime.PojoDifferance;
import com.bean.generator.AbstractServiceGenerator;
import com.bean.generator.ServiceGenerator;
import com.bean.model.ElementUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import javax.lang.model.element.Modifier;

@AutoService(ServiceGenerator.class)
public class ComparatorGenerator extends AbstractServiceGenerator<ChangeChecker> implements ServiceGenerator<ChangeChecker>{
    private ChangeCheckerRegistry changeCheckerRegistry = new ChangeCheckerRegistry();
    protected void buildClass(TypeSpec.Builder classBuilder, ClassName implementClassName, TypeElement classElement){
        List<ExecutableElement> methods = methodsIn(elementUtils.getAllMembers(classElement));
        TypeElement changeCheckerInterface = elementUtils.getTypeElement(ChangeChecker.class.getCanonicalName());
        List<ExecutableElement> changeCheckerInterfaceMethod = methodsIn(elementUtils.getAllMembers(changeCheckerInterface));
        implementMethod(classBuilder,classElement,changeCheckerInterfaceMethod);
        classBuilder.addAnnotation(serviceGeneratorAnnotation());
        methods
            .stream()
            .filter(this::canBeOverride)
            .filter(e -> !e.isDefault())
            .filter(e -> !objectModelUtil.isJavaLangObjectMethod(e))
            .filter(e -> !ElementUtils.containsIn(e, changeCheckerInterfaceMethod))
            .forEach(e -> implementMethod(classBuilder, e));
             
    }

    private AnnotationSpec serviceGeneratorAnnotation(){
        AnnotationSpec.Builder builder = AnnotationSpec.builder(AutoService.class);
        builder.addMember("value", "$T.class", ChangeChecker.class);
        return builder.build();
    }
    private void implementMethod(TypeSpec.Builder classBuilder, TypeElement classElement,List<ExecutableElement> methods){
        ExecutableElement targetMethod = findByName(methods, "check");
        DeclaredType declaredType = (DeclaredType)classElement.getInterfaces().get(0);
        
        MethodSpec.Builder builder = MethodSpec.overriding(targetMethod,declaredType,types);
        CodeBlock.Builder body = CodeBlock.builder();
        body.addStatement("if (arg0 == null && arg1 == null) {return Optional.empty();}");
        body.addStatement("$T<String, Differance> attributeChange = new $T<>()", Map.class,HashMap.class);
        body.addStatement("Optional<Differance> attributeDiff");
        DeclaredType targetClass =(DeclaredType) declaredType.getTypeArguments().get(0);
        List<VariableElement> fields = fieldsIn(elementUtils.getAllMembers((TypeElement)targetClass.asElement()));
        for (VariableElement variableElement : fields) {
            
            String filedName = variableElement.getSimpleName().toString();
            String className = ((DeclaredType)variableElement.asType()).asElement().toString();
            Class cheker = changeCheckerRegistry.lookup(className);
            String checkerName = filedName+"Checker";
            body.addStatement("$T $L = new $T()", cheker,checkerName, cheker);
            body.addStatement("attributeDiff = $L.check(arg0.getSalary(), arg1.getSalary())",checkerName);
            body.addStatement("if (attributeDiff.isPresent()) {attributeChange.put($S, attributeDiff.get());}", filedName);
        }

        

        
        body.add("return Optional.of(new $T(arg0, arg1, attributeChange));\n", PojoDifferance.class);
        builder.addCode(body.build());
        classBuilder.addMethod(builder.build());
    }

    private ExecutableElement findByName(List<ExecutableElement> methods, String name){
        return methods.stream().filter(e -> e.getSimpleName().toString().equals(name)).findAny().get();
    }
    private void implementMethod(TypeSpec.Builder classBuilder, ExecutableElement method){
        classBuilder.addMethod(
            MethodSpec.overriding(method).build()
            );
    }

    private boolean canBeOverride(ExecutableElement method){
        Set<Modifier> modifiers = method.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE)
            || modifiers.contains(Modifier.FINAL)
            || modifiers.contains(Modifier.STATIC)) {
          return false;
        }
        return true;
    }
}
