-- Creating the 'srinivasa_refrigeration_works' database.
CREATE DATABASE srinivasa_refrigeration_works;

-- Selecting the 'srinivasa_refrigeration_works' database for all subsequent operations.
USE srinivasa_refrigeration_works;

-- Creating the owners table
CREATE TABLE owners (
    owner_reference INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE, 
    email VARCHAR(45) NOT NULL UNIQUE,
    address VARCHAR(108) NOT NULL,
    owner_id VARCHAR(10) UNIQUE);
    
-- Creating the user_credentials table
CREATE TABLE user_credentials (
    user_id VARCHAR(25) NOT NULL UNIQUE PRIMARY KEY,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    username VARCHAR(45) NOT NULL UNIQUE,
    password VARCHAR(25) NOT NULL,
    enabled TINYINT NOT NULL,
    user_type ENUM('OWNER', 'EMPLOYEE', 'CUSTOMER') NOT NULL);
    
-- Creating the user_roles table
CREATE TABLE user_roles (
    user_id VARCHAR(25) NOT NULL UNIQUE,
    username VARCHAR(45) NOT NULL UNIQUE,
    roles VARCHAR(25) NOT NULL,
    PRIMARY KEY(user_id, roles),
    FOREIGN KEY(user_id) REFERENCES user_credentials(user_id));
    
-- Creating the employees table
CREATE TABLE employees (
    employee_reference INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE, 
    email VARCHAR(45) NOT NULL UNIQUE,
    address VARCHAR(108) NOT NULL,
    employee_id VARCHAR(10) UNIQUE,
    designation VARCHAR(25),
    date_of_hire VARCHAR(25) NOT NULL,
    salary INT NOT NULL,
    date_of_exit VARCHAR(25)) AUTO_INCREMENT=101;
    
-- Creating the customers table
CREATE TABLE customers (
    customer_reference INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE, 
    email VARCHAR(45) NOT NULL UNIQUE,
    address VARCHAR(108) NOT NULL,
    customer_id VARCHAR(10) UNIQUE,
    enrollment_date VARCHAR(25) NOT NULL) AUTO_INCREMENT=1001;