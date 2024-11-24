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
@Table(name = "customers") // Maps this entity to the "customers" table in the database
@Data
@NoArgsConstructor
public class Customer {

    // Auto-generated primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_reference")
    private int customerReference;

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
    @Pattern(regexp = "^[0-9+]{10,13}$", message = "Please provide a valid phone number.")
    @UniqueValue(fieldName = "phoneNumber", entityClass = Customer.class, message = "Phone number is already in use.")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    // Email address with validation for format and uniqueness
    @NotNull(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    @UniqueValue(fieldName = "email", entityClass = Customer.class, message = "Email is already in use.")
    @Column(name = "email", unique = true)
    private String email;

    // Address (required)
    @NotNull(message = "Address is required.")
    @Column(name = "address")
    private String address;

    // Customer ID (auto-assigned after creation)
    @Column(name = "customer_id")
    private String customerId;

    // Enrollment date (set automatically)
    @Column(name = "enrollment_date")
    private final String enrollmentDate = formattedDateTime();

    // Constructor for creating a new customer with mandatory details
    public Customer(String firstName, String lastName, String phoneNumber, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}