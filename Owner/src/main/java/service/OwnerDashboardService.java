package service;

import dto.DashboardStatsDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.EquipmentRepository;
import repository.InvoiceRepository;

@Service
@Transactional(readOnly = true)
public class OwnerDashboardService {

    private final EquipmentRepository equipmentRepo;
    private final InvoiceRepository invoiceRepo;

    public OwnerDashboardService(EquipmentRepository equipmentRepo,
                                 InvoiceRepository invoiceRepo) {
        this.equipmentRepo = equipmentRepo;
        this.invoiceRepo = invoiceRepo;
    }

    public DashboardStatsDTO getDashboardStats() {
        return DashboardStatsDTO.builder()
                .totalEquipmentQuantity(equipmentRepo.calculateTotalQuantity())
                .underMaintenanceCount(equipmentRepo.countByStatus("UNDER_MAINTENANCE"))
                .totalInvoices(invoiceRepo.count())
                .totalRevenueLkr(invoiceRepo.calculateTotalPaidRevenue())
                .pendingRevenueLkr(invoiceRepo.calculatePendingRevenue())
                .pendingInvoiceCount(invoiceRepo.countByPaymentStatus("PENDING"))
                .build();
    }
}

