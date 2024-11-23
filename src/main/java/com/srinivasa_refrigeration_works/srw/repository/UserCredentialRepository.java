package com.srinivasa_refrigeration_works.srw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srinivasa_refrigeration_works.srw.entity.UserCredential;

@Repository // Marks this interface as a Spring Data repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String>{

}
