package com.wcc.config;

import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.wcc.utility_classes.InternalServerError;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<InternalServerError> handleInternalServerError(DataAccessException ex) {
        return new ResponseEntity<>(new InternalServerError("Database error", ex.getMessage() + ": " + ex.getMostSpecificCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
