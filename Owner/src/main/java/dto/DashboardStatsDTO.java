package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardStatsDTO {
    private long totalEquipmentQuantity;
    private long underMaintenanceCount;
    private long totalInvoices;
    private double totalRevenueLkr;
    private double pendingRevenueLkr;
    private long pendingInvoiceCount;
}