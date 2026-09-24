package com.nibm.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "trainers")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Associated user is required")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, length = 100)
    private String specialization;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "monthly_rate_lkr", nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyRateLkr = new BigDecimal("2000.00");

    public Trainer() {
    }

    public Trainer(User user, String specialization, Integer experienceYears, String bio, BigDecimal monthlyRateLkr) {
        this.user = user;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
        this.bio = bio;
        this.monthlyRateLkr = monthlyRateLkr != null ? monthlyRateLkr : new BigDecimal("2000.00");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public BigDecimal getMonthlyRateLkr() {
        return monthlyRateLkr;
    }

    public void setMonthlyRateLkr(BigDecimal monthlyRateLkr) {
        this.monthlyRateLkr = monthlyRateLkr;
    }
}
