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

import com.srinivasa_refrigeration_works.srw.entity.Owner;
import com.srinivasa_refrigeration_works.srw.entity.UserCredential;
import com.srinivasa_refrigeration_works.srw.service.OwnerService;
import com.srinivasa_refrigeration_works.srw.service.UserCredentialService;
import com.srinivasa_refrigeration_works.srw.utility.StringTrimmer;
import com.srinivasa_refrigeration_works.srw.wrapper.OwnerCredentialWrapper;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/SRW") // Maps all methods in this controller to /SRW path
public class OwnerCredentialController {

    private final OwnerService ownerService; // Service for handling Owner operations
    private final UserCredentialService userCredentialService; // Service for handling UserCredential operations
    private final StringTrimmer stringTrimmer; // Utility for trimming strings

    // Constructor injection for required services
    public OwnerCredentialController(OwnerService ownerService, UserCredentialService userCredentialService, StringTrimmer stringTrimmer) {
        this.ownerService = ownerService;
        this.userCredentialService = userCredentialService;
        this.stringTrimmer = stringTrimmer;
    }

    @InitBinder
    public void initialize(WebDataBinder webDataBinder) {
        stringTrimmer.initBinder(webDataBinder); // Trims strings to remove leading/trailing spaces
    }

    @GetMapping("/owner-register") // Displays the form for creating an owner
    public String createOwner(Model model) {
        OwnerCredentialWrapper ownerCredential = new OwnerCredentialWrapper(); // Initialize the wrapper object
        ownerCredential.setOwner(new Owner()); // Set up a new Owner entity
        ownerCredential.setUserCredential(new UserCredential()); // Set up a new UserCredential entity
        model.addAttribute("ownerCredential", ownerCredential); // Add to model for use in the view
        return "owner-creation-form"; // Returns the view name for the form
    }

    @PostMapping("/owner-confirmation") // Processes the owner and credential form submission
    public String confirmOwner(@ModelAttribute("ownerCredential") @Valid OwnerCredentialWrapper ownerCredential, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // If validation errors exist, show the form again
            return "owner/owner-creation-form";
        }
        ownerService.addOwner(ownerCredential.getOwner()); // Add the Owner to the database
        String ownerId = ownerCredential.getOwner().getOwnerId(); // Get the owner's ID
        ownerCredential.getUserCredential().setUserId(ownerId); // Set the user credential's user ID
        userCredentialService.addOwnerCredential(ownerCredential.getUserCredential()); // Add UserCredential to the database
        return "owner/owner-confirmation"; // Return confirmation view after successful submission
    }
}
