package com.grupoHasten.pruebaSalahdin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupoHasten.pruebaSalahdin.security.JwtRequest;
import com.grupoHasten.pruebaSalahdin.security.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        String username = "user";
        String password = "password";

        JwtRequest jwtRequest = new JwtRequest(username, password);
        String requestBody = objectMapper.writeValueAsString(jwtRequest);

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JwtResponse jwtResponse = objectMapper.readValue(response, JwtResponse.class);
        token = jwtResponse.jwttoken();
    }

    @Test
    public void testAccessProtectedEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/naves")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticateFailure() throws Exception {
        String username = "user";
        String password = "wrongpassword";

        JwtRequest jwtRequest = new JwtRequest(username, password);
        String requestBody = objectMapper.writeValueAsString(jwtRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("INVALID_CREDENTIALS")));
    }
}
