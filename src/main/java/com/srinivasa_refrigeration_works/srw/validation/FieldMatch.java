package com.srinivasa_refrigeration_works.srw.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Custom annotation to validate that two fields match
@Constraint(validatedBy = FieldMatchConstraintValidator.class) // Specifies the validator class to use
@Target({ElementType.TYPE}) // Can be applied at the class level
@Retention(RetentionPolicy.RUNTIME) // Retained at runtime for reflection
public @interface FieldMatch {

    // Default error message if validation fails
    public String message() default "Fields did not match."; 

    // Groups for categorizing constraints (not often used)
    public Class<?>[] groups() default {}; 

    // Custom payload for additional data during validation
    public Class<? extends Payload>[] payload() default {}; 

    // Specifies the first field to compare
    String firstField();

    // Specifies the second field to compare
    String secondField();
}