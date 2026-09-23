package repository;

import model.Equipment;
import model.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    List<Equipment> findByCategory(EquipmentCategory category);

    List<Equipment> findByStatus(String status);

    List<Equipment> findByNameContainingIgnoreCase(String name);

    /** Total items in the gym (sum of quantity across all rows) */
    @Query("SELECT COALESCE(SUM(e.quantity), 0) FROM Equipment e")
    long calculateTotalQuantity();

    /** Count of items flagged UNDER_MAINTENANCE */
    long countByStatus(String status);
}