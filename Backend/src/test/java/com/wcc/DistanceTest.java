package com.wcc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.utility_classes.Distance;

@AutoConfigureMockMvc
@SpringBootTest
public class DistanceTest {

	private static final String BASE_URL = "http://localhost:8090";

	private static String token = "";

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
			ObjectMapper objectMapper = new ObjectMapper();

			assertNotNull(response.getBody());

			JsonNode jsonNode = objectMapper.readTree(response.getBody());
			String accessToken = jsonNode.get("accessToken").asText();

			System.out.println("Access Token: " + accessToken);
			DistanceTest.token = accessToken;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// calculate distance from KL to Seremban
	@Test
	public void testDistanceCalculation() {
		double distance = Distance.calculateDistance(3.140853, 101.693207, 2.7261, 101.9447);
		System.out.println("distance is " + distance);

		assertTrue(distance > 30 && distance < 200, "Distance not possible less than 30 or more than 200 kilometers");
	}

	@Test
	public void testDistanceURLisAuthorized() {
		// Arrange
		String url = BASE_URL + "/wcc/distance/get?source=50088&dest=70000";
		Map<String, Object> jsonObject = new HashMap<>();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("authorization", "Bearer " + DistanceTest.token); // Use the token variable
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(jsonObject, headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String responseBody = response.getBody();
		assertNotNull(response.getBody());

		assertTrue(Double.valueOf(responseBody) > 0, "Distance must more than 0");

	}

}
