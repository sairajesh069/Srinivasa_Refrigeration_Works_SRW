package com.srinivasa_refrigeration_works.srw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.srinivasa_refrigeration_works.srw.entity.Owner;
import com.srinivasa_refrigeration_works.srw.service.OwnerService;

@Controller // Marks this class as a Spring MVC controller
@RequestMapping("/SRW/owner") // Maps the base URL for all methods in this controller
public class OwnerController {

    private final OwnerService ownerService; // Service to handle Owner-related logic

    // Constructor-based dependency injection for the OwnerService
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // Handles the request to fetch all owners and display them in a list
    @GetMapping("/list") // Maps the GET request for "/list" endpoint
    public String getAllOwners(Model model) {
        List<Owner> owners = ownerService.getAllOwners(); // Fetch all owners from the service
        model.addAttribute("owners", owners); // Add the list of owners to the model
        return "owner/owner-list"; // Return the view for displaying the owner list
    }
}
