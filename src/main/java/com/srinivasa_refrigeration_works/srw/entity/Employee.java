package com.srinivasa_refrigeration_works.srw.entity;

import static com.srinivasa_refrigeration_works.srw.utility.CustomDateTimeFormatter.formattedDateTime;
import com.srinivasa_refrigeration_works.srw.validation.UniqueValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees") // Maps this entity to the "employees" table in the database
@Data
@NoArgsConstructor
public class Employee {

    // Auto-generated primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_reference")
    private int employeeReference;

    // First name (required)
    @NotNull(message = "First name is required.")
    @Column(name = "first_name")
    private String firstName;

    // Last name (required)
    @NotNull(message = "Last name is required.")
    @Column(name = "last_name")
    private String lastName;

    // Phone number with validation for format and uniqueness
    @NotNull(message = "Phone number is required.")
    @Pattern(regexp = "^[0-9+]{10,13}$", message = "Please enter a valid phone number.")
    @UniqueValue(fieldName = "phoneNumber", entityClass = Employee.class, inEveryUserEntity=true, message = "Phone number already in use.") // Ensures the phone number is unique across all user-related entities
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    // Email address with validation for format and uniqueness
    @NotNull(message = "Email is required.")
    @Email(message = "Please enter a valid email address.")
    @UniqueValue(fieldName = "email", entityClass = Employee.class, inEveryUserEntity=true, message = "Email already in use.") // Ensures the email is unique across all user-related entities
    @Column(name = "email", unique = true)
    private String email;

    // Address (required)
    @NotNull(message = "Address is required.")
    @Column(name = "address")
    private String address;

    // Employee ID (auto-assigned after creation)
    @Column(name = "employee_id")
    private String employeeId;

    // Job designation
    @Column(name = "designation")
    private String designation;

    // Hire date (set automatically)
    @Column(name = "date_of_hire")
    private final String dateOfHire = formattedDateTime();

    // Employee salary
    @Column(name = "salary")
    private Long salary;

    // Exit date (optional)
    @Column(name = "date_of_exit")
    private String dateOfExit;

    // Constructor for creating an employee with required fields
    public Employee(String firstName, String lastName, String phoneNumber, String email, String address, Long salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.salary = salary;
    }
}