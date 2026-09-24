package com.nibm.gym.repository;

import com.nibm.gym.model.Trainer;
import com.nibm.gym.model.User;
import com.nibm.gym.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    Optional<WorkoutPlan> findByCustomer(User customer);
    Optional<WorkoutPlan> findByCustomerId(Long customerId);
    List<WorkoutPlan> findByTrainer(Trainer trainer);
    List<WorkoutPlan> findByTrainerId(Long trainerId);
}
