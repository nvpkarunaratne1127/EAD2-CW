package com.nibm.gym.controller;

import com.nibm.gym.model.Equipment;
import com.nibm.gym.model.EquipmentCategory;
import com.nibm.gym.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@Tag(name = "Equipment Resource", description = "Endpoints for managing gym equipment: dumbbells, weight plates, bars, machines, resistance bands")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    @Operation(summary = "Get all equipment", description = "Retrieves all equipment items, optionally filtered by category (DUMBBELL, WEIGHT_PLATE, BAR, MACHINE, RESISTANCE_BAND)")
    public ResponseEntity<List<Equipment>> getAllEquipment(@RequestParam(required = false) EquipmentCategory category) {
        if (category != null) {
            return ResponseEntity.ok(equipmentService.getEquipmentByCategory(category));
        }
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get equipment by ID", description = "Retrieves an equipment item by its unique ID")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.getEquipmentById(id));
    }

    @PostMapping
    @Operation(summary = "Create equipment", description = "Adds a new gym equipment item into the inventory [201 Created]")
    public ResponseEntity<Equipment> createEquipment(@Valid @RequestBody Equipment equipment) {
        Equipment created = equipmentService.createEquipment(equipment);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update equipment", description = "Updates details, quantity, or maintenance status of an equipment item [200 OK]")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable Long id, @Valid @RequestBody Equipment equipment) {
        Equipment updated = equipmentService.updateEquipment(id, equipment);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete equipment", description = "Removes an equipment item from the inventory [204 No Content]")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
