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
 */
@AutoConfigureMockMvc
@SpringBootTest
class ControllerTests {

    private final MockMvc mockMvc;
    private final LoginController loginController;

    /**
     * Constructor-based dependency injection.
     *
     * @param mockMvc the mock MVC object for simulating requests
     * @param loginController the login controller to test
     */
    @Autowired
    ControllerTests(MockMvc mockMvc, LoginController loginController) {
        this.mockMvc = mockMvc;
        this.loginController = loginController;
    }

    @Test
    void contextLoads() {
        // No assertions needed; failure to load context will cause test to fail
    }

    @Test
    void testFinderControllerIsNotNull() {
        assertNotNull(loginController, "LoginController should not be null (Autowired failed)");
    }

    @Test
    void testGetDistanceEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/wcc/distance/postcode/all"))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
