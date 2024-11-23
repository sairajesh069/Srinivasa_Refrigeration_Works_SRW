package com.srinivasa_refrigeration_works.srw.service;

import org.springframework.stereotype.Service;

import com.srinivasa_refrigeration_works.srw.entity.UserCredential;
import com.srinivasa_refrigeration_works.srw.entity.UserCredential.UserType;
import com.srinivasa_refrigeration_works.srw.entity.UserRole;
import com.srinivasa_refrigeration_works.srw.repository.UserCredentialRepository;

@Service // Marks this class as a service for business logic
public class UserCredentialService {

    private final UserCredentialRepository userCredentialRepository; // Repository for UserCredential entity

    // Constructor injection for UserCredentialRepository
    public UserCredentialService(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

    // Method to save a user's credentials with role and user type
    public void saveCredential(UserCredential userCredential, UserType userType, String role) {
        userCredential.setUserType(userType); // Set the user type (OWNER, EMPLOYEE, CUSTOMER)
        userCredential.setPassword("{noop}" + userCredential.getPassword()); // Set password with no encryption (noop)
        
        // Create a new UserRole and associate it with the UserCredential
        UserRole userRole = new UserRole(userCredential.getUsername(), role);
        userRole.setUserCredential(userCredential);
        userCredential.addUserRole(userRole); // Add the role to the user

        userCredentialRepository.save(userCredential); // Save the user credentials
    }

    // Helper methods for adding credentials for different user types
    public void addOwnerCredential(UserCredential userCredential) {
        saveCredential(userCredential, UserType.OWNER, "ROLE_OWNER"); // Assign OWNER role
    }

    public void addEmployeeCredential(UserCredential userCredential) {
        saveCredential(userCredential, UserType.EMPLOYEE, "ROLE_EMPLOYEE"); // Assign EMPLOYEE role
    }
}
