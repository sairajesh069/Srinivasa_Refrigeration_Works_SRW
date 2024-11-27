package com.srinivasa_refrigeration_works.srw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.srinivasa_refrigeration_works.srw.entity.UserCredential;
import com.srinivasa_refrigeration_works.srw.service.UserCredentialService;
import com.srinivasa_refrigeration_works.srw.utility.StringTrimmer;

@Controller
@RequestMapping("/SRW") // Defines the base URL for the controller's mappings
public class AccountRecoveryController {

    private final UserCredentialService userCredentialService; // Service for handling user credentials
    private final StringTrimmer stringTrimmer; // Utility to trim whitespace from input fields

    // Constructor injection for userCredentialService and stringTrimmer
    public AccountRecoveryController(UserCredentialService userCredentialService, StringTrimmer stringTrimmer) {
        this.userCredentialService = userCredentialService;
        this.stringTrimmer = stringTrimmer;
    }

    // Initializes the WebDataBinder to use the StringTrimmer utility
    @InitBinder
    public void initialize(WebDataBinder webDataBinder) {
        stringTrimmer.initBinder(webDataBinder);
    }

    // Displays the username recovery form
    @GetMapping("/username-recovery")
    public String getUsername(Model model) {
        model.addAttribute("userCredential", new UserCredential()); // Create an empty UserCredential object
        return "user-recovery/username-recovery"; // Return the view for username recovery
    }

    // Processes the username recovery form submission
    @PostMapping("/username-recovery")
    public String fetchUsername(UserCredential userCredential, BindingResult bindingResult, Model model) {
        String phoneNumber = userCredential.getPhoneNumber();

        // Validate the phone number format
        if (phoneNumber == null || phoneNumber.isEmpty() || !phoneNumber.matches("\\d{10}")) {
            bindingResult.rejectValue("phoneNumber", "error.phoneNumber", "Enter a valid 10-digit phone number.");
        }

        // If validation errors occur, return to the recovery form
        if (bindingResult.hasErrors()) {
            return "user-recovery/username-recovery";
        }

        phoneNumber = "+91" + phoneNumber; // Add country code
        String username = userCredentialService.getUsernameByPhoneNumber(phoneNumber); // Fetch username by phone number

        // Display error message if username is not found
        if (username == null || username.isEmpty()) {
            model.addAttribute("errorMessage", "No user found with the provided phone number.");
        } 
        else {
            model.addAttribute("username", username); // Display the found username
        }
        return "user-recovery/username-recovery"; // Return to the recovery form
    }

    // Displays the password reset form
    @GetMapping("/password-reset")
    public String resetPassword(Model model) {
        model.addAttribute("userCredential", new UserCredential()); // Create an empty UserCredential object
        model.addAttribute("isUserValidated", false); // Initially, user is not validated
        return "user-recovery/password-reset"; // Return the view for password reset
    }

    // Processes the password reset form submission
    @PostMapping("/password-reset")
    public String validateUser(UserCredential userCredential, BindingResult bindingResult, Model model) {
        String phoneNumber = userCredential.getPhoneNumber();

        // Validate the phone number format
        if (phoneNumber == null || phoneNumber.isEmpty() || !phoneNumber.matches("\\d{10}")) {
            bindingResult.rejectValue("phoneNumber", "error.phoneNumber", "Enter a valid 10-digit phone number.");
        }

        String username = userCredential.getUsername();
        
        // Validate the username format
        if (username == null || username.isEmpty() || username.length() < 6) {
            bindingResult.rejectValue("username", "error.username", "Enter a valid username.");
        }

        // If validation errors occur, return to the password reset form
        if (bindingResult.hasErrors()) {
            model.addAttribute("isUserValidated", false);
            return "user-recovery/password-reset";
        }

        phoneNumber = "+91" + phoneNumber; // Add country code
        boolean isUserValidated = userCredentialService.validateUserByPhoneNumberAndUsername(phoneNumber, username); // Validate the user

        // If validation fails, show an error message
        if (!isUserValidated) {
            model.addAttribute("errorMessage", "No user matches the provided details.");
            model.addAttribute("isUserValidated", false);
            return "user-recovery/password-reset";
        }   
        else {
            // If validation succeeds, prompt user to set a new password
            model.addAttribute("validationSuccess", "You have been successfully validated. Please set your new password.");
            model.addAttribute("isUserValidated", true);

            String password = userCredential.getPassword();
            
            // Validate the new password format
            if (password == null) {
                bindingResult.rejectValue("password", "error.password", "Set up your new password");
            }
            else if (password.isEmpty() || !password.matches("^[a-zA-Z0-9@.#$&_]{8,}$")) {
                bindingResult.rejectValue("password", "error.password", "Password must be at least 8 characters long and can include digits, letters, and special characters (. @ # $ & _) only");
            }

            String confirmPassword = userCredential.getConfirmPassword();
            
            // Validate if the password and confirm password match
            if (confirmPassword == null) {
                bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Confirm that the passwords match");
            }
            else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
                bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "The passwords don't match. Please try again.");
            }

            // If validation errors occur, return to the password reset form
            if (bindingResult.hasErrors()) {
                return "user-recovery/password-reset";
            }
            else {
                userCredentialService.updatePassword(username, password); // Update the password in the database
                model.addAttribute("validationSuccess", "");
                model.addAttribute("passwordUpdated", "Your password has been successfully updated.");
                model.addAttribute("isUserValidated", false);
                return "user-recovery/password-reset"; // Return to the password reset page
            }
        }
    }
}
