package com.srinivasa_refrigeration_works.srw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Redirects to the home page
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/SRW/home"; // Redirects to "/SRW/home"
    }

    // Displays the home view
    @GetMapping("/SRW/home")
    public String home() {
        return "home"; // Returns the "home" view
    }    
    
}
