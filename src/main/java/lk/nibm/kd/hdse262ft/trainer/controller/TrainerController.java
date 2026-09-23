package lk.nibm.kd.hdse262ft.trainer.controller;

import lk.nibm.kd.hdse262ft.trainer.dto.TrainerDTO;
import lk.nibm.kd.hdse262ft.trainer.service.TrainerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    // CREATE TRAINER
    @PostMapping
    public ResponseEntity<TrainerDTO> createTrainer(
            @RequestBody TrainerDTO trainerDTO) {

        TrainerDTO createdTrainer =
                trainerService.createTrainer(trainerDTO);

        return new ResponseEntity<>(
                createdTrainer,
                HttpStatus.CREATED
        );
    }

    // GET ALL TRAINERS
    @GetMapping
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {

        List<TrainerDTO> trainers =
                trainerService.getAllTrainers();

        return ResponseEntity.ok(trainers);
    }

    // GET TRAINER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> getTrainerById(
            @PathVariable Long id) {

        TrainerDTO trainer =
                trainerService.getTrainerById(id);

        return ResponseEntity.ok(trainer);
    }

    // UPDATE TRAINER
    @PutMapping("/{id}")
    public ResponseEntity<TrainerDTO> updateTrainer(
            @PathVariable Long id,
            @RequestBody TrainerDTO trainerDTO) {

        TrainerDTO updatedTrainer =
                trainerService.updateTrainer(id, trainerDTO);

        return ResponseEntity.ok(updatedTrainer);
    }

    // DELETE TRAINER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(
            @PathVariable Long id) {

        trainerService.deleteTrainer(id);

        return ResponseEntity.noContent().build();
    }
}