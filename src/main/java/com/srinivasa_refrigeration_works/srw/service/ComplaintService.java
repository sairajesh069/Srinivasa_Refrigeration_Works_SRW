package com.srinivasa_refrigeration_works.srw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.srinivasa_refrigeration_works.srw.entity.Complaint;
import com.srinivasa_refrigeration_works.srw.repository.ComplaintRepository;

@Service // Marks the class as a Spring service
public class ComplaintService {

    // Repository for interacting with the Complaint database
    private final ComplaintRepository complaintRepository;

    // Constructor for dependency injection of ComplaintRepository
    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository; 
    }

    // Adds a new complaint to the database
    public void registerComplaint(Complaint complaint) {
        complaint.setContactNumber("+91" + complaint.getContactNumber()); // Adding country code to contact number
        complaintRepository.save(complaint); // Save complaint to DB
        String complaintId = "SRW" + String.format("%07d", complaint.getComplaintReference()); // Generate unique complaint ID
        complaint.setComplaintId(complaintId); // Set the generated complaint ID
        complaintRepository.save(complaint); // Save complaint again with ID
    }

    // Retrieves a list of complaints based on the userId (who booked them)
    public List<Complaint> getComplaintsByUserId(String userId) {
        return complaintRepository.findAllByBookedById(userId); // Fetch complaints from the repository using userId
    }
    
}
