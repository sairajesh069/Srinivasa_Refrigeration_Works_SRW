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
    private boolean inEveryUserEntity; // Flag to determine cross-entity validation

    @PersistenceContext // Injects the EntityManager to interact with the database
    private EntityManager entityManager;

    // Initializes the validator with the field name and entity class from the annotation
    @Override
    public void initialize(UniqueValue uniqueValue) {
        this.fieldName = uniqueValue.fieldName();
        this.entityClass = uniqueValue.entityClass();
        this.inEveryUserEntity = uniqueValue.inEveryUserEntity();
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

        // Query to check across all user-related entities if applicable
        String everyUserQuery = """
                                SELECT COUNT(e.dynamic_field) 
                                FROM (
                                    SELECT %s AS dynamic_field FROM Owner
                                    UNION
                                    SELECT %s AS dynamic_field FROM Employee
                                    UNION
                                    SELECT %s AS dynamic_field FROM Customer
                                ) e
                                WHERE e.dynamic_field = :value
                            """.formatted(fieldName, fieldName, fieldName);

        // Query to check within a single entity
        String singleUserQuery = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value";

        // Choose query based on the inEveryUserEntity flag
        String query = inEveryUserEntity ? everyUserQuery : singleUserQuery;
        
        // Executes the query and checks if any record exists with the same value
        Long count = entityManager
                            .createQuery(query, Long.class)
                            .setParameter("value", value)
                            .getSingleResult();

        return count == 0; // Returns true if no record found, indicating uniqueness
    }
}