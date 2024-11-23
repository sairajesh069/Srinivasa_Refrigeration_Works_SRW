package com.srinivasa_refrigeration_works.srw.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable // Indicates this class is an embeddable primary key
@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
public class UserRoleId implements Serializable {

    @Column(name = "user_id") // Maps to "user_id" column in the table
    private String userCredential; // Stores the user credential ID

    @Column(name = "roles") // Maps to "roles" column in the table
    private String role; // Stores the role of the user
}
