package com.nibm.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplement_orders")
public class SupplementOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @NotNull(message = "Supplement is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplement_id", nullable = false)
    private Supplement supplement;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "total_price_lkr", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPriceLkr;

    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus = "PAID"; // "PAID", "PENDING"

    public SupplementOrder() {
    }

    public SupplementOrder(User customer, Supplement supplement, Integer quantity, BigDecimal totalPriceLkr, String paymentStatus) {
        this.customer = customer;
        this.supplement = supplement;
        this.quantity = quantity;
        this.totalPriceLkr = totalPriceLkr;
        this.paymentStatus = paymentStatus != null ? paymentStatus : "PAID";
    }

    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
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

    public Supplement getSupplement() {
        return supplement;
    }

    public void setSupplement(Supplement supplement) {
        this.supplement = supplement;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPriceLkr() {
        return totalPriceLkr;
    }

    public void setTotalPriceLkr(BigDecimal totalPriceLkr) {
        this.totalPriceLkr = totalPriceLkr;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
