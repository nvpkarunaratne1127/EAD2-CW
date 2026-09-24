package com.nibm.gym.service;

import com.nibm.gym.dto.MembershipRequest;
import com.nibm.gym.dto.PricingCalculationResponse;
import com.nibm.gym.exception.BadRequestException;
import com.nibm.gym.exception.ResourceNotFoundException;
import com.nibm.gym.model.Invoice;
import com.nibm.gym.model.Membership;
import com.nibm.gym.model.Trainer;
import com.nibm.gym.model.User;
import com.nibm.gym.repository.InvoiceRepository;
import com.nibm.gym.repository.MembershipRepository;
import com.nibm.gym.repository.TrainerRepository;
import com.nibm.gym.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MembershipService {

    public static final BigDecimal BASE_PRICE_LKR = new BigDecimal("2500.00");
    public static final BigDecimal TRAINER_ADDON_LKR = new BigDecimal("2000.00");
    public static final BigDecimal TREADMILL_ADDON_LKR = new BigDecimal("1000.00");

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final InvoiceRepository invoiceRepository;

    public MembershipService(MembershipRepository membershipRepository,
                             UserRepository userRepository,
                             TrainerRepository trainerRepository,
                             InvoiceRepository invoiceRepository) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public PricingCalculationResponse calculatePricing(boolean hasTrainer, boolean hasTreadmill) {
        BigDecimal base = BASE_PRICE_LKR;
        BigDecimal trainerFee = hasTrainer ? TRAINER_ADDON_LKR : BigDecimal.ZERO;
        BigDecimal treadmillFee = hasTreadmill ? TREADMILL_ADDON_LKR : BigDecimal.ZERO;
        BigDecimal total = base.add(trainerFee).add(treadmillFee);

        StringBuilder explanation = new StringBuilder();
        explanation.append("Base Membership: 2,500 LKR");
        if (hasTrainer) {
            explanation.append(" + Personal Trainer: 2,000 LKR (Subtotal 4,500 LKR)");
        }
        if (hasTreadmill) {
            explanation.append(" + Treadmill/Cardio Pass: 1,000 LKR");
        }
        explanation.append(" = Total: ").append(total).append(" LKR/month");

        return new PricingCalculationResponse(base, trainerFee, treadmillFee, total, explanation.toString());
    }

    public Membership subscribeOrUpdate(MembershipRequest request) {
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));

        Trainer trainer = null;
        if (request.isHasTrainer()) {
            if (request.getTrainerId() == null) {
                // If trainer not specified, find first available trainer
                List<Trainer> trainers = trainerRepository.findAll();
                if (!trainers.isEmpty()) {
                    trainer = trainers.get(0);
                }
            } else {
                trainer = trainerRepository.findById(request.getTrainerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + request.getTrainerId()));
            }
        }

        // Check if customer already has an active membership
        Optional<Membership> existingOpt = membershipRepository.findByCustomerAndStatus(customer, "ACTIVE");
        Membership membership;
        if (existingOpt.isPresent()) {
            membership = existingOpt.get();
            membership.setTrainer(trainer);
            membership.setHasTrainer(request.isHasTrainer());
            membership.setHasTreadmill(request.isHasTreadmill());
            membership.calculateTotal();
            membership.setStartDate(LocalDate.now());
            membership.setEndDate(LocalDate.now().plusMonths(1));
        } else {
            membership = new Membership(customer, trainer, request.isHasTrainer(), request.isHasTreadmill());
        }

        Membership saved = membershipRepository.save(membership);

        // Generate Invoice for Membership
        String invoiceNo = "INV-MEM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String description = "Gym Membership: Base (2500 LKR)" +
                (saved.isHasTrainer() ? " + Personal Trainer (" + (saved.getTrainer() != null ? saved.getTrainer().getUser().getFullName() : "Assigned") + ")" : "") +
                (saved.isHasTreadmill() ? " + Treadmill Access" : "");
        Invoice invoice = new Invoice(invoiceNo, customer, description, saved.getTotalMonthlyFeeLkr(), "PAID");
        invoiceRepository.save(invoice);

        return saved;
    }

    @Transactional(readOnly = true)
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Membership getMembershipById(Long id) {
        return membershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Optional<Membership> getActiveMembershipByCustomerId(Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        return membershipRepository.findByCustomerAndStatus(customer, "ACTIVE");
    }

    public void cancelMembership(Long id) {
        Membership membership = getMembershipById(id);
        membership.setStatus("CANCELLED");
        membershipRepository.save(membership);
    }
}
