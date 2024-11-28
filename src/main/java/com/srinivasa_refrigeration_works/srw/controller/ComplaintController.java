package com.srinivasa_refrigeration_works.srw.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.srinivasa_refrigeration_works.srw.entity.Complaint;
import com.srinivasa_refrigeration_works.srw.service.ComplaintService;
import com.srinivasa_refrigeration_works.srw.service.UserCredentialService;
import com.srinivasa_refrigeration_works.srw.utility.StringTrimmer;

import jakarta.validation.Valid;

@Controller // Marks this class as a controller for Spring MVC
@RequestMapping("/SRW") // Maps the base URL for all methods in this controller
public class ComplaintController {

    private final ComplaintService complaintService; // Service to handle complaint logic
    private final UserCredentialService userCredentialService; // Service to handle UserCdredential logic
    private final StringTrimmer stringTrimmer; // Utility for trimming string values

    // Constructor-based dependency injection for services
    public ComplaintController(ComplaintService complaintService, UserCredentialService userCredentialService, StringTrimmer stringTrimmer) {
        this.complaintService = complaintService;
        this.userCredentialService = userCredentialService;
        this.stringTrimmer = stringTrimmer;
    }

    @InitBinder // Initializes WebDataBinder to trim string values in form fields
    public void initBinder(WebDataBinder webDataBinder) {
        stringTrimmer.initBinder(webDataBinder);
    }

    // Injects service types, air conditioner and refrigerator details from application properties
    @Value("${serviceTypes}")
    private List<String> serviceTypes;

    @Value("${airConditionerBrands}")
    private List<String> airConditionerBrands;

    @Value("${airConditionerModels}")
    private List<String> airConditionerModels;

    @Value("${refrigeratorBrands}")
    private List<String> refrigeratorBrands;

    @Value("${refrigeratorModels}")
    private List<String> refrigeratorModels;

    // Populates dropdown lists based on selected service type
    public void populateDropDowns(String serviceType, Model model) {
        model.addAttribute("serviceTypes", serviceTypes); // Add service types to model

        // Based on the service type, populate the brands and models
        switch(serviceType) {
            case "Refrigerator" -> { // If service type is Refrigerator
                model.addAttribute("brands", refrigeratorBrands); // Add refrigerator brands
                model.addAttribute("models", refrigeratorModels); // Add refrigerator models
            }
            case "Air Conditioner" -> { // If service type is Air Conditioner
                model.addAttribute("brands", airConditionerBrands); // Add AC brands
                model.addAttribute("models", airConditionerModels); // Add AC models
            }
            case "Other" -> { // If service type is Other
                model.addAttribute("brands", List.of("Other")); // Add "Other" as the brand
                model.addAttribute("models", List.of("Other")); // Add "Other" as the model
            }
            default -> { // If no valid service type is selected
                model.addAttribute("brands", List.of()); // Add empty list of brands
                model.addAttribute("models", List.of()); // Add empty list of models
            }
        }
    }

    // Display the complaint registration form with service details
    @GetMapping("/complaint-register")
    public String bookRepair(Model model) {
        model.addAttribute("complaint", new Complaint()); // Initialize an empty complaint object
        model.addAttribute("serviceTypes", serviceTypes); // Add service types
        return "complaint/complaint-register-form"; // Return view for complaint registration form
    }

    // Handle the form submission and validate the complaint details
    @PostMapping("/complaint-confirmation")
    public String registerComplaint(@ModelAttribute("complaint") @Valid Complaint complaint, BindingResult bindingResult, Model model, Principal principal) {
        if(bindingResult.hasErrors()) { // Check if there are validation errors in the form
            if(complaint.getServiceType() != null) { // If service type is selected
                populateDropDowns(complaint.getServiceType(), model); // Populate dropdowns based on service type
            }
            else { // If no service type is selected
                model.addAttribute("serviceTypes", serviceTypes); // Add service types to model
            }
            return "complaint/complaint-register-form"; // Return the form view again in case of errors
        }
        String username = principal.getName(); // Get the username of the currently authenticated user
        String userId = userCredentialService.getUserIdByUsername(username); // Get user ID from the username
        complaint.setBookedById(userId); // Set the user ID in the complaint
        complaintService.registerComplaint(complaint); // Call service to register the complaint
        return "complaint/complaint-confirmation"; // Return the confirmation view after successful registration
    }

    // Update the dropdowns based on the selected service type
    @PostMapping("/update-dropdown")
    public String bookRepair(Complaint complaint, Model model) {
        String serviceType = complaint.getServiceType(); // Get the selected service type
        populateDropDowns(serviceType, model); // Populate dropdowns based on service type
        return "complaint/complaint-register-form"; // Return the form view with updated dropdowns
    }

    // Fetches and displays complaints registered by the current user
    @GetMapping("/complaint-status")
    public String complaintStatus(Model model, Principal principal) {
        String userId = userCredentialService.getUserIdByUsername(principal.getName()); // Get userId from username
        List<Complaint> complaints = complaintService.getComplaintsByUserId(userId); // Retrieve complaints by userId
        if(complaints.isEmpty()) { // If no complaints are found, show a message
            model.addAttribute("noRecordsFound", "You have not registered any complaints.");
        }
        else { // If complaints are found, add them to the model
            model.addAttribute("complaints", complaints); // Add complaints to the model
        }
        return "complaint/complaint-status"; // Return the view for complaint status
    }
    
}
