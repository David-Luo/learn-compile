package com.bean.generator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

import static javax.lang.model.util.ElementFilter.methodsIn;

import com.bean.annotation.Mandatory;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class LangObjectMethodGenerator {
    private Elements elementUtils;
    private List<FieldSpec> fieldSpecs;
    private ClassName className;
    private TypeSpec.Builder classBuilder;
    private Map<String,Function<MethodSpec.Builder,CodeBlock>> bodyBuilders=new HashMap<>();

    public LangObjectMethodGenerator(Elements elementUtils,
            ClassName className,
            TypeSpec.Builder classBuilder){
        this.elementUtils = elementUtils;
        this.className = className;
        this.classBuilder = classBuilder;
        this.fieldSpecs = new LinkedList<>();
        init();
    }

    private void init(){
        bodyBuilders.put("toString", this::buildToStringBody);
        bodyBuilders.put("equals", this::buildEqualsBody);
        bodyBuilders.put("hashCode", this::buildHashCodeBody);
    }

    private boolean isMandatory(FieldSpec fieldSpec){
       return fieldSpec.annotations.stream()
                .anyMatch(a -> a.type.toString().equals(Mandatory.class.getCanonicalName()));
    }
    public void overrideLangObject() {
        List<FieldSpec> fieldSpecs = classBuilder.fieldSpecs.stream().filter(this::isMandatory).collect(Collectors.toList());
        this.fieldSpecs = fieldSpecs.isEmpty()?classBuilder.fieldSpecs:fieldSpecs;

        List<ExecutableElement> methods = methodsIn(elementUtils.getAllMembers(elementUtils.getTypeElement(Object.class.getName())));
        for (ExecutableElement executableElement : methods) {
            String methodName = executableElement.getSimpleName().toString();
            Function<MethodSpec.Builder,CodeBlock> bodyBuilder = bodyBuilders.get(methodName);
            if(bodyBuilder==null){
                continue;
            }
            MethodSpec.Builder methodBuilder = MethodSpec.overriding(executableElement);
            methodBuilder.addCode(bodyBuilder.apply(methodBuilder));
            classBuilder.addMethod(methodBuilder.build());
        }
    }
    private CodeBlock buildHashCodeBody(MethodSpec.Builder methodBuilder){
        CodeBlock.Builder builder = CodeBlock.builder();
        methodBuilder.modifiers.remove(Modifier.NATIVE);
        builder.addStatement("int result = 17");
        String propertyName;
        for (FieldSpec fieldSymbol : fieldSpecs) {
            propertyName = fieldSymbol.name;
            builder.add("if ($L != null) {result = 31 * result + $L.hashCode();}\n", propertyName, propertyName);
        }
        builder.addStatement("return result");
        return builder.build();
    }

    private CodeBlock buildEqualsBody(MethodSpec.Builder methodBuilder){
        String that = methodBuilder.parameters.get(0).name;
        
        CodeBlock.Builder builder = CodeBlock.builder(); 
        builder.add("if(this == $L){return true;}\n", that);
        builder.add("if(!($L instanceof $L))return false;\n",that, className.simpleName());
        builder.addStatement("$L that = ($L)$L",className.simpleName(),className.simpleName(), that);
        String propertyName;
        for (FieldSpec fieldSymbol : fieldSpecs) {
            propertyName = fieldSymbol.name;
            builder.addStatement("if(!$T.equals($L, that.$L))return false",Objects.class,propertyName,propertyName);
        }
        builder.addStatement("return true");
        return builder.build();
    }
    private CodeBlock buildToStringBody(MethodSpec.Builder methodBuilder){
        CodeBlock.Builder builder = CodeBlock.builder(); 
        builder.addStatement("StringBuffer sb = new StringBuffer()");
        builder.addStatement("sb.append(String.format(\"{\\\"class\\\":\\\"%s\\\"\", $S))",className.simpleName());
        
        for (FieldSpec fieldSymbol : fieldSpecs) {
            builder.add("if($L != null){\n",fieldSymbol.name);
            builder.addStatement("sb.append(String.format(\",\\\"$L\\\":\\\"%s\\\"\", $L))",fieldSymbol.name,fieldSymbol.name);
            builder.add("}\n");
        }
        builder.addStatement("sb.append('}')");
        builder.addStatement("return sb.toString()");
        return builder.build();
    }
 
}
