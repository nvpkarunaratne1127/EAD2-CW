package com.nibm.gym.dto;

import jakarta.validation.constraints.NotNull;

public class MembershipRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    private Long trainerId;

    private boolean hasTrainer = false;

    private boolean hasTreadmill = false;

    public MembershipRequest() {
    }

    public MembershipRequest(Long customerId, Long trainerId, boolean hasTrainer, boolean hasTreadmill) {
        this.customerId = customerId;
        this.trainerId = trainerId;
        this.hasTrainer = hasTrainer;
        this.hasTreadmill = hasTreadmill;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public boolean isHasTrainer() {
        return hasTrainer;
    }

    public void setHasTrainer(boolean hasTrainer) {
        this.hasTrainer = hasTrainer;
    }

    public boolean isHasTreadmill() {
        return hasTreadmill;
    }

    public void setHasTreadmill(boolean hasTreadmill) {
        this.hasTreadmill = hasTreadmill;
    }
}
