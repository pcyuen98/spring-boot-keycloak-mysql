package com.wcc.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wcc.utility_classes.ExceptionUtil;


@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
	
	@ExceptionHandler(DemoAppException.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        
        Map<String, Object> restfulResponse = new HashMap<>();
        restfulResponse.put(DemoAppException.class.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        
        ResponseEntity<Map<String, Object>> responseEntity = ExceptionUtil.getResponseEntity("Demo App general Exception. ", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        
        if (logger.isErrorEnabled()) {
            logger.error("Exception handled: {}", responseEntity);
        }
        
        return responseEntity;
    }
}
