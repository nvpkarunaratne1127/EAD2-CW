package com.nibm.gym.dto;

import jakarta.validation.constraints.NotNull;

public class WorkoutPlanRequest {

    @NotNull(message = "Trainer ID is required")
    private Long trainerId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    private String routineNotes;
    private String dietNotes;
    private String progressNotes;

    public WorkoutPlanRequest() {
    }

    public WorkoutPlanRequest(Long trainerId, Long customerId, String routineNotes, String dietNotes, String progressNotes) {
        this.trainerId = trainerId;
        this.customerId = customerId;
        this.routineNotes = routineNotes;
        this.dietNotes = dietNotes;
        this.progressNotes = progressNotes;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getRoutineNotes() {
        return routineNotes;
    }

    public void setRoutineNotes(String routineNotes) {
        this.routineNotes = routineNotes;
    }

    public String getDietNotes() {
        return dietNotes;
    }

    public void setDietNotes(String dietNotes) {
        this.dietNotes = dietNotes;
    }

    public String getProgressNotes() {
        return progressNotes;
    }

    public void setProgressNotes(String progressNotes) {
        this.progressNotes = progressNotes;
    }
}
