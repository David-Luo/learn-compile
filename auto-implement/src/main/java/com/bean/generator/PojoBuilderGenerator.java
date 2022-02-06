package com.bean.generator;

import java.util.List;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.*;

public class PojoBuilderGenerator {
    
    public void createBuilder(ClassName outerClass,TypeSpec.Builder outerClassBuilder) {
        ClassName innerclass = outerClass.nestedClass("Builder");
        TypeSpec.Builder inner = TypeSpec.classBuilder(innerclass);
        inner.addModifiers(Modifier.PUBLIC,Modifier.STATIC);
        FieldSpec fieldSpec = FieldSpec.builder(outerClass, "target", Modifier.PRIVATE).build();
        inner.addField(fieldSpec);

        inner.addMethod(buildconstructor(outerClass));
        inner.addMethod(buildCreate(outerClass));
        
        List<FieldSpec> fields = outerClassBuilder.fieldSpecs;
        for (FieldSpec fieldSymbol : fields) {
            inner.addMethod(buildSetter(innerclass, fieldSymbol));
        }

        outerClassBuilder.addMethod(buildStatic(outerClass, innerclass));
        outerClassBuilder.addType(inner.build());
    }

    private MethodSpec buildStatic(ClassName outerClass,ClassName innerclass){
        MethodSpec.Builder builder =  MethodSpec.methodBuilder("builder");
        builder.addModifiers(Modifier.STATIC,Modifier.PUBLIC);
        builder.addCode("return new $L.Builder();",outerClass.simpleName());
        builder.returns(innerclass);
        return builder.build();
    }

    private MethodSpec buildSetter(ClassName innerclass, FieldSpec field) {
        String fieldName = field.name;
        MethodSpec.Builder builder = MethodSpec.methodBuilder(field.name);
        builder.addModifiers(Modifier.PUBLIC);
        builder.addCode("target." + fieldName + "=" + fieldName + ";\n return this;");
        ParameterSpec parameterSpec = ParameterSpec.builder(field.type, fieldName).build();
        builder.addParameter(parameterSpec);
        builder.returns(innerclass);
        return builder.build();
    }

    private MethodSpec buildconstructor(ClassName outerClass){
        MethodSpec.Builder builder =  MethodSpec.constructorBuilder();
        builder.addCode("this.target = new $L();",outerClass.simpleName());
        return builder.build();
    }

    private MethodSpec buildCreate(ClassName outer){
        MethodSpec.Builder builder =  MethodSpec.methodBuilder("build");
        builder.addCode("return this.target;");
        builder.returns(outer);
        builder.addModifiers(Modifier.PUBLIC);
        return builder.build();
    }
}
