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

import com.srinivasa_refrigeration_works.srw.entity.Employee;
import com.srinivasa_refrigeration_works.srw.entity.UserCredential;
import com.srinivasa_refrigeration_works.srw.service.EmployeeService;
import com.srinivasa_refrigeration_works.srw.service.UserCredentialService;
import com.srinivasa_refrigeration_works.srw.utility.StringTrimmer;
import com.srinivasa_refrigeration_works.srw.wrapper.EmployeeCredentialWrapper;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/SRW")
public class EmployeeCredentialController {

    private final EmployeeService employeeService;
    private final UserCredentialService userCredentialService;
    private final StringTrimmer stringTrimmer;

    // Constructor to inject dependencies
    public EmployeeCredentialController(EmployeeService employeeService, UserCredentialService userCredentialService, StringTrimmer stringTrimmer) {
        this.employeeService = employeeService;
        this.userCredentialService = userCredentialService;
        this.stringTrimmer = stringTrimmer;
    }

    // Initialize custom binder for trimming string inputs
    @InitBinder
    public void initialize(WebDataBinder webDataBinder) {
        stringTrimmer.initBinder(webDataBinder);
    }

    // Displays the employee creation form
    @GetMapping("/employee-register")
    public String createEmployee(Model model) {
        EmployeeCredentialWrapper employeeCredential = new EmployeeCredentialWrapper();
        employeeCredential.setEmployee(new Employee());
        employeeCredential.setUserCredential(new UserCredential());
        model.addAttribute("employeeCredential", employeeCredential);
        return "employee/employee-register-form";
    }

    // Confirms employee details and processes the submission
    @PostMapping("/employee-confirmation")
    public String confirmEmployee(@ModelAttribute("employeeCredential") @Valid EmployeeCredentialWrapper employeeCredential, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "employee/employee-register-form"; // Return to form if there are errors
        }
        employeeService.addEmployee(employeeCredential.getEmployee()); // Save employee details
        String employeeId = employeeCredential.getEmployee().getEmployeeId(); // Get the employee's ID
        employeeCredential.getUserCredential().setUserId(employeeId); // Link employee ID to user credentials
        String phoneNumber = employeeCredential.getEmployee().getPhoneNumber(); // Get the employee's phone number
        employeeCredential.getUserCredential().setPhoneNumber(phoneNumber); // Set the user credential's phone number
        userCredentialService.addEmployeeCredential(employeeCredential.getUserCredential()); // Save user credentials
        return "employee/employee-confirmation"; // Confirmation view after successful submission
    }
}
