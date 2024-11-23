package com.srinivasa_refrigeration_works.srw.utility;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Component // Marks this class as a Spring-managed bean
public class StringTrimmer {

    @InitBinder // Registers custom editors for binding form data
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true); // Trims leading/trailing whitespace for String fields
        webDataBinder.registerCustomEditor(String.class, trimmerEditor); // Applies the String trimmer to all String fields
    }
}
