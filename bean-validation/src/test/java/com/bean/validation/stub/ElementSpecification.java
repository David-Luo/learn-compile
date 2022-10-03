package com.bean.validation.stub;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import com.bean.validation.ValidCondition;
import com.bean.validation.SpecificationValidator;

public interface ElementSpecification  extends SpecificationValidator<Element>{
    
    public class MethodCondition implements ValidCondition<Element>{
        public boolean test(Element t){
            return t.getKind() == ElementKind.METHOD;
        }
    }
    public class FieldCondition implements ValidCondition<Element>{
        public boolean test(Element t){
            return t.getKind() == ElementKind.FIELD;
        }
    }
}
