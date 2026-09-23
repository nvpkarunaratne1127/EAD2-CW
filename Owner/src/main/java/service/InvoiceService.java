package service;

import dto.InvoiceDTO;
import exception.BadRequestException;
import exception.ResourceNotFoundException;
import model.Invoice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.InvoiceRepository;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class InvoiceService {

    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    // ---------- READ ----------
    public List<InvoiceDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public InvoiceDTO getById(Long id) {
        Invoice inv = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + id));
        return toDTO(inv);
    }

    public List<InvoiceDTO> getByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<InvoiceDTO> getByStatus(String status) {
        return repository.findByPaymentStatus(status).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    // ---------- CREATE ----------
    @Transactional
    public InvoiceDTO create(InvoiceDTO dto) {
        if (dto.getAmountLkr() < 0) {
            throw new BadRequestException("Amount cannot be negative");
        }
        Invoice inv = Invoice.builder()
                .invoiceNo(generateInvoiceNo())
                .customerName(dto.getCustomerName())
                .customerId(dto.getCustomerId())
                .description(dto.getDescription())
                .amountLkr(dto.getAmountLkr())
                .issuedDate(dto.getIssuedDate() != null ? dto.getIssuedDate() : LocalDate.now())
                .paymentStatus(dto.getPaymentStatus() != null ? dto.getPaymentStatus() : "PENDING")
                .category(dto.getCategory())
                .build();
        return toDTO(repository.save(inv));
    }

    // ---------- UPDATE ----------
    @Transactional
    public InvoiceDTO update(Long id, InvoiceDTO dto) {
        Invoice inv = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + id));
        inv.setCustomerName(dto.getCustomerName());
        inv.setCustomerId(dto.getCustomerId());
        inv.setDescription(dto.getDescription());
        inv.setAmountLkr(dto.getAmountLkr());
        if (dto.getIssuedDate() != null) inv.setIssuedDate(dto.getIssuedDate());
        if (dto.getPaymentStatus() != null) inv.setPaymentStatus(dto.getPaymentStatus());
        inv.setCategory(dto.getCategory());
        return toDTO(repository.save(inv));
    }

    @Transactional
    public InvoiceDTO markPaid(Long id) {
        Invoice inv = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + id));
        inv.setPaymentStatus("PAID");
        return toDTO(repository.save(inv));
    }

    // ---------- DELETE ----------
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Invoice not found: " + id);
        }
        repository.deleteById(id);
    }

    // ---------- STATS ----------
    public double getTotalRevenue() {
        return repository.calculateTotalPaidRevenue();
    }

    public double getPendingRevenue() {
        return repository.calculatePendingRevenue();
    }

    public long getPendingCount() {
        return repository.countByPaymentStatus("PENDING");
    }

    public long getTotalCount() {
        return repository.count();
    }

    // ---------- HELPERS ----------
    private String generateInvoiceNo() {
        long seq = repository.count() + 1;
        String candidate;
        do {
            candidate = String.format("INV-%d-%04d", Year.now().getValue(), seq++);
        } while (repository.existsByInvoiceNo(candidate));
        return candidate;
    }

    private InvoiceDTO toDTO(Invoice i) {
        return InvoiceDTO.builder()
                .id(i.getId())
                .invoiceNo(i.getInvoiceNo())
                .customerName(i.getCustomerName())
                .customerId(i.getCustomerId())
                .description(i.getDescription())
                .amountLkr(i.getAmountLkr())
                .issuedDate(i.getIssuedDate())
                .paymentStatus(i.getPaymentStatus())
                .category(i.getCategory())
                .build();
    }
}
