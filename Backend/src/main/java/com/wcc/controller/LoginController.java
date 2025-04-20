package com.wcc.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wcc.exceptions.DemoAppException;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/wcc")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Operation(summary = "Login and retrieve access token from Keycloak using username and password. ", description = "Authenticates the user with Keycloak and returns an access token based on provided source and destination parameters. Should use HTTP POST for security purposes instead of GET. Demo purposes only")
	@GetMapping("/login")
	@ResponseBody
	public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
		// Keycloak server details
		logger.info("User Login username={} , password={}", username, password );
		String keycloakUrl = "http://localhost:8080/realms/master/protocol/openid-connect/token";
		String clientId = "admin-cli";
		String scope = "openid profile email";

		// 1. Set up the request headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// 2. Set up the request body
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("username", username);
		map.add("password", password);
		map.add("client_id", clientId);
		map.add("scope", scope);

		ResponseEntity<Map> response;
		Map<String, Object> restfulResponse = new HashMap<>();
		try {
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

			RestTemplate restTemplate = new RestTemplate();

			response = restTemplate.postForEntity(keycloakUrl, request, Map.class);
		} catch (Exception e) {
			throw new DemoAppException(e);
		}

		if (response.getStatusCode().is2xxSuccessful()) {
			Map<String, Object> responseBody = response.getBody();
			String accessToken = (String) responseBody.get("access_token");
			if (accessToken != null) {
				restfulResponse.put("accessToken", accessToken);
				return new ResponseEntity<>(restfulResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(restfulResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {			
			return new ResponseEntity<>(restfulResponse, HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "Controller to test isAuthenticated tag", description = "Token Must be provided to access. For Testing and Demo Purposes only")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/test")
	public String test() {
		return "success";
	}
}
