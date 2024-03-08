package com.example.farm.exception;

public class EntityDoesNotExistException extends RuntimeException{
    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
