package com.nibm.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nibm.gym.dto.AuthRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/auth/login with valid owner credentials should return 200 OK and OWNER role")
    void testLoginSuccess_Owner() throws Exception {
        AuthRequest loginRequest = new AuthRequest("admin", "admin123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("admin")))
                .andExpect(jsonPath("$.role", is("OWNER")))
                .andExpect(jsonPath("$.message", is("Login successful")));
    }

    @Test
    @DisplayName("POST /api/auth/login with valid trainer credentials should return 200 OK and TRAINER role")
    void testLoginSuccess_Trainer() throws Exception {
        AuthRequest loginRequest = new AuthRequest("kasun", "trainer123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("kasun")))
                .andExpect(jsonPath("$.role", is("TRAINER")));
    }

    @Test
    @DisplayName("POST /api/auth/login with invalid password should return 400 Bad Request")
    void testLoginFailure_WrongPassword() throws Exception {
        AuthRequest loginRequest = new AuthRequest("admin", "wrong_password");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Bad Request")));
    }
}
