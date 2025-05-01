package com.wcc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration test for verifying Keycloak authentication and authorization for
 * protected Spring Boot endpoints.
 * 
 * <p>
 * This test class performs:
 * <ul>
 * <li>Login to Keycloak-secured endpoint to obtain a JWT access token</li>
 * <li>Authorization check for a protected endpoint using the access token</li>
 * </ul>
 * </p>
 */
public class LoginTest {

	/** Base URL of the Spring Boot application */
	private static final String BASE_URL = "http://localhost:8090";

	/** JWT access token obtained after login */
	private static String token = "";

	/**
	 * Logs in to the Keycloak-secured login endpoint and retrieves an access token.
	 * This method is executed once before all test cases.
	 */
	@BeforeAll
	static void setup() {
		try {
			String endpoint = BASE_URL + "/wcc/login?username=admin&password=adminpass";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, requestEntity,
					String.class);

			System.out.println("Key Cloak HTTP Login Status Code: " + response.getStatusCode());
			assertEquals(HttpStatus.OK, response.getStatusCode());

			assertNotNull(response.getBody());

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response.getBody());
			String accessToken = jsonNode.get("accessToken").asText();

			System.out.println("Access Token: " + accessToken);
			LoginTest.token = accessToken;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests access to a secured endpoint using the retrieved access token. Verifies
	 * that the endpoint responds with HTTP 200 and a body of "success".
	 */
	@Test
	public void testPreAuthorized() {
		// Arrange
		String url = BASE_URL + "/wcc/test"; // Replace with your actual URL
		Map<String, Object> jsonObject = new HashMap<>();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("authorization", "Bearer " + LoginTest.token);
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(jsonObject, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		System.out.println("Test Spring Boot HTTP Get Response Body (Success): " + response.getBody());

		assertEquals("success", response.getBody());
	}

	/**
	 * Tests access to a secured endpoint using the retrieved access token. Verifies
	 * that the endpoint responds with HTTP 200 and a body of "success".
	 */
	@Test
	public void testUnAuthorized() {
		// Arrange
		String url = BASE_URL + "/wcc/test"; // Replace with your actual URL
		Map<String, Object> jsonObject = new HashMap<>();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(jsonObject, headers);

		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		} catch (HttpClientErrorException e) {
			System.out.println("Unauthorized Access Code: " + e.getRawStatusCode());
			assertEquals(401, e.getRawStatusCode());
		}
	}
}
