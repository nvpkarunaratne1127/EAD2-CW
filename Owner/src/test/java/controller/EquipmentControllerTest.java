package controller;

import tools.jackson.databind.ObjectMapper;
import dto.EquipmentDTO;
import model.EquipmentCategory;
import service.EquipmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EquipmentController.class)
class EquipmentControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @MockitoBean private EquipmentService service;

    @Test
    void getAll_returnsList() throws Exception {
        when(service.getAll()).thenReturn(List.of(
                EquipmentDTO.builder().id(1L).name("Dumbbell")
                        .category(EquipmentCategory.DUMBBELL).quantity(10).build()
        ));

        mockMvc.perform(get("/api/owner/equipment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Dumbbell"))
                .andExpect(jsonPath("$[0].quantity").value(10));
    }

    @Test
    void create_returns201() throws Exception {
        EquipmentDTO in = EquipmentDTO.builder()
                .name("Olympic Bar").category(EquipmentCategory.BAR)
                .quantity(5).weightSpecs("2.2m").build();
        EquipmentDTO out = EquipmentDTO.builder()
                .id(1L).name("Olympic Bar").category(EquipmentCategory.BAR)
                .quantity(5).weightSpecs("2.2m").status("AVAILABLE").build();

        when(service.create(any(EquipmentDTO.class))).thenReturn(out);

        mockMvc.perform(post("/api/owner/equipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(in)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Olympic Bar"));
    }

    @Test
    void create_returns400_whenNameMissing() throws Exception {
        EquipmentDTO invalid = EquipmentDTO.builder()
                .category(EquipmentCategory.BAR).quantity(5).build();

        mockMvc.perform(post("/api/owner/equipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/api/owner/equipment/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void adjustQuantity_patchesStock() throws Exception {
        when(service.adjustQuantity(eq(1L), eq(5)))
                .thenReturn(EquipmentDTO.builder().id(1L).name("Plate").quantity(15).build());

        mockMvc.perform(patch("/api/owner/equipment/1/quantity")
                        .param("delta", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(15));
    }
}
