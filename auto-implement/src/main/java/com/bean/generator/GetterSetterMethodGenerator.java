package com.bean.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class GetterSetterMethodGenerator {

    public void createDataMethod(List<ExecutableElement> methods, TypeSpec.Builder classBuilder) {
        methods = methods
            .stream()
            .filter(this::canBeOverride)
            .filter(e -> !e.isDefault())
            .collect(Collectors.toList());

        List<ExecutableElement> getter = methods.stream()
            .filter(this::isGetter)
            .collect(Collectors.toList());

        List<ExecutableElement> setter = methods.stream()
            .filter(this::isSetter)
            .collect(Collectors.toList());

        List<FieldSpec> fieldSpecs = buildFieldForGetter(getter);
        fieldSpecs.stream().forEach( f -> classBuilder.addField(f));
        List<MethodSpec> getterSpecs = buildGetterSpec(getter);
        getterSpecs.stream().forEach( f -> classBuilder.addMethod(f));
        List<MethodSpec> setterSpecs = buildSetterSpec(setter);
        setterSpecs.stream().forEach( f -> classBuilder.addMethod(f));
    }

    private List<MethodSpec> buildSetterSpec(List<ExecutableElement> setter){
        return setter.stream().map(this::buildSetterSpec).collect(Collectors.toList());
    }
    private MethodSpec buildSetterSpec(ExecutableElement setter){
        MethodSpec.Builder builder = MethodSpec.overriding(setter);
        String fieldName = setterToFieldName(setter);
        builder.addCode("this.$L = $L;", fieldName,setter.getParameters().get(0).getSimpleName().toString());
        return builder.build();
    }
    private List<MethodSpec> buildGetterSpec(List<ExecutableElement> getter) {
        return getter.stream().map(this::buildGetterSpec).collect(Collectors.toList());
    }
    private MethodSpec buildGetterSpec(ExecutableElement getter){
        MethodSpec.Builder builder = MethodSpec.overriding(getter);
        String fieldName = getterToFieldName(getter);
        builder.addCode("return $L;", fieldName);
        return builder.build();
    }
    private List<FieldSpec> buildFieldForGetter( List<ExecutableElement> getter) {
       return getter.stream().map(this::buildFieldForGetter).collect(Collectors.toList());
    }
    private FieldSpec buildFieldForGetter(ExecutableElement getter){
        TypeName type=ClassName.get(getter.getReturnType());
        String fieldName = getterToFieldName(getter);
        FieldSpec.Builder builder = FieldSpec.builder(type, fieldName, Modifier.PRIVATE);
        
        getter.getAnnotationMirrors().stream()
            .filter(this::canAnnotationOnField)
            .map(AnnotationSpec::get)
            .forEach(builder::addAnnotation);
        
        return builder.build();
    }
   
    private boolean canAnnotationOnField(AnnotationMirror mirror){
        ElementType[] target = mirror
            .getAnnotationType()
            .asElement()
            .getAnnotationsByType(Target.class)[0]
            .value();

        for (ElementType t : target) {
            if(t == ElementType.FIELD){
                return true;
            }
        }
        return false;
    }

    private static String getterToFieldName(ExecutableElement getter) {
        String methodName=getter.getSimpleName().toString();
        if (methodName.startsWith("get")) {
            return fieldName(methodName);
        }
        return null;
    }
    private static String setterToFieldName(ExecutableElement getter) {
        String methodName=getter.getSimpleName().toString();
        if (methodName.startsWith("set")) {
            return fieldName(methodName);
        }
        return null;
    }

    private static String fieldName(String methodName) {
        String str = methodName.substring(3);
        if (str.length() == 0) {
            return null;
        } else if (str.length() == 1) {
            return str.toLowerCase();
        } else {
            return Character.toLowerCase(str.charAt(0)) + str.substring(1);
        }
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
    private boolean isGetter(ExecutableElement method){
        if(!method.getSimpleName().toString().startsWith("get")){
            return false;
        }
        if(method.getParameters().size()>0){
            return false;
        }
        if(method.getReturnType().getKind() == TypeKind.VOID){
            return false;
        }
        return true;
    }
    private boolean isSetter(ExecutableElement method){
        if(!method.getSimpleName().toString().startsWith("set")){
            return false;
        }
        if(method.getParameters().size() != 1){
            return false;
        }
        if(method.getReturnType().getKind() != TypeKind.VOID){
            return false;
        }
        return true;
    }

}
