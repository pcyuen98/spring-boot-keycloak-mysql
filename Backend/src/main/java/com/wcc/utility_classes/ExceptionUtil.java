package com.wcc.utility_classes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionUtil {

	  private ExceptionUtil() {
		    throw new IllegalStateException("Utility class");
		  }
	
	public static ResponseEntity<Map<String, Object>> getResponseEntity(String msg, HttpStatus httpStatus, Exception e) {

		Map<String, Object> restfulResponse = new HashMap<>();
		restfulResponse.put("message", msg);
		restfulResponse.put("status", httpStatus);
		restfulResponse.put("code", httpStatus.value());
		restfulResponse.put("exception", e.getMessage());

		return new ResponseEntity<>(restfulResponse, httpStatus);
	}
}
