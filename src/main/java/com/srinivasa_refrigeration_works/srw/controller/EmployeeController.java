package com.srinivasa_refrigeration_works.srw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.srinivasa_refrigeration_works.srw.entity.Employee;
import com.srinivasa_refrigeration_works.srw.service.EmployeeService;

@Controller // Marks this class as a Spring MVC controller
@RequestMapping("/SRW/employee") // Maps the base URL for all methods in this controller
public class EmployeeController {

    private final EmployeeService employeeService; // Service to handle Employee-related logic

    // Constructor-based dependency injection for the EmployeeService
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Handles the request to fetch all employees and display them in a list
    @GetMapping("/list") // Maps the GET request for "/list" endpoint
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees(); // Fetch all employees from the service
        model.addAttribute("employees", employees); // Add the list of employees to the model
        return "employee/employee-list"; // Return the view for displaying the employee list
    }
}

