package com.srinivasa_refrigeration_works.srw.entity;

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
@Table(name = "owners") // Maps this class to the "owners" table
@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates primary key
    @Column(name = "owner_reference") // Maps to "owner_reference" column
    private int ownerReference;

    @NotNull(message = "First name is mandatory") // Validation for non-null first name
    @Column(name = "first_name") // Maps to "first_name" column
    private String firstName;

    @NotNull(message = "Last name is mandatory") // Validation for non-null last name
    @Column(name = "last_name") // Maps to "last_name" column
    private String lastName;

    @NotNull(message = "Phone number is mandatory") // Validation for non-null phone number
    @Pattern(regexp = "^[0-9+]{10,13}$", message = "Please enter a valid phone number") // Phone number format validation
    @UniqueValue(fieldName = "phoneNumber", entityClass = Owner.class, inEveryUserEntity=true, message = "This phone number is already registered") // Ensures the phone number is unique across all user-related entities
    @Column(name = "phone_number", unique = true) // Unique constraint for "phone_number"
    private String phoneNumber;

    @NotNull(message = "Email is mandatory") // Validation for non-null email
    @Email(message = "Please enter a valid email address") // Email format validation
    @UniqueValue(fieldName = "email", entityClass = Owner.class, inEveryUserEntity=true, message = "This email address is already registered") // Ensures the email is unique across all user-related entities
    @Column(name = "email") // Maps to "email" column
    private String email;

    @NotNull(message = "Address is mandatory") // Validation for non-null address
    @Column(name = "address") // Maps to "address" column
    private String address;

    @Column(name = "owner_id") // Maps to "owner_id" column
    private String ownerId;

    // Custom constructor to initialize required fields
    public Owner(String firstName, String lastName, String phoneNumber, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }
}