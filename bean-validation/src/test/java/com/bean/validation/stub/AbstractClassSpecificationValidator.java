package com.bean.validation.stub;

import com.bean.validation.ConstraintDefine;
import com.bean.validation.BeanConstraintValidator;
import com.bean.validation.SpecificationValidator;
import com.bean.validation.SpecificationValidatorHelper;
import com.bean.validation.ValidCondition;
import com.bean.validation.ValidationContext;
import com.bean.validation.ValueContext;

import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;

import com.bean.model.element.ClassSymbolImpl;
import com.bean.runtime.Point;

public class AbstractClassSpecificationValidator implements AbstractClassSpecification {

    private SpecificationValidatorHelper helper;
    private ConstraintDefine<Name> simpleNameNotNullDefine;
    private NotNullValidator simpleNameNotNullValidator;

    final class NotNullDefine implements ConstraintDefine<Name>, Annotation,NotNull{

        @Override
        public Class<?>[] groups() {
            return null;
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return null;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return NotEmpty.class;
        }

        @Override
        public String message() {
            return "不能为空";
        }

        @Override
        public Class<Name> getTargetType() {
            return Name.class;
        }

        @Override
        public String code() {
            return NotNull.class.getName();
        }
    }

    private ValidCondition<Name>[] simpleNameNotEmptyConditon;

    private BeanConstraintValidator<List<Element>>[] enclosedElementsNotEmpty;
    private ValidCondition<Element>[] enclosedElementsFilter_1;
    private SpecificationValidator<Element> enclosedElementsValidator_1;

    public AbstractClassSpecificationValidator() {
        simpleNameNotEmptyValidator();
    }

    private void simpleNameNotEmptyValidator() {
        ConstraintDefine<Name>  simpleNameNotNullDefine = new NotNullDefine();
        NotNullValidator validator = new NotNullValidator();
        validator.initialize(simpleNameNotNullDefine);
        this.simpleNameNotNullValidator = validator;
    }

    @Override
    public void validateSpecification(ValidationContext<?> validationContext,
            ValueContext<ClassSymbolImpl> beanContext) {
        simpleName(validationContext, beanContext);
        // validate properties

    }

    @Override
    public void simpleName() {

    }

    public void simpleName(ValidationContext<?> validationContext,
            ValueContext<ClassSymbolImpl> containerContext) {
        Point propertyPath = containerContext.getPath().creatProperty("simpleName");
        ValueContext<Name> propertyValueContext = helper.buildValueContext(validationContext, containerContext,
                propertyPath, containerContext.getCurrentValue().getSimpleName());
        boolean isValidationRequired = helper.isValidationRequired(propertyValueContext, simpleNameNotEmptyConditon);
        
        if (isValidationRequired) {
            helper.validateMetaConstraint(validationContext, propertyValueContext, simpleNameNotNullDefine,
            simpleNameNotNullValidator);
        }
    }

    @Override
    public void enclosedElements() {
    }

    public void enclosedElements(ValidationContext<?> validationContext,
            ValueContext<ClassSymbolImpl> containerContext) {

        // validate Iterable
        Point propertyPath = containerContext.getPath().creatProperty("enclosedElements");
        ValueContext<List<Element>> propertyValueContext = helper.buildValueContext(validationContext, containerContext,
                propertyPath, containerContext.getCurrentValue().getEnclosedElements());
        boolean isValidationRequired = false;
        Iterable<Element> enclosedElements = containerContext.getCurrentValue().getEnclosedElements();
        int i = 0;
        if (enclosedElements != null) {
            for (Element element : enclosedElements) {
                Point elementPath = propertyPath.createIndexableElement(i++);
                ValueContext<Element> elementValueContext = helper.buildValueContext(validationContext,
                        propertyValueContext, elementPath, element);
                isValidationRequired = helper.isValidationRequired(elementValueContext, enclosedElementsFilter_1);
                if (isValidationRequired) {
                    helper.validateMetaConstraint(validationContext, elementValueContext, enclosedElementsValidator_1);
                }
            }
        }
    }
}
