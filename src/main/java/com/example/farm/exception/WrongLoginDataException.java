package com.example.farm.exception;

public class WrongLoginDataException extends RuntimeException{
    public WrongLoginDataException(String message) {
        super(message);
    }
}
