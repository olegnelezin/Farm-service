package com.example.farm.exception;

public class EmployeeAlreadyExistsException extends RuntimeException{
    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}
