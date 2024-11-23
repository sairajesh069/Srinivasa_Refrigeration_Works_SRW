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