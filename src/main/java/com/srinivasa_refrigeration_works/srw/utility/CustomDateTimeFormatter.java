package com.srinivasa_refrigeration_works.srw.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Utility for date-time formatting
public class CustomDateTimeFormatter {

    // Returns the current date-time in "dd-MM-yyyy HH:mm:ss" format.

    public static String formattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
