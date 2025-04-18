package com.mgbt.socialapp_backend;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostRequestTest {

	private static final String BASE_URL = "http://localhost:8080";
	
    @Autowired
    private MockMvc mockMvc;
    
	private static String token = "";
    @BeforeAll
    static void setup() {
    	try {
            // Replace with your target endpoint
            String endpoint = "https://jsonplaceholder.typicode.com/posts/1";
            URL url = new URL(endpoint);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // or "POST", "PUT", etc.

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print the response
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("GET request failed.");
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    
    @Test
    public void testPostRequestSuccess() {
        // Arrange
        String url = "http://localhost:8090/finder/test"; // Replace with your actual URL
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJQTURHeFR4aTBGamIwRHMzVmdTWGVvSFhXdFBScnFyTG1LUnE2eElLSG1zIn0.eyJleHAiOjE3NDQ5NDcyMzIsImlhdCI6MTc0NDk0NjkzMiwianRpIjoiY2JmN2Y3MDgtNDhiNy00Y2NiLTgwZjYtOTRjMTMzOTg0OWQwIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9zb2NpYWxhcHByZWFsbSIsImF1ZCI6WyJzcHJpbmdiYWNrIiwiYWNjb3VudCJdLCJzdWIiOiJkZGYyNjg0My01NDcxLTRiNzktOGVmNS1kYTM0NjRmNGRhOWMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhbmd1bGFyZnJvbnQiLCJzZXNzaW9uX3N0YXRlIjoiZmY1MzliN2EtMWM1MS00ZTdiLTljZWUtNDQyMWM3ZDM4NDNkIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjQyMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtc29jaWFsYXBwcmVhbG0iLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsic3ByaW5nYmFjayI6eyJyb2xlcyI6WyJ1c2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwic2lkIjoiZmY1MzliN2EtMWM1MS00ZTdiLTljZWUtNDQyMWM3ZDM4NDNkIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtYXRhYm90In0.Auqq-REDYKJ9shQjRmxkbTvsOoBPO2IKtcJG9Mr-AWRhFdBZ2S10hXLyGhZcECTD3rW-pMW11nDXldvAn-sE0VFcWOkuIu4C2dgXkiTuZcdds-DLY9cuPoZ5xoGglbJmbF87xbVfRt7oK8gLzSrWkqUXqqeQlE8qc0WDi1DylZCn4InufhwxPrKeP0iRqK1XcFpKcdjtfQQ8wHWXGH8UzpvgWEyFXICz_vjuIs8xhyWNxDLp5vJqea8V0XCYE7Fs-iarnPdutB-N1A74FV1zouwkAhzBGpDK9TayIJWdw9nNDJP0QNzwxoS7OwFPwl9C43qebTdrKVYE_3NOIfZ8lA"; //  Replace with a valid test token
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("token", token);
        // Add any other necessary data to the jsonObject
        //jsonObject.put("key1", "value1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token); // Use the token variable
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
