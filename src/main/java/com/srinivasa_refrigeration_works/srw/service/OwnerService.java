package com.srinivasa_refrigeration_works.srw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.srinivasa_refrigeration_works.srw.entity.Owner;
import com.srinivasa_refrigeration_works.srw.repository.OwnerRepository;

@Service // Marks this class as a Spring service
public class OwnerService {

    private final OwnerRepository ownerRepository; // Repository for performing database operations on Owner entities

    // Constructor injection for OwnerRepository
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // Method to add an Owner, including setting the phone number format and assigning an Owner ID
    public void addOwner(Owner owner) {
        owner.setPhoneNumber("+91" + owner.getPhoneNumber()); // Ensure phone number starts with country code
        ownerRepository.save(owner); // Save the owner to the repository

        // Generate and set the Owner ID with the format "SRWxxx"
        owner.setOwnerId("SRW" + String.format("%03d", owner.getOwnerReference()));
        ownerRepository.save(owner); // Save the updated owner with the new ID
    }
    
     // Retrieves all owners from the database
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }
}
