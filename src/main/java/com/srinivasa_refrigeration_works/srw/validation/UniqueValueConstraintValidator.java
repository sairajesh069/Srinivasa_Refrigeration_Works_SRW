package com.srinivasa_refrigeration_works.srw.validation;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component // Indicates that this is a Spring-managed component
public class UniqueValueConstraintValidator implements ConstraintValidator<UniqueValue, Object> {

    private String fieldName; // Field to be validated
    private Class<?> entityClass; // Entity class for the validation

    @PersistenceContext // Injects the EntityManager to interact with the database
    private EntityManager entityManager;

    // Initializes the validator with the field name and entity class from the annotation
    @Override
    public void initialize(UniqueValue uniqueValue) {
        this.fieldName = uniqueValue.fieldName();
        this.entityClass = uniqueValue.entityClass();
    }

    // Validates that the value of the field is unique in the database
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null || entityManager == null) {
            // TODO: Fix bug - 'entityManager' becomes null during persistence. 
            // The issue arises because 'entityManager' is not considered a Spring bean by Hibernate Validator.
            // Investigate and resolve to ensure 'entityManager' is properly injected as a Spring bean.
            return true; // If value is null validation is considered successful
        }

        // For phone number, prepend country code if not already present
        if(fieldName.equals("phoneNumber")) {
            value = value.toString().startsWith("+91") ? value : "+91" + value;
        }

        // Constructs a query to count records with the same value in the specified field
        String query = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value";
        
        // Executes the query and checks if any record exists with the same value
        Long count = entityManager
                            .createQuery(query, Long.class)
                            .setParameter("value", value)
                            .getSingleResult();

        return count == 0; // Returns true if no record found, indicating uniqueness
    }
}