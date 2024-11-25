package com.srinivasa_refrigeration_works.srw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/SRW") // Base URL for the controller
public class LoginController {

    // Maps the login page request
    @GetMapping("/login")
    public String loginPage() {
        return "login/custom-login"; // Returns the "custom-login" view
    }  

    // Maps the access-denied page request
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";  // Returns the access-denied" view to display when access is denied
    }

}

