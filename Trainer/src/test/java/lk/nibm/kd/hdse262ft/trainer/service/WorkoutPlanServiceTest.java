package lk.nibm.kd.hdse262ft.trainer.service;

import lk.nibm.kd.hdse262ft.trainer.dto.WorkoutPlanDTO;
import lk.nibm.kd.hdse262ft.trainer.entity.WorkoutPlan;
import lk.nibm.kd.hdse262ft.trainer.repository.WorkoutPlanRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutPlanServiceTest {

    @Mock
    private WorkoutPlanRepository workoutPlanRepository;

    @InjectMocks
    private WorkoutPlanService workoutPlanService;


    // CREATE TEST
    @Test
    void createWorkoutPlan() {

        WorkoutPlanDTO dto = new WorkoutPlanDTO(
                null,
                "Weight Loss Plan",
                "Beginner workout plan",
                "Weight Loss",
                8,
                1L
        );

        WorkoutPlan savedPlan = new WorkoutPlan();

        savedPlan.setPlanName("Weight Loss Plan");
        savedPlan.setDescription("Beginner workout plan");
        savedPlan.setGoal("Weight Loss");
        savedPlan.setDurationWeeks(8);
        savedPlan.setTrainerId(1L);

        when(workoutPlanRepository.save(any(WorkoutPlan.class)))
                .thenReturn(savedPlan);

        WorkoutPlanDTO result =
                workoutPlanService.createWorkoutPlan(dto);

        assertNotNull(result);
        assertEquals("Weight Loss Plan", result.getPlanName());
        assertEquals("Weight Loss", result.getGoal());

        verify(workoutPlanRepository, times(1))
                .save(any(WorkoutPlan.class));
    }


    // GET BY ID TEST
    @Test
    void getWorkoutPlanById() {

        WorkoutPlan plan = new WorkoutPlan();

        plan.setPlanName("Weight Loss Plan");
        plan.setDescription("Beginner workout plan");
        plan.setGoal("Weight Loss");
        plan.setDurationWeeks(8);
        plan.setTrainerId(1L);

        when(workoutPlanRepository.findById(1L))
                .thenReturn(Optional.of(plan));

        WorkoutPlanDTO result =
                workoutPlanService.getWorkoutPlanById(1L);

        assertNotNull(result);
        assertEquals("Weight Loss Plan", result.getPlanName());
        assertEquals("Weight Loss", result.getGoal());

        verify(workoutPlanRepository, times(1))
                .findById(1L);
    }


    // NOT FOUND TEST
    @Test
    void getWorkoutPlanByIdWhenNotFound() {

        when(workoutPlanRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> workoutPlanService.getWorkoutPlanById(1L)
        );

        verify(workoutPlanRepository, times(1))
                .findById(1L);
    }


    // DELETE TEST
    @Test
    void deleteWorkoutPlan() {

        when(workoutPlanRepository.existsById(1L))
                .thenReturn(true);

        workoutPlanService.deleteWorkoutPlan(1L);

        verify(workoutPlanRepository, times(1))
                .existsById(1L);

        verify(workoutPlanRepository, times(1))
                .deleteById(1L);
    }
}