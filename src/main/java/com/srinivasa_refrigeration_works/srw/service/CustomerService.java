package com.srinivasa_refrigeration_works.srw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.srinivasa_refrigeration_works.srw.entity.Customer;
import com.srinivasa_refrigeration_works.srw.repository.CustomerRepository;

// Service to manage customer-related operations
@Service
public class CustomerService {

    // Repository for interacting with the Customer database
    private final CustomerRepository customerRepository;

    // Constructor for dependency injection of CustomerRepository
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Adds a new customer to the database
    public void addCustomer(Customer customer) {
        // Prefix phone number with country code
        customer.setPhoneNumber("+91" + customer.getPhoneNumber());

        // Save customer to the database
        customerRepository.save(customer);

        // Generate a unique customer ID based on reference ID
        customer.setCustomerId("SRW" + customer.getCustomerReference());

        // Save the updated customer object to include the generated ID
        customerRepository.save(customer);
    }

    // Retrieves all customers from the database
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
