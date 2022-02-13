package com.bean.validation.stub;

import com.bean.model.element.MethodSymbolImpl;
import com.bean.validation.AnnotationUtils;
import com.bean.validation.ValidationSpec;
import java.lang.Object;
import java.lang.Override;
import java.util.Set;
import javax.lang.model.element.Modifier;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.NotEmpty;

public class MethodInfoValidationProcess implements MethodInfoValidationSpec {
  private static final ConstraintValidator modifiersNotEmpty = AnnotationUtils.initConstraintValidator(null,"com.bean.validation.stub.MethodInfoValidationSpecImpl","_modifiersNotEmpty");

  private static final ConstraintValidator modifiersSubsetOf = AnnotationUtils.initConstraintValidator("com.bean.validation.stub.ModifierValidation","com.bean.validation.stub.MethodInfoValidationSpecImpl","_modifiersSubsetOf");

  @NotEmpty
  private Object _modifiersNotEmpty;

  @SubsetOf({
      Modifier.STATIC,
      Modifier.PUBLIC
  })
  private Object _modifiersSubsetOf;

  @Override
  public void initialize(ValidationSpec arg0) {
  }

  @Override
  public boolean modifiers(Set<Modifier> value, ConstraintValidatorContext context) {
    boolean temp,result = true;
    temp = modifiersNotEmpty.isValid(value, context);
    result = result && temp;
    temp = modifiersSubsetOf.isValid(value, context);
    result = result && temp;
    return result;
  }

  private boolean _isValid(MethodSymbolImpl arg0, ConstraintValidatorContext arg1) {
    boolean temp,result = true;
    return result;
  }

  private boolean _children(MethodSymbolImpl arg0, ConstraintValidatorContext arg1) {
    boolean temp,result = true;
    ConstraintValidatorContext context = arg1;
    temp = modifiers(arg0.getModifiers(), context);
    result = result && temp;
    return true;
  }

  @Override
  public boolean isValid(MethodSymbolImpl arg0, ConstraintValidatorContext arg1) {
    boolean temp,result = true;
    temp = _isValid(arg0, arg1);
    result = result && temp;
    temp = _children(arg0, arg1);
    result = result && temp;
    return true;
  }
}
