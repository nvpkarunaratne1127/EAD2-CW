package com.nibm.gym.service;

import com.nibm.gym.exception.ResourceNotFoundException;
import com.nibm.gym.model.Equipment;
import com.nibm.gym.model.EquipmentCategory;
import com.nibm.gym.repository.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Transactional(readOnly = true)
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Equipment> getEquipmentByCategory(EquipmentCategory category) {
        return equipmentRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public Equipment getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id: " + id));
    }

    public Equipment createEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public Equipment updateEquipment(Long id, Equipment details) {
        Equipment existing = getEquipmentById(id);
        existing.setName(details.getName());
        existing.setCategory(details.getCategory());
        existing.setQuantity(details.getQuantity());
        existing.setWeightSpecs(details.getWeightSpecs());
        existing.setStatus(details.getStatus());
        existing.setDescription(details.getDescription());
        return equipmentRepository.save(existing);
    }

    public void deleteEquipment(Long id) {
        Equipment existing = getEquipmentById(id);
        equipmentRepository.delete(existing);
    }
}
