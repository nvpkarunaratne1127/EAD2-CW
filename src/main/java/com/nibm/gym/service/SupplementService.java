package com.nibm.gym.service;

import com.nibm.gym.dto.SupplementOrderRequest;
import com.nibm.gym.exception.BadRequestException;
import com.nibm.gym.exception.ResourceNotFoundException;
import com.nibm.gym.model.Invoice;
import com.nibm.gym.model.Supplement;
import com.nibm.gym.model.SupplementCategory;
import com.nibm.gym.model.SupplementOrder;
import com.nibm.gym.model.User;
import com.nibm.gym.repository.InvoiceRepository;
import com.nibm.gym.repository.SupplementOrderRepository;
import com.nibm.gym.repository.SupplementRepository;
import com.nibm.gym.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SupplementService {

    private final SupplementRepository supplementRepository;
    private final SupplementOrderRepository supplementOrderRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;

    public SupplementService(SupplementRepository supplementRepository,
                             SupplementOrderRepository supplementOrderRepository,
                             UserRepository userRepository,
                             InvoiceRepository invoiceRepository) {
        this.supplementRepository = supplementRepository;
        this.supplementOrderRepository = supplementOrderRepository;
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional(readOnly = true)
    public List<Supplement> getAllSupplements() {
        return supplementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Supplement> getSupplementsByCategory(SupplementCategory category) {
        return supplementRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public Supplement getSupplementById(Long id) {
        return supplementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplement not found with id: " + id));
    }

    public Supplement createSupplement(Supplement supplement) {
        return supplementRepository.save(supplement);
    }

    public Supplement updateSupplement(Long id, Supplement details) {
        Supplement existing = getSupplementById(id);
        existing.setName(details.getName());
        existing.setCategory(details.getCategory());
        existing.setBrand(details.getBrand());
        existing.setPriceLkr(details.getPriceLkr());
        existing.setStockQuantity(details.getStockQuantity());
        existing.setServingSize(details.getServingSize());
        existing.setDescription(details.getDescription());
        return supplementRepository.save(existing);
    }

    public void deleteSupplement(Long id) {
        Supplement existing = getSupplementById(id);
        supplementRepository.delete(existing);
    }

    public SupplementOrder placeOrder(SupplementOrderRequest request) {
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));

        Supplement supplement = getSupplementById(request.getSupplementId());

        if (supplement.getStockQuantity() < request.getQuantity()) {
            throw new BadRequestException("Insufficient stock for " + supplement.getName() +
                    ". Available: " + supplement.getStockQuantity() + ", requested: " + request.getQuantity());
        }

        // Deduct stock
        supplement.setStockQuantity(supplement.getStockQuantity() - request.getQuantity());
        supplementRepository.save(supplement);

        // Calculate total in LKR
        BigDecimal totalPrice = supplement.getPriceLkr().multiply(BigDecimal.valueOf(request.getQuantity()));

        SupplementOrder order = new SupplementOrder(customer, supplement, request.getQuantity(), totalPrice, "PAID");
        SupplementOrder savedOrder = supplementOrderRepository.save(order);

        // Generate Invoice
        String invoiceNo = "INV-SUP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String description = "Supplement Purchase: " + supplement.getName() + " (" + request.getQuantity() + " unit(s))";
        Invoice invoice = new Invoice(invoiceNo, customer, description, totalPrice, "PAID");
        invoiceRepository.save(invoice);

        return savedOrder;
    }

    @Transactional(readOnly = true)
    public List<SupplementOrder> getCustomerOrders(Long customerId) {
        return supplementOrderRepository.findByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public List<SupplementOrder> getAllOrders() {
        return supplementOrderRepository.findAll();
    }
}
