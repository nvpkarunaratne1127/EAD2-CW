package dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InvoiceDTO {
    private Long id;

    private String invoiceNo;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotNull(message = "Customer id is required")
    private Long customerId;

    @NotBlank(message = "Description is required")
    private String description;

    @Min(value = 0, message = "Amount cannot be negative")
    private double amountLkr;

    private LocalDate issuedDate;

    private String paymentStatus;

    private String category;
}