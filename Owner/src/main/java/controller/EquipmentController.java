package controller;

import dto.EquipmentDTO;
import model.EquipmentCategory;
import service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/owner/equipment")
@RequiredArgsConstructor
@Tag(name = "Owner – Equipment", description = "Gym equipment inventory CRUD (Owner only)")
public class EquipmentController {

    private final EquipmentService service;

    // ---------- CRUD ----------
    @GetMapping
    @Operation(summary = "List all equipment")
    public List<EquipmentDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one equipment by id")
    public EquipmentDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Filter by category (DUMBBELL, WEIGHT_PLATE, BAR, MACHINE, RESISTANCE_BAND)")
    public List<EquipmentDTO> getByCategory(@PathVariable EquipmentCategory category) {
        return service.getByCategory(category);
    }

    @GetMapping("/search")
    @Operation(summary = "Search equipment by name")
    public List<EquipmentDTO> search(@RequestParam String name) {
        return service.searchByName(name);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Filter by status e.g. AVAILABLE, UNDER_MAINTENANCE")
    public List<EquipmentDTO> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new equipment")
    public EquipmentDTO create(@Valid @RequestBody EquipmentDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update equipment details")
    public EquipmentDTO update(@PathVariable Long id, @Valid @RequestBody EquipmentDTO dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change equipment status")
    public EquipmentDTO updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    @PatchMapping("/{id}/quantity")
    @Operation(summary = "Add or remove quantity (delta = +n or -n)")
    public EquipmentDTO adjustQuantity(@PathVariable Long id, @RequestParam int delta) {
        return service.adjustQuantity(id, delta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete equipment")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // ---------- Dashboard Stats ----------
    @GetMapping("/stats")
    @Operation(summary = "Owner dashboard: total quantity + maintenance count")
    public Map<String, Long> stats() {
        return Map.of(
                "totalQuantity", service.getTotalQuantity(),
                "underMaintenance", service.getUnderMaintenanceCount()
        );
    }
}