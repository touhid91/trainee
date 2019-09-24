package com.gym.trainee.controller;

import com.gym.trainee.model.error.ErrorResponse;
import com.gym.trainee.model.error.ValidationErrorResponse;
import com.gym.trainee.model.error.Violation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
class ErrorController {
    private final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<ErrorResponse> onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations())
            error.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));

        logger.error("caught: " + error.getViolations().toString());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getViolations()), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors())
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));

        logger.error(String.format("caught: %s", error.getViolations()));
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getViolations()), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({JsonParseException.class, JsonProcessingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<ErrorResponse> onJsonMappingException(
            JsonMappingException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.getViolations().add(new Violation(e.getMessage(), e.getOriginalMessage()));

        logger.error(String.format("caught: %s", error.getViolations()));
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getViolations()), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ResponseEntity<ErrorResponse> onNoSuchFieldError(IndexOutOfBoundsException e) {
        logger.error(String.format("caught: %s", e.getMessage()));
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<ErrorResponse> onIllegalArgumentException(IllegalArgumentException e) {
        logger.error(String.format("caught: %s", e.getMessage()));
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()), null, HttpStatus.BAD_REQUEST);
    }
}