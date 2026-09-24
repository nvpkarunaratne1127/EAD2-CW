package com.nibm.gym.repository;

import com.nibm.gym.model.Membership;
import com.nibm.gym.model.Trainer;
import com.nibm.gym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findByCustomerAndStatus(User customer, String status);
    List<Membership> findByCustomerId(Long customerId);
    List<Membership> findByTrainer(Trainer trainer);
    List<Membership> findByTrainerId(Long trainerId);
    List<Membership> findByStatus(String status);
}
