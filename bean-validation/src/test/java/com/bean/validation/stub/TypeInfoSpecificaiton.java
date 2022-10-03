package com.bean.validation.stub;

import javax.lang.model.element.ElementKind;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.validation.IterableElementSpec;
import com.bean.validation.ValidCondition;
import com.bean.validation.ValidationSpec;
import com.bean.validation.SpecificationValidator;
import com.bean.validation.stub.ElementSpecification.FieldCondition;
import com.bean.validation.stub.ElementSpecification.MethodCondition;
import com.bean.validation.stub.TypeInfoSpecificaiton.*;

import jakarta.validation.constraints.NotNull;

@ValidationSpec(value = ClassSpecification.class, groups = ClassCondition.class)
@ValidationSpec(value = InterfaceSpecification.class, groups = InterfaceCondition.class)
public interface TypeInfoSpecificaiton extends SpecificationValidator<ClassSymbolImpl>{
    
    @NotNull(groups = NotAnonymous.class)
    void simpleName();

    public class ClassCondition implements ValidCondition<ClassSymbolImpl>{
        public boolean test(ClassSymbolImpl t){
            return t.getKind() == ElementKind.CLASS;
        }
    }

    public class InterfaceCondition implements ValidCondition<ClassSymbolImpl>{
        public boolean test(ClassSymbolImpl t){
            return t.getKind() == ElementKind.INTERFACE;
        }
    }

    public class NotAnonymous implements  ValidCondition<ClassSymbolImpl>{
        public boolean test(ClassSymbolImpl t){
            //FIXME
            return true;
        }
    }

}
