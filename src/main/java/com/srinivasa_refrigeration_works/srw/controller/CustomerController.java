package com.srinivasa_refrigeration_works.srw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.srinivasa_refrigeration_works.srw.entity.Customer;
import com.srinivasa_refrigeration_works.srw.service.CustomerService;

@Controller // Marks this class as a Spring MVC controller
@RequestMapping("/SRW/customer") // Base URL mapping for customer-related operations
public class CustomerController {

    private final CustomerService customerService; // Service to manage customer-related logic

    // Constructor-based dependency injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list") // Handles requests to fetch a list of customers
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers(); // Fetch all customers from the service
        if(customers.isEmpty()) { // Check if the customer list is empty
            model.addAttribute("noUsersFound", "No Customer Entries in Database"); // Add message if no customers are found
        } else {
            model.addAttribute("customers", customers); // Add the list of customers to the model
        }
        return "customer/customer-list"; // Return the view for displaying customer data
    }
}