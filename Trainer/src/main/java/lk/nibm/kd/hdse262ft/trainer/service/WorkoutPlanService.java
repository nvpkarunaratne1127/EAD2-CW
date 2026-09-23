package lk.nibm.kd.hdse262ft.trainer.service;

import lk.nibm.kd.hdse262ft.trainer.dto.WorkoutPlanDTO;
import lk.nibm.kd.hdse262ft.trainer.entity.WorkoutPlan;
import lk.nibm.kd.hdse262ft.trainer.repository.WorkoutPlanRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
    }

    // CREATE
    public WorkoutPlanDTO createWorkoutPlan(WorkoutPlanDTO workoutPlanDTO) {

        WorkoutPlan workoutPlan = new WorkoutPlan();

        workoutPlan.setPlanName(workoutPlanDTO.getPlanName());
        workoutPlan.setDescription(workoutPlanDTO.getDescription());
        workoutPlan.setGoal(workoutPlanDTO.getGoal());
        workoutPlan.setDurationWeeks(workoutPlanDTO.getDurationWeeks());
        workoutPlan.setTrainerId(workoutPlanDTO.getTrainerId());

        WorkoutPlan savedWorkoutPlan =
                workoutPlanRepository.save(workoutPlan);

        return convertToDTO(savedWorkoutPlan);
    }

    // GET ALL
    public List<WorkoutPlanDTO> getAllWorkoutPlans() {

        return workoutPlanRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public WorkoutPlanDTO getWorkoutPlanById(Long id) {

        WorkoutPlan workoutPlan = workoutPlanRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Workout plan not found with ID: " + id));

        return convertToDTO(workoutPlan);
    }

    // UPDATE
    public WorkoutPlanDTO updateWorkoutPlan(
            Long id,
            WorkoutPlanDTO workoutPlanDTO) {

        WorkoutPlan workoutPlan = workoutPlanRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Workout plan not found with ID: " + id));

        workoutPlan.setPlanName(workoutPlanDTO.getPlanName());
        workoutPlan.setDescription(workoutPlanDTO.getDescription());
        workoutPlan.setGoal(workoutPlanDTO.getGoal());
        workoutPlan.setDurationWeeks(workoutPlanDTO.getDurationWeeks());
        workoutPlan.setTrainerId(workoutPlanDTO.getTrainerId());

        WorkoutPlan updatedWorkoutPlan =
                workoutPlanRepository.save(workoutPlan);

        return convertToDTO(updatedWorkoutPlan);
    }

    // DELETE
    public void deleteWorkoutPlan(Long id) {

        if (!workoutPlanRepository.existsById(id)) {
            throw new RuntimeException(
                    "Workout plan not found with ID: " + id);
        }

        workoutPlanRepository.deleteById(id);
    }

    // ENTITY → DTO
    private WorkoutPlanDTO convertToDTO(WorkoutPlan workoutPlan) {

        return new WorkoutPlanDTO(
                workoutPlan.getId(),
                workoutPlan.getPlanName(),
                workoutPlan.getDescription(),
                workoutPlan.getGoal(),
                workoutPlan.getDurationWeeks(),
                workoutPlan.getTrainerId()
        );
    }
}