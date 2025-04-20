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
 * Integration tests to verify the context loading and REST controller availability
 * in the Spring Boot application.
 * 
 * <p>This test class ensures that:
 * <ul>
 *   <li>Spring context loads successfully</li>
 *   <li>The {@link LoginController} is correctly injected</li>
 *   <li>REST endpoints respond as expected</li>
 * </ul>
 * </p>
 */
@AutoConfigureMockMvc
@SpringBootTest
class ControllerTests {

    /** MockMvc instance used to simulate HTTP requests for controller testing */
    @Autowired
    private MockMvc mockMvc;

    /** LoginController instance injected by Spring to verify controller loading */
    @Autowired
    private LoginController loginController;

    /**
     * Verifies that the Spring application context loads without any issues.
     */
    @Test
    void contextLoads() {
        // No assertions needed; failure to load context will cause test to fail
    }

    /**
     * Checks that the {@link LoginController} bean is correctly autowired and not null.
     */
    @Test
    void testFinderControllerIsNotNull() {
        assertNotNull(loginController, "LoginController should not be null (Autowired failed)");
    }

    /**
     * Tests the `/wcc/distance/postcode/all` endpoint to ensure it responds with HTTP 200 OK.
     * 
     * @throws Exception if the request fails
     */
    @Test
    void testGetDistanceEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/wcc/distance/postcode/all"))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
