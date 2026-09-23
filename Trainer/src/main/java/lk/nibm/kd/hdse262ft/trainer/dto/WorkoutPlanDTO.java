package lk.nibm.kd.hdse262ft.trainer.dto;

public class WorkoutPlanDTO {

    private Long id;
    private String planName;
    private String description;
    private String goal;
    private int durationWeeks;
    private Long trainerId;

    public WorkoutPlanDTO() {
    }

    public WorkoutPlanDTO(Long id, String planName, String description,
                          String goal, int durationWeeks, Long trainerId) {
        this.id = id;
        this.planName = planName;
        this.description = description;
        this.goal = goal;
        this.durationWeeks = durationWeeks;
        this.trainerId = trainerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(int durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }
}