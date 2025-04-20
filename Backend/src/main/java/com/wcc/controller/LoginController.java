package com.wcc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcc.model.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/wcc")
public class LoginController {

	@Autowired
	LoginService loginService;

	@Operation(summary = "Login and retrieve access token from Keycloak using username and password. ", description = "Authenticates the user with Keycloak and returns an access token based on provided source and destination parameters. Should use HTTP POST for security purposes instead of GET. Demo purposes only")
	@GetMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		return loginService.login(username, password);
	}

	@Operation(summary = "Controller to test isAuthenticated tag", description = "Token Must be provided to access. For Testing and Demo Purposes only")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/test")
	public String test() {
		return "success";
	}
}
