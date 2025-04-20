package com.wcc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.wcc.controller.LoginController;

/**
 * Integration tests for verifying Spring context loading and controller availability.
 * <p>
 * This test class uses {@link SpringBootTest} to load the full application context
 * and {@link AutoConfigureMockMvc} for testing the web layer.
 * </p>
 * 
 * It checks that:
 * <ul>
 *   <li>The application context is loaded correctly</li>
 *   <li>The {@link LoginController} bean is properly injected</li>
 *   <li>The `/wcc/distance/postcode/all` endpoint is accessible and responds with HTTP 200</li>
 * </ul>
 * 
 * @author CY
 */
@AutoConfigureMockMvc
@SpringBootTest
public class RepositoryTest {

    @Autowired
    private MockMvc mockMvc; // Added for more comprehensive testing

    @Autowired
    private LoginController loginController;

    /**
     * Verifies that the Spring application context loads successfully.
     */
    @Test
    void contextLoads() {
        // Basic sanity test
    }

    /**
     * Ensures that the LoginController bean is properly injected by Spring.
     */
    @Test
    void testFinderControllerIsNotNull() {
        assertNotNull(loginController, "LoginController should not be null (Autowired failed)");
    }

    /**
     * Sends a GET request to the /wcc/distance/postcode/all endpoint and expects a 200 OK response.
     * 
     * @throws Exception if the request fails
     */
    @Test
    void testGetDistanceEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/wcc/distance/postcode/all"))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
