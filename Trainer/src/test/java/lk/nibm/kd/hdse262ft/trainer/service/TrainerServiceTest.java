package lk.nibm.kd.hdse262ft.trainer.service;

import lk.nibm.kd.hdse262ft.trainer.dto.TrainerDTO;
import lk.nibm.kd.hdse262ft.trainer.entity.Trainer;
import lk.nibm.kd.hdse262ft.trainer.repository.TrainerRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService trainerService;


    // CREATE TEST
    @Test
    void createTrainer() {

        TrainerDTO dto = new TrainerDTO(
                null,
                "John",
                "john@gmail.com",
                "0771234567",
                "Fitness",
                "5 Years"
        );

        Trainer savedTrainer = new Trainer();

        savedTrainer.setName("John");
        savedTrainer.setEmail("john@gmail.com");
        savedTrainer.setPhone("0771234567");
        savedTrainer.setSpecialization("Fitness");
        savedTrainer.setExperience("5 Years");

        when(trainerRepository.save(any(Trainer.class)))
                .thenReturn(savedTrainer);

        TrainerDTO result =
                trainerService.createTrainer(dto);

        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("john@gmail.com", result.getEmail());

        verify(trainerRepository, times(1))
                .save(any(Trainer.class));
    }


    // GET BY ID TEST
    @Test
    void getTrainerById() {

        Trainer trainer = new Trainer();

        trainer.setName("John");
        trainer.setEmail("john@gmail.com");
        trainer.setPhone("0771234567");
        trainer.setSpecialization("Fitness");
        trainer.setExperience("5 Years");

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(trainer));

        TrainerDTO result =
                trainerService.getTrainerById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("john@gmail.com", result.getEmail());

        verify(trainerRepository, times(1))
                .findById(1L);
    }


    // NOT FOUND TEST
    @Test
    void getTrainerByIdWhenNotFound() {

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> trainerService.getTrainerById(1L)
        );

        verify(trainerRepository, times(1))
                .findById(1L);
    }


    // DELETE TEST
    @Test
    void deleteTrainer() {

        when(trainerRepository.existsById(1L))
                .thenReturn(true);

        trainerService.deleteTrainer(1L);

        verify(trainerRepository, times(1))
                .existsById(1L);

        verify(trainerRepository, times(1))
                .deleteById(1L);
    }
}