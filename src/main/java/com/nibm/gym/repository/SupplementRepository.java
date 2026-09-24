package com.nibm.gym.repository;

import com.nibm.gym.model.Supplement;
import com.nibm.gym.model.SupplementCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SupplementRepository extends JpaRepository<Supplement, Long> {
    List<SupplementCategory> findDistinctCategoryBy();
    List<Supplement> findByCategory(SupplementCategory category);
}
