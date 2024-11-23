package com.srinivasa_refrigeration_works.srw.wrapper;

import org.springframework.stereotype.Component;

import com.srinivasa_refrigeration_works.srw.entity.Employee;
import com.srinivasa_refrigeration_works.srw.entity.UserCredential;

import jakarta.validation.Valid;

// Wrapper for Employee and UserCredential
@Component
public class EmployeeCredentialWrapper {

    @Valid
    private Employee employee;  // Holds Employee details

    @Valid
    private UserCredential userCredential;  // Holds UserCredential details

    // Getter and setter for employee
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Getter and setter for userCredential
    public UserCredential getUserCredential() {
        return userCredential;
    }
    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    // Returns a string representation of the wrapped entities
    @Override
    public String toString() {
        return "Customer Credentials: [Employee: "+ employee + " , User Credentials: " + userCredential + "]";
    }
}
