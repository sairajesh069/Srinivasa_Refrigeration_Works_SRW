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

// Entity class representing an Employee
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
public class Employee {

    // Primary key with auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_reference")
    private int employeeReference;

    // First name of the employee (required field)
    @NotNull(message = "is required")
    @Column(name = "first_name")
    private String firstName;

    // Last name of the employee (required field)
    @NotNull(message = "is required")
    @Column(name = "last_name")
    private String lastName;

    // Phone number with validation and uniqueness constraints
    @NotNull(message = "is required")
    @Pattern(regexp = "^[0-9+]{10,13}$", message = "enter a valid phone number")
    @UniqueValue(fieldName = "phoneNumber", entityClass = Employee.class, message = "phone number already exists")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    // Email address with validation and uniqueness constraints
    @NotNull(message = "is required")
    @Email(message = "enter a valid email")
    @UniqueValue(fieldName = "email", entityClass = Employee.class, message = "email already exists")
    @Column(name = "email", unique = true)
    private String email;

    // Address of the employee (required field)
    @NotNull(message = "is required")
    @Column(name = "address")
    private String address;

    // Unique identifier for the employee
    @Column(name = "employee_id")
    private String employeeId;

    // Designation or role of the employee
    @Column(name = "designation")
    private String designation;

    // Automatically set hire date using a custom date formatter
    @Column(name = "date_of_hire")
    private final String dateOfHire = formattedDateTime();

    // Salary of the employee
    @Column(name = "salary")
    private Long salary;

    // Optional field for employee exit date
    @Column(name = "date_of_exit")
    private String dateOfExit;

    // Constructor for creating a new employee with mandatory details
    public Employee(String firstName, String lastName, String phoneNumber, String email, String address, Long salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.salary = salary;
    }
}

