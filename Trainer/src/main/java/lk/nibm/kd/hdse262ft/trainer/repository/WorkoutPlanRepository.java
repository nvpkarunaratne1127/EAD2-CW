package lk.nibm.kd.hdse262ft.trainer.repository;

import lk.nibm.kd.hdse262ft.trainer.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

}