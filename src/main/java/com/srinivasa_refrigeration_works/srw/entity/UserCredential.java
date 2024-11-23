package com.srinivasa_refrigeration_works.srw.entity;

import java.util.ArrayList;
import java.util.List;

import com.srinivasa_refrigeration_works.srw.validation.UniqueValue;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_credentials") // Maps this class to the "user_credentials" table
@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
public class UserCredential {

    // Enum for user types
    public enum UserType {
        OWNER, EMPLOYEE, CUSTOMER;
    }

    @Id
    @Column(name = "user_id") // Maps to "user_id" column
    private String userId;

    @Column(name = "phone_number") // Maps to "phone_number" column
    private String phoneNumber;

    @NotNull(message = "Username is required") // Validation for non-null username
    @Size(min = 6, message = "Username must be at least 6 characters long") // Username length validation
    @UniqueValue(entityClass = UserCredential.class, fieldName = "username", message = "This username is already taken") // Custom uniqueness validation
    @Column(name = "username") // Maps to "username" column
    private String username;

    @NotNull(message = "Password is required") // Validation for non-null password
    @Pattern(regexp = "^[a-zA-Z0-9@.#$&{}]{8,}$", message = "Password must be at least 8 characters long and can include digits, letters, and special characters (. @ # $ & {}) only") // Password format validation
    @Column(name = "password") // Maps to "password" column
    private String password;

    @Transient // Marks this field as not part of the database table
    private String confirmPassword;

    @Column(name = "enabled") // Maps to "enabled" column
    private short enabled = 1; // Default value for enabled

    @Enumerated(EnumType.STRING) // Maps enum to a string in the database
    @Column(name = "user_type") // Maps to "user_type" column
    private UserType userType;

    @OneToMany(mappedBy = "userCredential", cascade = CascadeType.ALL) // One-to-many relationship with UserRole, cascading all changes
    private List<UserRole> userRoles;

    // PrePersist method to set confirmPassword before persistence
    @PrePersist
    public void prePersist() {
        this.confirmPassword = password;
    }

    // Adds a UserRole to the userRoles list and sets the bidirectional relationship
    public void addUserRole(UserRole userRole) {
        if (userRoles == null) {
            userRoles = new ArrayList<>();
        }
        userRoles.add(userRole);
        userRole.setUserCredential(this);
    }
}

