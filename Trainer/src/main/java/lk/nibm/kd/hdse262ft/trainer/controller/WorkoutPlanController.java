package lk.nibm.kd.hdse262ft.trainer.controller;

import lk.nibm.kd.hdse262ft.trainer.dto.WorkoutPlanDTO;
import lk.nibm.kd.hdse262ft.trainer.service.WorkoutPlanService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-plans")
@CrossOrigin(origins = "*")
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    // CREATE WORKOUT PLAN
    @PostMapping
    public ResponseEntity<WorkoutPlanDTO> createWorkoutPlan(
            @RequestBody WorkoutPlanDTO workoutPlanDTO) {

        WorkoutPlanDTO createdWorkoutPlan =
                workoutPlanService.createWorkoutPlan(workoutPlanDTO);

        return new ResponseEntity<>(
                createdWorkoutPlan,
                HttpStatus.CREATED
        );
    }

    // GET ALL WORKOUT PLANS
    @GetMapping
    public ResponseEntity<List<WorkoutPlanDTO>> getAllWorkoutPlans() {

        List<WorkoutPlanDTO> workoutPlans =
                workoutPlanService.getAllWorkoutPlans();

        return ResponseEntity.ok(workoutPlans);
    }

    // GET WORKOUT PLAN BY ID
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlanDTO> getWorkoutPlanById(
            @PathVariable Long id) {

        WorkoutPlanDTO workoutPlan =
                workoutPlanService.getWorkoutPlanById(id);

        return ResponseEntity.ok(workoutPlan);
    }

    // UPDATE WORKOUT PLAN
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlanDTO> updateWorkoutPlan(
            @PathVariable Long id,
            @RequestBody WorkoutPlanDTO workoutPlanDTO) {

        WorkoutPlanDTO updatedWorkoutPlan =
                workoutPlanService.updateWorkoutPlan(
                        id,
                        workoutPlanDTO
                );

        return ResponseEntity.ok(updatedWorkoutPlan);
    }

    // DELETE WORKOUT PLAN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutPlan(
            @PathVariable Long id) {

        workoutPlanService.deleteWorkoutPlan(id);

        return ResponseEntity.noContent().build();
    }
}