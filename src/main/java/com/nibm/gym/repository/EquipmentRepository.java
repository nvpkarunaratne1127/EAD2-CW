package com.nibm.gym.repository;

import com.nibm.gym.model.Equipment;
import com.nibm.gym.model.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByCategory(EquipmentCategory category);
    List<Equipment> findByStatus(String status);
}
