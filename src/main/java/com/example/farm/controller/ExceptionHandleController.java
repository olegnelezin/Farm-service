package com.example.farm.controller;

import com.example.farm.exception.EntityAlreadyExistsException;
import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.exception.InvalidDataException;
import com.example.farm.exception.WrongDataException;
import com.example.farm.model.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler({WrongDataException.class, InvalidDataException.class})
    public ResponseEntity<ExceptionDTO> badRequestExceptionHandler() {
        return new ResponseEntity<>(new ExceptionDTO("Wrong or invalid input data"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityDoesNotExistException.class})
    public ResponseEntity<ExceptionDTO> notFoundExceptionHandler() {
        return new ResponseEntity<>(new ExceptionDTO("Entity does not exist"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<ExceptionDTO> entityAlreadyExistsException() {
        return new ResponseEntity<>(new ExceptionDTO("Entity already exists"), HttpStatus.CONFLICT);
    }
}
