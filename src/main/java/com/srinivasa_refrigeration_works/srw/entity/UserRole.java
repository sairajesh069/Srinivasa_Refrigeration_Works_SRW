package com.srinivasa_refrigeration_works.srw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_roles") // Maps this class to the "user_roles" table
@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
public class UserRole {

    @EmbeddedId // Indicates that this is a composite key
    private UserRoleId userId; // Embedded composite ID for UserRole

    @Column(name = "username") // Maps to "username" column
    private String username;

    @ManyToOne // Many-to-one relationship with UserCredential
    @MapsId("userCredential") // Maps the ID field from the composite key
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // Joins with "user_id" in the UserCredential table
    private UserCredential userCredential;

    // Constructor to initialize username and role
    public UserRole(String username, String role) {
        this.userId = new UserRoleId(); // Initializes the composite key
        this.username = username;
        this.userId.setRole(role); // Sets the role in the composite key
    }
}
