package com.nibm.gym.service;

import com.nibm.gym.dto.PricingCalculationResponse;
import com.nibm.gym.repository.InvoiceRepository;
import com.nibm.gym.repository.MembershipRepository;
import com.nibm.gym.repository.TrainerRepository;
import com.nibm.gym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @Mock
    private MembershipRepository membershipRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private InvoiceRepository invoiceRepository;

    private MembershipService membershipService;

    @BeforeEach
    void setUp() {
        membershipService = new MembershipService(membershipRepository, userRepository, trainerRepository, invoiceRepository);
    }

    @Test
    @DisplayName("Should calculate Base Membership at 2,500 LKR when no addons selected")
    void testCalculatePricing_BaseOnly() {
        PricingCalculationResponse response = membershipService.calculatePricing(false, false);

        assertNotNull(response);
        assertEquals(new BigDecimal("2500.00"), response.getBaseFeeLkr());
        assertEquals(BigDecimal.ZERO, response.getTrainerFeeLkr());
        assertEquals(BigDecimal.ZERO, response.getTreadmillFeeLkr());
        assertEquals(new BigDecimal("2500.00"), response.getTotalMonthlyFeeLkr());
    }

    @Test
    @DisplayName("Should calculate 4,500 LKR (2,500 + 2,000) when Personal Trainer addon is selected")
    void testCalculatePricing_WithTrainer() {
        PricingCalculationResponse response = membershipService.calculatePricing(true, false);

        assertNotNull(response);
        assertEquals(new BigDecimal("2500.00"), response.getBaseFeeLkr());
        assertEquals(new BigDecimal("2000.00"), response.getTrainerFeeLkr());
        assertEquals(BigDecimal.ZERO, response.getTreadmillFeeLkr());
        assertEquals(new BigDecimal("4500.00"), response.getTotalMonthlyFeeLkr());
    }

    @Test
    @DisplayName("Should calculate 3,500 LKR (2,500 + 1,000) when Treadmill addon is selected")
    void testCalculatePricing_WithTreadmill() {
        PricingCalculationResponse response = membershipService.calculatePricing(false, true);

        assertNotNull(response);
        assertEquals(new BigDecimal("2500.00"), response.getBaseFeeLkr());
        assertEquals(BigDecimal.ZERO, response.getTrainerFeeLkr());
        assertEquals(new BigDecimal("1000.00"), response.getTreadmillFeeLkr());
        assertEquals(new BigDecimal("3500.00"), response.getTotalMonthlyFeeLkr());
    }

    @Test
    @DisplayName("Should calculate 5,500 LKR (2,500 + 2,000 + 1,000) when both Trainer and Treadmill are selected")
    void testCalculatePricing_WithTrainerAndTreadmill() {
        PricingCalculationResponse response = membershipService.calculatePricing(true, true);

        assertNotNull(response);
        assertEquals(new BigDecimal("2500.00"), response.getBaseFeeLkr());
        assertEquals(new BigDecimal("2000.00"), response.getTrainerFeeLkr());
        assertEquals(new BigDecimal("1000.00"), response.getTreadmillFeeLkr());
        assertEquals(new BigDecimal("5500.00"), response.getTotalMonthlyFeeLkr());
    }
}
