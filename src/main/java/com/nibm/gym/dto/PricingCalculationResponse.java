package com.nibm.gym.dto;

import java.math.BigDecimal;

public class PricingCalculationResponse {

    private BigDecimal baseFeeLkr;
    private BigDecimal trainerFeeLkr;
    private BigDecimal treadmillFeeLkr;
    private BigDecimal totalMonthlyFeeLkr;
    private String breakdownExplanation;

    public PricingCalculationResponse() {
    }

    public PricingCalculationResponse(BigDecimal baseFeeLkr, BigDecimal trainerFeeLkr, BigDecimal treadmillFeeLkr, BigDecimal totalMonthlyFeeLkr, String breakdownExplanation) {
        this.baseFeeLkr = baseFeeLkr;
        this.trainerFeeLkr = trainerFeeLkr;
        this.treadmillFeeLkr = treadmillFeeLkr;
        this.totalMonthlyFeeLkr = totalMonthlyFeeLkr;
        this.breakdownExplanation = breakdownExplanation;
    }

    public BigDecimal getBaseFeeLkr() {
        return baseFeeLkr;
    }

    public void setBaseFeeLkr(BigDecimal baseFeeLkr) {
        this.baseFeeLkr = baseFeeLkr;
    }

    public BigDecimal getTrainerFeeLkr() {
        return trainerFeeLkr;
    }

    public void setTrainerFeeLkr(BigDecimal trainerFeeLkr) {
        this.trainerFeeLkr = trainerFeeLkr;
    }

    public BigDecimal getTreadmillFeeLkr() {
        return treadmillFeeLkr;
    }

    public void setTreadmillFeeLkr(BigDecimal treadmillFeeLkr) {
        this.treadmillFeeLkr = treadmillFeeLkr;
    }

    public BigDecimal getTotalMonthlyFeeLkr() {
        return totalMonthlyFeeLkr;
    }

    public void setTotalMonthlyFeeLkr(BigDecimal totalMonthlyFeeLkr) {
        this.totalMonthlyFeeLkr = totalMonthlyFeeLkr;
    }

    public String getBreakdownExplanation() {
        return breakdownExplanation;
    }

    public void setBreakdownExplanation(String breakdownExplanation) {
        this.breakdownExplanation = breakdownExplanation;
    }
}
