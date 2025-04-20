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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.model.bean.Distance;
import com.wcc.utility_classes.DistanceUtil;

/**
 * Integration tests for the distance calculation and secured endpoint authorization.
 * 
 * <p>This test class includes:
 * <ul>
 *   <li>Verification of token retrieval from the Keycloak login endpoint</li>
 *   <li>Calculation of distance between two coordinates</li>
 *   <li>Authorization testing of the `/wcc/distance/get` endpoint</li>
 * </ul>
 * </p>
 */
@AutoConfigureMockMvc
@SpringBootTest
public class DistanceTest {

    private static final String BASE_URL = "http://localhost:8090";
    private static String token = "";

    /**
     * Setup method executed once before all test cases.
     * Authenticates with the login endpoint and retrieves a Keycloak access token.
     */
    @BeforeAll
    static void setup() {
        try {
            String endpoint = BASE_URL + "/wcc/login?username=admin&password=adminpass";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, requestEntity, String.class);

            System.out.println("Key Cloak HTTP Login Status Code: " + response.getStatusCode());
            assertEquals(HttpStatus.OK, response.getStatusCode());

            assertNotNull(response.getBody());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String accessToken = jsonNode.get("accessToken").asText();
            System.out.println("Access Token: " + accessToken);

            DistanceTest.token = accessToken;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Unit test to validate the distance calculation between Kuala Lumpur and Seremban.
     */
    @Test
    public void testDistanceCalculation() {
        double distance = DistanceUtil.calculateDistance(3.140853, 101.693207, 2.7261, 101.9447);
        System.out.println("Distance is " + distance);
        assertTrue(distance > 30 && distance < 200, "Distance not possible less than 30 or more than 200 kilometers");
    }

    /**
     * Integration test to verify the secured `/wcc/distance/get` endpoint returns 
     * valid distance data when authenticated using a Bearer token.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    @Test
    public void testDistanceURLisAuthorizedWithValidValue() throws JsonMappingException, JsonProcessingException {
        String url = BASE_URL + "/wcc/distance/get?source=50088&dest=70000";
        Map<String, Object> jsonObject = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + DistanceTest.token);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(jsonObject, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        String responseBody = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        Distance distance = mapper.readValue(responseBody, Distance.class);
        
        assertTrue(distance.getDistance() > 0, "Distance must be more than 0");
        
    }
}
