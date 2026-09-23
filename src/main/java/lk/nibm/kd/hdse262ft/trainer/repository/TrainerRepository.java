package lk.nibm.kd.hdse262ft.trainer.repository;


import lk.nibm.kd.hdse262ft.trainer.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

}