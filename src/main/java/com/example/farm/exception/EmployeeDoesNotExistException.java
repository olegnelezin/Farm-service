package com.example.farm.exception;

public class EmployeeDoesNotExistException extends RuntimeException{
    public EmployeeDoesNotExistException(String message) {
        super(message);
    }
}
