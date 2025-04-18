package com.wcc.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.wcc.model.entity.Postcodelatlng;
import com.wcc.model.service.PostService;
import com.wcc.utility_classes.Distance;

@RestController
@RequestMapping("/finder")
public class HelloController {

	@Autowired
	PostService postService;

	@GetMapping("/get")
	public String hello() {

		return postService.findAll().toString();
	}

	@GetMapping("/getdistance")
	public double getDistance(@RequestParam("source") String source, @RequestParam("dest") String dest) {

		Postcodelatlng postcodelatlngSource = postService.findByPostcode(source);

		Postcodelatlng postcodelatlngDest = postService.findByPostcode(dest);

		System.out.println("postcodelatlng:=" + postcodelatlngSource);

		double distance = Distance.calculateDistance(postcodelatlngSource.getLatitude(),
				postcodelatlngSource.getLongitude(), postcodelatlngDest.getLatitude(),
				postcodelatlngDest.getLongitude());
		return distance;
	}

	@GetMapping("/login")
	@ResponseBody
	public ResponseEntity<?> login() {
		// Keycloak server details

		String keycloakUrl = "http://localhost:8080/realms/socialapprealm/protocol/openid-connect/token";
		String clientId = "angularfront";
		String username = "matabot";
		String password = "password";
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

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		// 3. Use RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response;
		try {
			response = restTemplate.postForEntity(keycloakUrl, request, Map.class);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Map<String, Object> restfulResponse = new HashMap<>();
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

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/test")
	public String test() {
		return "test";
	}

	record Message(String message) {
	};
}
