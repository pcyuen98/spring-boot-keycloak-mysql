package com.wcc.controller;

import java.util.Map;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.model.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/wcc")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private final LoginService loginService;

	/**
	 * Constructor-based dependency injection.
	 *
	 * @param loginService service to handle login logic
	 */
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@Operation(summary = "Login and retrieve access token from Keycloak using username and password.",
			   description = "Authenticates the user with Keycloak and returns an access token based on provided source and destination parameters. Should use HTTP POST for security purposes instead of GET. Demo purposes only")
	@GetMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam("username") String username,
	                                                 @RequestParam("password") String password) {
		return loginService.login(username, password);
	}

	@Operation(summary = "Controller to test isAuthenticated tag", 
	           description = "Token must be provided to access. For testing and demo purposes only")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/test")
	public String test() throws JsonProcessingException {
		@SuppressWarnings("unchecked")
		KeycloakPrincipal<KeycloakSecurityContext> user = (KeycloakPrincipal<KeycloakSecurityContext>)
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		AccessToken token = user.getKeycloakSecurityContext().getToken();

		ObjectMapper mapper = new ObjectMapper();
		String tokenJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(token);
		logger.info("token --> {}", tokenJson);
		return "success";
	}
}
