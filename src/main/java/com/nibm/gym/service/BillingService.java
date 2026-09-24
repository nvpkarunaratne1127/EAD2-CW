package com.nibm.gym.service;

import com.nibm.gym.model.Invoice;
import com.nibm.gym.repository.EquipmentRepository;
import com.nibm.gym.repository.InvoiceRepository;
import com.nibm.gym.repository.MembershipRepository;
import com.nibm.gym.repository.SupplementRepository;
import com.nibm.gym.repository.TrainerRepository;
import com.nibm.gym.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BillingService {

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final MembershipRepository membershipRepository;
    private final EquipmentRepository equipmentRepository;
    private final SupplementRepository supplementRepository;

    public BillingService(InvoiceRepository invoiceRepository,
                          UserRepository userRepository,
                          TrainerRepository trainerRepository,
                          MembershipRepository membershipRepository,
                          EquipmentRepository equipmentRepository,
                          SupplementRepository supplementRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.membershipRepository = membershipRepository;
        this.equipmentRepository = equipmentRepository;
        this.supplementRepository = supplementRepository;
    }

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAllByOrderByInvoiceDateDesc();
    }

    @Transactional(readOnly = true)
    public List<Invoice> getCustomerInvoices(Long customerId) {
        return invoiceRepository.findByCustomerIdOrderByInvoiceDateDesc(customerId);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getSystemStats() {
        Map<String, Object> stats = new HashMap<>();

        BigDecimal totalRevenue = invoiceRepository.calculateTotalPaidRevenue();
        stats.put("totalRevenueLkr", totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
        stats.put("totalUsers", userRepository.count());
        stats.put("totalTrainers", trainerRepository.count());
        stats.put("activeMemberships", membershipRepository.findByStatus("ACTIVE").size());
        stats.put("totalEquipmentItems", equipmentRepository.count());
        stats.put("totalSupplements", supplementRepository.count());
        stats.put("totalInvoices", invoiceRepository.count());

        return stats;
    }
}
