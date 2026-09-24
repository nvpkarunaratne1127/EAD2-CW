package com.nibm.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_no", nullable = false, unique = true, length = 50)
    private String invoiceNo;

    @NotNull(message = "Customer is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(name = "amount_lkr", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountLkr;

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus = "PAID"; // "PAID", "PENDING"

    @Column(name = "invoice_date", updatable = false)
    private LocalDateTime invoiceDate;

    public Invoice() {
    }

    public Invoice(String invoiceNo, User customer, String description, BigDecimal amountLkr, String paymentStatus) {
        this.invoiceNo = invoiceNo;
        this.customer = customer;
        this.description = description;
        this.amountLkr = amountLkr;
        this.paymentStatus = paymentStatus != null ? paymentStatus : "PAID";
    }

    @PrePersist
    protected void onCreate() {
        this.invoiceDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmountLkr() {
        return amountLkr;
    }

    public void setAmountLkr(BigDecimal amountLkr) {
        this.amountLkr = amountLkr;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
