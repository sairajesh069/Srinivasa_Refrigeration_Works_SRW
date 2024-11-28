package com.srinivasa_refrigeration_works.srw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srinivasa_refrigeration_works.srw.entity.UserCredential;

import jakarta.transaction.Transactional;

@Repository // Marks this interface as a Spring Data repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {

    @Query("SELECT username FROM UserCredential WHERE phoneNumber = :phoneNumber") 
    // Custom query to find the username associated with a given phone number
    public String findUsernameByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT COUNT(u)>0 FROM UserCredential u WHERE u.phoneNumber = :phoneNumber AND u.username = :username") 
    // Custom query to check if a user with the given phone number and username exists
    public boolean existsByPhoneNumberAndUsername(@Param("phoneNumber") String phoneNumber, @Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE UserCredential SET password = :password WHERE username = :username") 
    // Update the password for a specific user identified by the username
    public void updatePassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT userId FROM UserCredential WHERE username = :username")
    // Custom query to find the userId associated with a given username
    public String findUserIdByUsername(@Param("username") String username);

}
