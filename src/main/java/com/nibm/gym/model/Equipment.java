package com.nibm.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Equipment name is required")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EquipmentCategory category;

    @Min(value = 0, message = "Quantity must be 0 or greater")
    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "weight_specs", length = 100)
    private String weightSpecs;

    @Column(nullable = false, length = 30)
    private String status; // "AVAILABLE", "MAINTENANCE", "IN_USE"

    @Column(columnDefinition = "TEXT")
    private String description;

    public Equipment() {
    }

    public Equipment(String name, EquipmentCategory category, Integer quantity, String weightSpecs, String status, String description) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.weightSpecs = weightSpecs;
        this.status = status;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EquipmentCategory getCategory() {
        return category;
    }

    public void setCategory(EquipmentCategory category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getWeightSpecs() {
        return weightSpecs;
    }

    public void setWeightSpecs(String weightSpecs) {
        this.weightSpecs = weightSpecs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
