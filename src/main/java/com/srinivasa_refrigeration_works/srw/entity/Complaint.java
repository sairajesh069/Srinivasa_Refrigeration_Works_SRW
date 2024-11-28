package com.srinivasa_refrigeration_works.srw.entity;

import static com.srinivasa_refrigeration_works.srw.utility.CustomDateTimeFormatter.formattedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marks this class as a JPA entity
@Table(name="complaints") // Maps this entity to the "complaints" table in the database
@Data 
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
public class Complaint {

    // Enum for complaint status
    public enum Status {
        OPEN, IN_PROGRESS, RESOLVED; // Possible states for a complaint
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Auto-generate the ID value
    @Column(name="complaint_reference") // Column mapping for the complaint reference ID
    private int complaintReference;

    @Column(name="booked_by_id") // Column mapping for the booked by ID
    private String bookedById;

    @NotNull(message="Customer name is required.") // Validation to ensure the customer name is provided
    @Column(name="customer_name") // Column mapping for the customer's name
    private String customerName;

    @NotNull(message="Contact number is required.") // Validation to ensure contact number is provided
    @Pattern(regexp="^[0-9+]{10,13}$", message="Please enter a valid phone number.") // Validation for contact number format
    @Column(name="contact_number") // Column mapping for the contact number
    private String contactNumber;
    
    @NotNull(message="Email address is required.") // Validation to ensure email is provided
    @Email(message="Please enter a valid email address.") // Validation for email format
    @Column(name="email") // Column mapping for the email
    private String email;

    @NotNull(message="Address is required.") // Validation to ensure address is provided
    @Column(name="address") // Column mapping for the address
    private String address;

    @NotNull(message = "Please select a valid service type.") // Validation to ensure service type is selected
    @Column(name = "service_type") // Column mapping for the service type
    private String serviceType;

    @NotNull(message = "Please select a valid brand.") // Validation to ensure brand is selected
    @Column(name = "brand") // Column mapping for the brand
    private String brand;

    @NotNull(message = "Please select a valid model.") // Validation to ensure model is selected
    @Column(name = "model") // Column mapping for the model
    private String model;

    @NotNull(message="Description is required.") // Validation to ensure description is provided
    @Column(name="description") // Column mapping for the description
    private String description;

    @Column(name="created_at") // Column mapping for the created timestamp
    private final String createdAt = formattedDateTime(); // Automatically sets the creation time using a utility

    @Column(name="complaint_id") // Column mapping for the complaint ID
    private String complaintId;

    @Enumerated(EnumType.STRING) // Enum mapping for the complaint status
    @Column(name="status") // Column mapping for the status of the complaint
    private Status status = Status.OPEN; // Default status is "OPEN"

    @Column(name="technician_id") // Column mapping for the technician assigned to close the complaint
    private String technicianId;

    @Column(name="closed_at") // Column mapping for the timestamp when the complaint is closed
    private String closedAt;

    // Constructor with required fields to create a complaint
    public Complaint(String customerName, String contactNumber, String email, String address, String serviceType, String brand, String model, String description) {
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.serviceType = serviceType;
        this.brand = brand;
        this.model = model;
        this.description = description;
    }
}