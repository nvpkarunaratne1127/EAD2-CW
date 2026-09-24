package com.nibm.gym.controller;

import com.nibm.gym.model.Invoice;
import com.nibm.gym.service.BillingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/billing")
@Tag(name = "Billing & Financials", description = "Endpoints for invoices, payment receipts, and gym revenue statistics in LKR")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/invoices")
    @Operation(summary = "Get all invoices", description = "Retrieves all billing records across memberships and supplement sales")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(billingService.getAllInvoices());
    }

    @GetMapping("/invoices/customer/{customerId}")
    @Operation(summary = "Get customer invoices", description = "Retrieves all invoices for a specific customer")
    public ResponseEntity<List<Invoice>> getCustomerInvoices(@PathVariable Long customerId) {
        return ResponseEntity.ok(billingService.getCustomerInvoices(customerId));
    }

    @GetMapping("/stats")
    @Operation(summary = "Get system dashboard statistics", description = "Retrieves revenue metrics in LKR, total members, trainers, and inventory count")
    public ResponseEntity<Map<String, Object>> getSystemStats() {
        return ResponseEntity.ok(billingService.getSystemStats());
    }
}
