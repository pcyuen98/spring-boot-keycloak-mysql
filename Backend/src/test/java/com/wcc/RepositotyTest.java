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

@AutoConfigureMockMvc
@SpringBootTest
public class RepositotyTest {

	@Autowired
	private MockMvc mockMvc; // Added for more comprehensive testing

	@Autowired
	private LoginController loginController;

	@Test
	void contextLoads() {

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