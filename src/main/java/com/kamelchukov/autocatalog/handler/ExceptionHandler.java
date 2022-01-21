package com.kamelchukov.autocatalog.handler;

import com.kamelchukov.autocatalog.exception.EntityNotFoundException;
import com.kamelchukov.autocatalog.exception.IncorrectDataException;
import com.kamelchukov.autocatalog.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleException(EntityNotFoundException ex, ServletWebRequest request) {

        return returnExceptionResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IncorrectDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleException(IncorrectDataException ex, ServletWebRequest request) {

        return returnExceptionResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionResponse> returnExceptionResponse(
            Exception e, ServletWebRequest request, HttpStatus status) {
        return new ResponseEntity<>(
                new ExceptionResponse(e.getMessage(), request.getRequest().getRequestURI()),
                status);
    }
}
