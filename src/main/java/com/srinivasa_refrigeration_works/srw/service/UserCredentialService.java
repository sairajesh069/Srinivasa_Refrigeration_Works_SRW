package com.srinivasa_refrigeration_works.srw.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.srinivasa_refrigeration_works.srw.entity.UserCredential;
import com.srinivasa_refrigeration_works.srw.entity.UserCredential.UserType;
import com.srinivasa_refrigeration_works.srw.entity.UserRole;
import com.srinivasa_refrigeration_works.srw.repository.UserCredentialRepository;

@Service // Marks this class as a service to handle business logic related to user credentials
public class UserCredentialService {

    private final UserCredentialRepository userCredentialRepository; // Repository for interacting with UserCredential entity

    private final PasswordEncoder passwordEncoder; // Password encoder to handle encryption of passwords

    // Constructor injection for UserCredentialRepository and PasswordEncoder
    public UserCredentialService(UserCredentialRepository userCredentialRepository, PasswordEncoder passwordEncoder) {
        this.userCredentialRepository = userCredentialRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to save a user's credentials along with their role and user type
    public void saveCredential(UserCredential userCredential, UserType userType, String role) {
        userCredential.setUserType(userType); // Assign the user type (OWNER, EMPLOYEE, CUSTOMER)

        String encodedPassword = passwordEncoder.encode(userCredential.getPassword());
        userCredential.setPassword(encodedPassword); // Encrypt the user's password using bcrypt

        // Create a new UserRole and associate it with the UserCredential
        UserRole userRole = new UserRole(userCredential.getUsername(), role);
        userRole.setUserCredential(userCredential);
        userCredential.addUserRole(userRole); // Add the role to the user's credential

        userCredentialRepository.save(userCredential); // Save the user credentials to the database
    }

    // Helper methods to add credentials for specific user types
    public void addOwnerCredential(UserCredential userCredential) {
        saveCredential(userCredential, UserType.OWNER, "ROLE_OWNER"); // Assign the OWNER role
    }

    public void addEmployeeCredential(UserCredential userCredential) {
        saveCredential(userCredential, UserType.EMPLOYEE, "ROLE_EMPLOYEE"); // Assign the EMPLOYEE role
    }

    public void addCustomerCredential(UserCredential userCredential) {
        saveCredential(userCredential, UserType.CUSTOMER, "ROLE_CUSTOMER"); // Assign the CUSTOMER role
    }

    // Method to retrieve the username based on the user's phone number
    public String getUsernameByPhoneNumber(String phoneNumber) {
        String username = userCredentialRepository.findUsernameByPhoneNumber(phoneNumber);
        return username;
    }

    // Method to validate if a user exists with the given phone number and username
    public boolean validateUserByPhoneNumberAndUsername(String phoneNumber, String username) {
        return userCredentialRepository.existsByPhoneNumberAndUsername(phoneNumber, username);
    }

    // Method to update a user's password
    public void updatePassword(String username, String password) {
        password = passwordEncoder.encode(password); // Encrypt the new password
        userCredentialRepository.updatePassword(username, password); // Update the password in the database
    }
}