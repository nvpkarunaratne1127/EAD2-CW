package com.nibm.gym.service;

import com.nibm.gym.dto.WorkoutPlanRequest;
import com.nibm.gym.exception.ResourceNotFoundException;
import com.nibm.gym.model.Trainer;
import com.nibm.gym.model.User;
import com.nibm.gym.model.WorkoutPlan;
import com.nibm.gym.repository.TrainerRepository;
import com.nibm.gym.repository.UserRepository;
import com.nibm.gym.repository.WorkoutPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository,
                              TrainerRepository trainerRepository,
                              UserRepository userRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Optional<WorkoutPlan> getPlanByCustomerId(Long customerId) {
        return workoutPlanRepository.findByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public List<WorkoutPlan> getPlansByTrainerId(Long trainerId) {
        return workoutPlanRepository.findByTrainerId(trainerId);
    }

    public WorkoutPlan saveOrUpdatePlan(WorkoutPlanRequest request) {
        Trainer trainer = trainerRepository.findById(request.getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + request.getTrainerId()));

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));

        Optional<WorkoutPlan> existing = workoutPlanRepository.findByCustomer(customer);
        WorkoutPlan plan;
        if (existing.isPresent()) {
            plan = existing.get();
            plan.setTrainer(trainer);
            plan.setRoutineNotes(request.getRoutineNotes());
            plan.setDietNotes(request.getDietNotes());
            plan.setProgressNotes(request.getProgressNotes());
        } else {
            plan = new WorkoutPlan(trainer, customer, request.getRoutineNotes(), request.getDietNotes(), request.getProgressNotes());
        }

        return workoutPlanRepository.save(plan);
    }
}
