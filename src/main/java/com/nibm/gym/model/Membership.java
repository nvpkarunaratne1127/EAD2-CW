package com.nibm.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Column(name = "base_fee_lkr", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseFeeLkr = new BigDecimal("2500.00");

    @Column(name = "has_trainer", nullable = false)
    private boolean hasTrainer = false;

    @Column(name = "has_treadmill", nullable = false)
    private boolean hasTreadmill = false;

    @Column(name = "total_monthly_fee_lkr", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalMonthlyFeeLkr;

    @Column(nullable = false, length = 20)
    private String status = "ACTIVE"; // "ACTIVE", "PENDING", "EXPIRED"

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Membership() {
    }

    public Membership(User customer, Trainer trainer, boolean hasTrainer, boolean hasTreadmill) {
        this.customer = customer;
        this.trainer = trainer;
        this.hasTrainer = hasTrainer;
        this.hasTreadmill = hasTreadmill;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusMonths(1);
        calculateTotal();
    }

    public void calculateTotal() {
        BigDecimal total = new BigDecimal("2500.00"); // Base price is 2500 LKR
        if (this.hasTrainer) {
            total = total.add(new BigDecimal("2000.00")); // +2000 LKR (becomes 4500 LKR)
        }
        if (this.hasTreadmill) {
            total = total.add(new BigDecimal("1000.00")); // +1000 LKR treadmill pass
        }
        this.totalMonthlyFeeLkr = total;
    }

    @PrePersist
    @PreUpdate
    protected void onSave() {
        calculateTotal();
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        if (endDate == null) {
            endDate = startDate.plusMonths(1);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public BigDecimal getBaseFeeLkr() {
        return baseFeeLkr;
    }

    public void setBaseFeeLkr(BigDecimal baseFeeLkr) {
        this.baseFeeLkr = baseFeeLkr;
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

    public BigDecimal getTotalMonthlyFeeLkr() {
        return totalMonthlyFeeLkr;
    }

    public void setTotalMonthlyFeeLkr(BigDecimal totalMonthlyFeeLkr) {
        this.totalMonthlyFeeLkr = totalMonthlyFeeLkr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
