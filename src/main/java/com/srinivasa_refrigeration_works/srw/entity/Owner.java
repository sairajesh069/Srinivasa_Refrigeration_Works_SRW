package com.srinivasa_refrigeration_works.srw.entity;

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
@Table(name = "owners") // Maps this class to the "owners" table
@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates primary key
    @Column(name = "owner_reference") // Maps to "owner_reference" column
    private int ownerReference;

    @NotNull(message = "is required") // Validation for non-null values
    @Column(name = "first_name") // Maps to "first_name" column
    private String firstName;

    @NotNull(message = "is required")
    @Column(name = "last_name") // Maps to "last_name" column
    private String lastName;

    @NotNull(message = "is required")
    @Pattern(regexp = "^[0-9+]{10,13}$", message = "enter a valid phone number") // Validates phone number format
    @Column(name = "phone_number", unique = true) // Unique constraint for "phone_number"
    private String phoneNumber;

    @NotNull(message = "is required")
    @Email(message = "enter a valid email") // Validates email format
    @Column(name = "email") // Maps to "email" column
    private String email;

    @NotNull(message = "is required")
    @Column(name = "address") // Maps to "address" column
    private String address;

    @Column(name = "owner_id") // Maps to "owner_id" column
    private String ownerId;

    // Custom constructor for initializing required fields
    public Owner(String firstName, String lastName, String phoneNumber, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}