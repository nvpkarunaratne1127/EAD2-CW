package com.nibm.gym.repository;

import com.nibm.gym.model.SupplementOrder;
import com.nibm.gym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SupplementOrderRepository extends JpaRepository<SupplementOrder, Long> {
    List<SupplementOrder> findByCustomer(User customer);
    List<SupplementOrder> findByCustomerId(Long customerId);
}
