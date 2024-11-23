package com.srinivasa_refrigeration_works.srw.service;

import org.springframework.stereotype.Service;

import com.srinivasa_refrigeration_works.srw.entity.Employee;
import com.srinivasa_refrigeration_works.srw.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // Constructor to inject EmployeeRepository
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Method to add an employee, format phone number and employee ID
    public void addEmployee(Employee employee) {
        employee.setPhoneNumber("+91" + employee.getPhoneNumber()); // Prefix phone number with country code
        employeeRepository.save(employee); // Save employee with phone number
        employee.setEmployeeId("SRW" + String.format("%04d", employee.getEmployeeReference())); // Generate employee ID
        employeeRepository.save(employee); // Save employee with employee ID
    }
}
