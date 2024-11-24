package com.srinivasa_refrigeration_works.srw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.srinivasa_refrigeration_works.srw.entity.Customer;
import com.srinivasa_refrigeration_works.srw.entity.UserCredential;
import com.srinivasa_refrigeration_works.srw.service.CustomerService;
import com.srinivasa_refrigeration_works.srw.service.UserCredentialService;
import com.srinivasa_refrigeration_works.srw.utility.StringTrimmer;
import com.srinivasa_refrigeration_works.srw.wrapper.CustomerCredentialWrapper;

import jakarta.validation.Valid;

// Controller to manage customer registration and credential creation
@Controller
@RequestMapping("/SRW")
public class CustomerCredentialController {

    // Service for customer-related operations
    private final CustomerService customerService;

    // Service for user credential-related operations
    private final UserCredentialService userCredentialService;

    // Utility to trim strings (e.g., for removing leading/trailing spaces)
    private final StringTrimmer stringTrimmer;

    // Constructor for dependency injection
    public CustomerCredentialController(CustomerService customerService, UserCredentialService userCredentialService, StringTrimmer stringTrimmer) {
        this.customerService = customerService;
        this.userCredentialService = userCredentialService;
        this.stringTrimmer = stringTrimmer;
    }

    // Initialize the string trimmer for all incoming requests
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        stringTrimmer.initBinder(webDataBinder);
    }

    // Display the customer registration form
    @GetMapping("/customer-register")
    public String createCustomer(Model model) {
        // Initialize the wrapper for customer and user credentials
        CustomerCredentialWrapper customerCredential = new CustomerCredentialWrapper();
        customerCredential.setCustomer(new Customer());
        customerCredential.setUserCredential(new UserCredential());
        
        // Add the wrapper as an attribute to the model for the view
        model.addAttribute("customerCredential", customerCredential);
        
        // Return the registration form view
        return "customer/customer-creation-form";
    }

    // Handle customer form submission and create customer with credentials
    @PostMapping("/customer-confirmation")
    public String createCustomer(@ModelAttribute("customerCredential") @Valid CustomerCredentialWrapper customerCredential, BindingResult bindingResult) {
        // Check for validation errors
        if(bindingResult.hasErrors()) {
            return "customer/customer-creation-form"; // Return to form if there are errors
        }

        // Add customer to the database
        customerService.addCustomer(customerCredential.getCustomer());

        // Set the generated customer ID
        String customerId = customerCredential.getCustomer().getCustomerId();
        customerCredential.getUserCredential().setUserId(customerId);

        // Use phone number as the password for the user credential
        String phoneNumber = customerCredential.getCustomer().getPhoneNumber();
        customerCredential.getUserCredential().setPassword(phoneNumber);

        // Add user credential to the database
        userCredentialService.addCustomerCredential(customerCredential.getUserCredential());

        // Return the confirmation page after successful registration
        return "customer/customer-confirmation";
    }
}