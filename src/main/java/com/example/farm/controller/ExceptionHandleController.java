package com.example.farm.controller;

import com.example.farm.exception.EntityAlreadyExistsException;
import com.example.farm.exception.EntityDoesNotExistException;
import com.example.farm.exception.InvalidDataException;
import com.example.farm.exception.WrongDataException;
import com.example.farm.model.dto.ExceptionDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionDTO> internalServerErrorHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionDTO("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({WrongDataException.class, InvalidDataException.class,
            DataIntegrityViolationException.class, ConstraintViolationException.class})
    public ResponseEntity<ExceptionDTO> badRequestExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionDTO("Wrong or invalid input data"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityDoesNotExistException.class})
    public ResponseEntity<ExceptionDTO> notFoundExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<ExceptionDTO> entityAlreadyExistsException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage()), HttpStatus.CONFLICT);
    }
}
