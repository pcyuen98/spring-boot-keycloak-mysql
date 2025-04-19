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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PostRequestTest {

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
            ResponseEntity<String> response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );

            System.out.println("Status Code: " + response.getStatusCode());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String accessToken = jsonNode.get("accessToken").asText();

            System.out.println("Access Token: " + accessToken);
            PostRequestTest.token = accessToken;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    
    @Test
    public void testPreAuthorized() {
        // Arrange
        String url = BASE_URL + "/wcc/test"; // Replace with your actual URL
        Map<String, Object> jsonObject = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + PostRequestTest.token); // Use the token variable
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(jsonObject, headers);

        RestTemplate restTemplate = new RestTemplate();

        // Act
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        //  Add more specific assertions about the response body content if needed.
        System.out.println("Response Body (Success): " + response.getBody()); //optional
    }


}
