package com.bean.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;

import com.bean.validation.AnnotationUtils;
import com.bean.validation.IValidationSpecProcess;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class ConstraintValidatorGenerator {
    private Types typeUtil;
    private Elements elementUtil;

    public ConstraintValidatorGenerator(Types types, Elements elements) {
        this.typeUtil = types;
        this.elementUtil = elements;
    }

    public void createCode(ClassName implementClassName, List<ExecutableElement> methods, TypeSpec.Builder classBuilder,
            TypeElement classElement) {
        methods.stream().filter(this::isInitialize).map(m -> createInitialize(m, classBuilder, classElement))
                .forEach(f -> classBuilder.addMethod(f));

        methods = methods
                .stream()
                .filter(this::canBeOverride)
                .filter(e -> !e.isDefault())
                .filter(this::isValidator)
                .collect(Collectors.toList());

        List<MethodSpec> propertyValidation = methods.stream().filter(m -> !isMainValidator(m))
                .map(m -> buildPropertyValidator(implementClassName, m, classBuilder))
                .collect(Collectors.toList());
        propertyValidation.stream().forEach(f -> classBuilder.addMethod(f));
        
        methods.stream().filter(this::isMainValidator).map(m -> buildMainValidator(m, classBuilder, classElement,implementClassName,propertyValidation))
                .forEach(f -> classBuilder.addMethod(f));

    }

    private MethodSpec createInitialize(ExecutableElement method, TypeSpec.Builder classBuilder,
            TypeElement classElement) {
        DeclaredType declaredType = (DeclaredType) classElement.asType();
        MethodSpec.Builder builder = MethodSpec.overriding(method, declaredType, typeUtil);
        return builder.build();
    }

    private boolean isInitialize(ExecutableElement m) {
        return m.getSimpleName().toString().equals("initialize");
    }

    private MethodSpec buildPropertyValidator(ClassName implementClassName, ExecutableElement method,
            TypeSpec.Builder classBuilder) {

        MethodSpec.Builder builder = MethodSpec.overriding(method);
        String methodName = method.getSimpleName().toString();
        buildBody(implementClassName, method, classBuilder, builder, methodName);
        return builder.build();
    }

    private void buildBody(ClassName implementClassName, ExecutableElement method, TypeSpec.Builder classBuilder,
            MethodSpec.Builder builder, String methodName) {
        List<? extends AnnotationMirror> constraintAnno = method.getAnnotationMirrors().stream()
                .filter(this::isConstraint).collect(Collectors.toList());
        CodeBlock.Builder code = CodeBlock.builder();
        code.addStatement("boolean temp,result = true");
        for (AnnotationMirror annotationMirror : constraintAnno) {
            String validatorFieldName = buildValidator(methodName, annotationMirror, classBuilder, implementClassName);
            code.addStatement("temp = $L.isValid(value, context)", validatorFieldName);
            code.addStatement("result = result && temp");
        }
        code.addStatement("return result");
        builder.addCode(code.build());
    }

    private boolean isConstraint(AnnotationMirror annotationMirror) {
        return getConstraint(annotationMirror) != null;
    }

    private Constraint getConstraint(AnnotationMirror annotationMirror) {
        Constraint[] c = annotationMirror.getAnnotationType().asElement().getAnnotationsByType(Constraint.class);
        if (c.length > 0) {
            return c[0];
        }
        return null;
    }

    private String buildValidator(String methodName, AnnotationMirror annotationMirror,
            TypeSpec.Builder classBuilder, ClassName implementClassName) {
        String validatorFieldName = methodName
                + annotationMirror.getAnnotationType().asElement().getSimpleName().toString();
        String annFieldName = "_" + validatorFieldName;

        FieldSpec.Builder annField = FieldSpec.builder(ClassName.get(Object.class), annFieldName, Modifier.PRIVATE);
        annField.addAnnotation(AnnotationSpec.get(annotationMirror));
        classBuilder.addField(annField.build());
        String constraintValidatorClassName = getConstraintValidatorClassName(annotationMirror);
        FieldSpec.Builder validatorField = FieldSpec.builder(ClassName.get(ConstraintValidator.class),
                validatorFieldName, Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        validatorField.initializer("$T.initConstraintValidator($S,$S,$S)", AnnotationUtils.class,
                constraintValidatorClassName, implementClassName.toString(), annFieldName);
        classBuilder.addField(validatorField.build());
        return validatorFieldName;
    }

    private String getConstraintValidatorClassName(AnnotationMirror annotationMirror) {
        Optional<AnnotationMirror> constraintMirror = AnnotationUtils
                .getAnnotationMirror(annotationMirror.getAnnotationType().asElement(), Constraint.class);
        Optional<AnnotationValue> op = AnnotationUtils.getAnnotationValue(constraintMirror.get(), "validatedBy");
        if (op.isPresent()) {
            List list = (List) op.get().getValue();
            if (!list.isEmpty()) {
                return list.get(0).toString().replace(".class", "");
            }
        }

        return null;
    }

    private boolean isMainValidator(ExecutableElement m) {
        return m.getSimpleName().toString().equals("isValid");
    }

    private MethodSpec buildMainValidator(ExecutableElement method, TypeSpec.Builder classBuilder,
            TypeElement classElement, ClassName implementClassName,List<MethodSpec> propertyValidation) {
        DeclaredType declaredType = (DeclaredType) classElement.asType();
        MethodSpec.Builder builder = MethodSpec.overriding(method, declaredType, typeUtil);
        CodeBlock.Builder code = CodeBlock.builder();
        code.addStatement("boolean temp,result = true");
        code.addStatement("temp = _isValid($L, $L)",builder.parameters.get(0).name,builder.parameters.get(1).name);
        code.addStatement("result = result && temp");
        code.addStatement("temp = _children($L, $L)",builder.parameters.get(0).name,builder.parameters.get(1).name);
        code.addStatement("result = result && temp");
        code.addStatement("return true");
        builder.addCode(code.build());
        MethodSpec shaddow = shaddow(builder, method, classBuilder, classElement, implementClassName);
        classBuilder.addMethod(shaddow);
        MethodSpec children = children(builder, method, classBuilder, classElement, implementClassName,propertyValidation);
        classBuilder.addMethod(children);
        
        return builder.build();
    }

    private MethodSpec shaddow(MethodSpec.Builder mainBuilder, ExecutableElement method, TypeSpec.Builder classBuilder,
            TypeElement classElement, ClassName implementClassName) {
        String methodName = "_"+method.getSimpleName().toString();
        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName);
        builder.returns(boolean.class);
        builder.addModifiers(Modifier.PRIVATE);
        builder.addParameters(mainBuilder.parameters);
        buildBody(implementClassName, method, classBuilder, builder, methodName);

        return builder.build();
    }

    private MethodSpec children(MethodSpec.Builder mainBuilder, ExecutableElement method, TypeSpec.Builder classBuilder,
            TypeElement classElement, ClassName implementClassName,List<MethodSpec> propertyValidation) {
        String methodName = "_children";
        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName);
        builder.returns(boolean.class);
        builder.addModifiers(Modifier.PRIVATE);
        builder.addParameters(mainBuilder.parameters);
        CodeBlock.Builder code = CodeBlock.builder();
        code.addStatement("boolean temp,result = true");
        for (MethodSpec methodSpec : propertyValidation) {
            code.addStatement("ConstraintValidatorContext context = arg1");
            code.addStatement("temp = $L(arg0.$L(), context)",methodSpec.name,getter(methodSpec.name));
            code.addStatement("result = result && temp");
        }

        code.addStatement("return result");
        builder.addCode(code.build());
        return builder.build();
    }

    private String getter(String fieldName){
       return  "get"+Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }
    private boolean canBeOverride(ExecutableElement method) {
        Set<Modifier> modifiers = method.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE)
                || modifiers.contains(Modifier.FINAL)
                || modifiers.contains(Modifier.STATIC)) {
            return false;
        }
        return true;
    }

    private boolean isValidator(ExecutableElement method) {

        if (method.getParameters().size() != 2) {
            return false;
        }
        if (method.getReturnType().getKind() != TypeKind.BOOLEAN) {
            return false;
        }
        return true;
    }

}
