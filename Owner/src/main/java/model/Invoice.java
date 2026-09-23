package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String invoiceNo;             // e.g. "INV-2026-0001"

    @NotBlank
    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private Long customerId;

    @NotBlank
    @Column(nullable = false)
    private String description;           // e.g. "Monthly membership - PREMIUM"

    @Min(0)
    @Column(nullable = false)
    private double amountLkr;

    @Column(nullable = false)
    private LocalDate issuedDate;

    /** PAID, PENDING, OVERDUE, CANCELLED */
    @Column(nullable = false)
    @Builder.Default
    private String paymentStatus = "PENDING";

    /** Optional: which module generated the invoice */
    private String category;              // MEMBERSHIP, SUPPLEMENT, TRAINER, OTHER
}
