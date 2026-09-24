package com.nibm.gym.controller;

import com.nibm.gym.model.Membership;
import com.nibm.gym.model.Trainer;
import com.nibm.gym.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@Tag(name = "Trainer Resource", description = "Endpoints for managing gym trainers and viewing assigned clients")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    @Operation(summary = "Get all trainers", description = "Retrieves all certified trainers with specializations and bio")
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get trainer by ID", description = "Retrieves a trainer profile by trainer ID")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get trainer by User ID", description = "Retrieves a trainer profile linked to a specific user account")
    public ResponseEntity<Trainer> getTrainerByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(trainerService.getTrainerByUserId(userId));
    }

    @PostMapping("/user/{userId}")
    @Operation(summary = "Register trainer profile", description = "Creates a trainer profile associated with a user account [201 Created]")
    public ResponseEntity<Trainer> createTrainer(@PathVariable Long userId, @RequestBody Trainer trainer) {
        Trainer created = trainerService.createTrainer(userId, trainer);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update trainer", description = "Updates trainer specialization, experience, or bio [200 OK]")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Long id, @RequestBody Trainer trainer) {
        Trainer updated = trainerService.updateTrainer(id, trainer);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete trainer", description = "Removes a trainer profile [204 No Content]")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/clients")
    @Operation(summary = "Get trainer's assigned clients", description = "Retrieves all gym-goers currently assigned to this trainer")
    public ResponseEntity<List<Membership>> getAssignedClients(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getAssignedClients(id));
    }
}
