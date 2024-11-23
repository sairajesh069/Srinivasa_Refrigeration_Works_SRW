package com.srinivasa_refrigeration_works.srw.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Custom annotation for validating unique values in a database
@Constraint(validatedBy = UniqueValueConstraintValidator.class) // Specifies the validator class
@Target({ElementType.METHOD, ElementType.FIELD}) // Can be used on methods or fields
@Retention(RetentionPolicy.RUNTIME) // Retained at runtime for reflection
public @interface UniqueValue {

    // Default error message if validation fails
    public String message() default "Value already exists."; 

    // Groups for categorizing constraints (not often used)
    public Class<?>[] groups() default {}; 

    // Custom payload for additional data during validation
    public Class<? extends Payload>[] payload() default {}; 

    // Specifies the field name to check for uniqueness
    String fieldName();

    // Specifies the entity class to validate against
    Class<?> entityClass();
}