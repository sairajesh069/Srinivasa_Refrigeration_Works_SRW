package com.srinivasa_refrigeration_works.srw.validation;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class FieldMatchConstraintValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstField;
    private String secondField;
    private String message;

    @Override
    public void initialize(FieldMatch fieldMatch) {
        this.firstField = fieldMatch.firstField();
        this.secondField = fieldMatch.secondField();
        this.message = fieldMatch.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value==null) {
            return true;
        }
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        String firstValue = (String) beanWrapper.getPropertyValue(firstField);
        String secondValue = (String) beanWrapper.getPropertyValue(secondField);
        if(firstValue != null && secondValue != null && firstValue.equals(secondValue)) {
            return true;
        }
        else{
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(secondField).addConstraintViolation();
            return false;
        }

    }

}