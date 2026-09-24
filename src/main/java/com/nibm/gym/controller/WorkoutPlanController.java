package com.nibm.gym.controller;

import com.nibm.gym.dto.WorkoutPlanRequest;
import com.nibm.gym.model.WorkoutPlan;
import com.nibm.gym.service.WorkoutPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-plans")
@Tag(name = "Workout & Diet Plans", description = "Endpoints for trainers to prescribe routines, diet plans, and notes for assigned clients")
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get customer's workout and diet plan", description = "Retrieves the prescribed routine and diet notes for a client")
    public ResponseEntity<WorkoutPlan> getPlanByCustomerId(@PathVariable Long customerId) {
        return workoutPlanService.getPlanByCustomerId(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/trainer/{trainerId}")
    @Operation(summary = "Get all plans assigned by trainer", description = "Retrieves all client workout routines authored by a trainer")
    public ResponseEntity<List<WorkoutPlan>> getPlansByTrainerId(@PathVariable Long trainerId) {
        return ResponseEntity.ok(workoutPlanService.getPlansByTrainerId(trainerId));
    }

    @PostMapping
    @Operation(summary = "Save or update workout & diet plan", description = "Allows a trainer to assign or update routines and diet instructions")
    public ResponseEntity<WorkoutPlan> saveOrUpdatePlan(@Valid @RequestBody WorkoutPlanRequest request) {
        WorkoutPlan saved = workoutPlanService.saveOrUpdatePlan(request);
        return ResponseEntity.ok(saved);
    }
}
