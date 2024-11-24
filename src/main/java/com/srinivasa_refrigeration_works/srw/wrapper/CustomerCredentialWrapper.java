package com.srinivasa_refrigeration_works.srw.wrapper;

import org.springframework.stereotype.Component;

import com.srinivasa_refrigeration_works.srw.entity.Customer;
import com.srinivasa_refrigeration_works.srw.entity.UserCredential;

import jakarta.validation.Valid;

// Wrapper component to bind Customer and UserCredential objects for validation and processing
@Component
public class CustomerCredentialWrapper {

    // Customer entity with validation constraints
    @Valid
    private Customer customer;

    // UserCredential entity with validation constraints
    @Valid
    private UserCredential userCredential;

    // Getter for the Customer object
    public Customer getCustomer() {
        return customer;
    }

    // Setter for the Customer object
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Getter for the UserCredential object
    public UserCredential getUserCredential() {
        return userCredential;
    }

    // Setter for the UserCredential object
    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    // Returns a string representation of the wrapper for logging or debugging
    @Override
    public String toString() {
        return "Customer Credentials: [Customer: " + customer + " , User Credentials: " + userCredential + "]";
    }
}
