package com.nibm.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nibm.gym.model.Equipment;
import com.nibm.gym.model.EquipmentCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/equipment should return 200 OK and preloaded equipment list")
    void testGetAllEquipment() throws Exception {
        mockMvc.perform(get("/api/equipment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].name", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/equipment?category=DUMBBELL should return only dumbbell equipment")
    void testGetEquipmentByCategory() throws Exception {
        mockMvc.perform(get("/api/equipment")
                        .param("category", "DUMBBELL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].category", is("DUMBBELL")));
    }

    @Test
    @DisplayName("POST /api/equipment should create new equipment and return 201 Created")
    void testCreateEquipment() throws Exception {
        Equipment newEquipment = new Equipment(
                "Incline Chest Press Machine",
                EquipmentCategory.MACHINE,
                2,
                "Pin-selected 100kg weight stack",
                "AVAILABLE",
                "Upper pectoralis isolation machine"
        );

        mockMvc.perform(post("/api/equipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEquipment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Incline Chest Press Machine")))
                .andExpect(jsonPath("$.category", is("MACHINE")));
    }
}
