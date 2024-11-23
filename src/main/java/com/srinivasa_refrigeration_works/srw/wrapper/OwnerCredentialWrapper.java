package com.srinivasa_refrigeration_works.srw.wrapper;

import org.springframework.stereotype.Component;

import com.srinivasa_refrigeration_works.srw.entity.Owner;
import com.srinivasa_refrigeration_works.srw.entity.UserCredential;

import jakarta.validation.Valid;

@Component // Marks this class as a Spring-managed bean
public class OwnerCredentialWrapper {

    @Valid // Validates Owner object
    private Owner owner;

    @Valid // Validates UserCredential object
    private UserCredential userCredential;

    // Getter and Setter for Owner
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    // Getter and Setter for UserCredential
    public UserCredential getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    // Custom toString method for displaying Owner and UserCredential details
    @Override
    public String toString() {
        return "OwnerCredential: [Owner: " + owner + ", Credentials: " + userCredential + "]";
    }
}