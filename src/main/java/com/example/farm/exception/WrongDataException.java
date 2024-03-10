package com.example.farm.exception;

public class WrongDataException extends RuntimeException{
    public WrongDataException(String message) {
        super(message);
    }
}
