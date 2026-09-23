package lk.nibm.kd.hdse262ft.trainer.controller;

import lk.nibm.kd.hdse262ft.trainer.dto.TrainerDTO;
import lk.nibm.kd.hdse262ft.trainer.service.TrainerService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainerController.class)
class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TrainerService trainerService;

    // POST - Create Trainer
    @Test
    void createTrainer() throws Exception {

        TrainerDTO trainerDTO = new TrainerDTO(
                1L,
                "John",
                "john@gmail.com",
                "0771234567",
                "Fitness",
                "5 years"
        );

        when(trainerService.createTrainer(any(TrainerDTO.class)))
                .thenReturn(trainerDTO);

        mockMvc.perform(post("/api/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John",
                                    "email": "john@gmail.com",
                                    "phone": "0771234567",
                                    "specialization": "Fitness",
                                    "experience": "5 years"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@gmail.com"));
    }

    // GET - All Trainers
    @Test
    void getAllTrainers() throws Exception {

        TrainerDTO trainer1 = new TrainerDTO(
                1L,
                "John",
                "john@gmail.com",
                "0771234567",
                "Fitness",
                "5 years"
        );

        when(trainerService.getAllTrainers())
                .thenReturn(Arrays.asList(trainer1));

        mockMvc.perform(get("/api/trainers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    // GET - Trainer By ID
    @Test
    void getTrainerById() throws Exception {

        TrainerDTO trainerDTO = new TrainerDTO(
                1L,
                "John",
                "john@gmail.com",
                "0771234567",
                "Fitness",
                "5 years"
        );

        when(trainerService.getTrainerById(1L))
                .thenReturn(trainerDTO);

        mockMvc.perform(get("/api/trainers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@gmail.com"));
    }

    // PUT - Update Trainer
    @Test
    void updateTrainer() throws Exception {

        TrainerDTO trainerDTO = new TrainerDTO(
                1L,
                "John Updated",
                "johnupdated@gmail.com",
                "0771234567",
                "Strength Training",
                "6 years"
        );

        when(trainerService.updateTrainer(
                eq(1L),
                any(TrainerDTO.class)
        )).thenReturn(trainerDTO);

        mockMvc.perform(put("/api/trainers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John Updated",
                                    "email": "johnupdated@gmail.com",
                                    "phone": "0771234567",
                                    "specialization": "Strength Training",
                                    "experience": "6 years"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    // DELETE - Delete Trainer
    @Test
    void deleteTrainer() throws Exception {

        doNothing().when(trainerService).deleteTrainer(1L);

        mockMvc.perform(delete("/api/trainers/1"))
                .andExpect(status().isNoContent());

        verify(trainerService, times(1))
                .deleteTrainer(1L);
    }
}