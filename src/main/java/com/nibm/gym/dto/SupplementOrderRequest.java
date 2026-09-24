package com.nibm.gym.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SupplementOrderRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Supplement ID is required")
    private Long supplementId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public SupplementOrderRequest() {
    }

    public SupplementOrderRequest(Long customerId, Long supplementId, Integer quantity) {
        this.customerId = customerId;
        this.supplementId = supplementId;
        this.quantity = quantity;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSupplementId() {
        return supplementId;
    }

    public void setSupplementId(Long supplementId) {
        this.supplementId = supplementId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
