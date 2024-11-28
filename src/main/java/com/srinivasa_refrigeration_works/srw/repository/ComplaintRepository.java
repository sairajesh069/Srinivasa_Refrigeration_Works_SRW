package com.srinivasa_refrigeration_works.srw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srinivasa_refrigeration_works.srw.entity.Complaint;

@Repository // Marks this interface as a Spring Data repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    
    // Finds all complaints that were booked by a specific user (identified by userId)
    public List<Complaint> findAllByBookedById(String userId);

}
