package service;

import dto.EquipmentDTO;
import exception.BadRequestException;
import exception.ResourceNotFoundException;
import model.Equipment;
import model.EquipmentCategory;
import repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipmentService {

    private final EquipmentRepository repository;

    // ---------- READ ----------
    public List<EquipmentDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EquipmentDTO getById(Long id) {
        Equipment eq = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found: " + id));
        return toDTO(eq);
    }

    public List<EquipmentDTO> getByCategory(EquipmentCategory category) {
        return repository.findByCategory(category).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<EquipmentDTO> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<EquipmentDTO> getByStatus(String status) {
        return repository.findByStatus(status).stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ---------- CREATE ----------
    @Transactional
    public EquipmentDTO create(EquipmentDTO dto) {
        if (dto.getQuantity() < 0) {
            throw new BadRequestException("Quantity cannot be negative");
        }
        Equipment eq = Equipment.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .quantity(dto.getQuantity())
                .weightSpecs(dto.getWeightSpecs())
                .status(dto.getStatus() != null ? dto.getStatus() : "AVAILABLE")
                .location(dto.getLocation())
                .build();
        return toDTO(repository.save(eq));
    }

    // ---------- UPDATE ----------
    @Transactional
    public EquipmentDTO update(Long id, EquipmentDTO dto) {
        Equipment eq = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found: " + id));
        eq.setName(dto.getName());
        eq.setCategory(dto.getCategory());
        eq.setQuantity(dto.getQuantity());
        eq.setWeightSpecs(dto.getWeightSpecs());
        if (dto.getStatus() != null) eq.setStatus(dto.getStatus());
        eq.setLocation(dto.getLocation());
        return toDTO(repository.save(eq));
    }

    /** Quick status change — e.g. mark as UNDER_MAINTENANCE */
    @Transactional
    public EquipmentDTO updateStatus(Long id, String status) {
        Equipment eq = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found: " + id));
        eq.setStatus(status);
        return toDTO(repository.save(eq));
    }

    /** Add/remove quantity atomically (positive = add stock, negative = deduct) */
    @Transactional
    public EquipmentDTO adjustQuantity(Long id, int delta) {
        Equipment eq = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found: " + id));
        int newQty = eq.getQuantity() + delta;
        if (newQty < 0) {
            throw new BadRequestException("Not enough stock to deduct " + (-delta));
        }
        eq.setQuantity(newQty);
        return toDTO(repository.save(eq));
    }

    // ---------- DELETE ----------
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Equipment not found: " + id);
        }
        repository.deleteById(id);
    }

    // ---------- STATS (owner dashboard) ----------
    public long getTotalQuantity() {
        return repository.calculateTotalQuantity();
    }

    public long getUnderMaintenanceCount() {
        return repository.countByStatus("UNDER_MAINTENANCE");
    }

    // ---------- MAPPER ----------
    private EquipmentDTO toDTO(Equipment e) {
        return EquipmentDTO.builder()
                .id(e.getId())
                .name(e.getName())
                .category(e.getCategory())
                .quantity(e.getQuantity())
                .weightSpecs(e.getWeightSpecs())
                .status(e.getStatus())
                .location(e.getLocation())
                .build();
    }
}
