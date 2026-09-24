package com.nibm.gym.controller;

import com.nibm.gym.dto.MembershipRequest;
import com.nibm.gym.dto.PricingCalculationResponse;
import com.nibm.gym.model.Membership;
import com.nibm.gym.service.MembershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@Tag(name = "Membership & Pricing Resource", description = "Endpoints for membership subscriptions and dynamic LKR fee calculation")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/calculate")
    @Operation(summary = "Calculate dynamic membership price",
            description = "Calculates monthly fee in LKR based on chosen options: Base (2500 LKR) + Trainer (+2000 LKR -> 4500 LKR) + Treadmill (+1000 LKR)")
    public ResponseEntity<PricingCalculationResponse> calculatePrice(
            @RequestParam(defaultValue = "false") boolean hasTrainer,
            @RequestParam(defaultValue = "false") boolean hasTreadmill) {
        PricingCalculationResponse response = membershipService.calculatePricing(hasTrainer, hasTreadmill);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all memberships", description = "Retrieves all active and historical gym memberships")
    public ResponseEntity<List<Membership>> getAllMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get membership by ID", description = "Retrieves a specific membership record")
    public ResponseEntity<Membership> getMembershipById(@PathVariable Long id) {
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get customer active membership", description = "Retrieves current active membership for a specific customer")
    public ResponseEntity<Membership> getCustomerActiveMembership(@PathVariable Long customerId) {
        return membershipService.getActiveMembershipByCustomerId(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    @Operation(summary = "Subscribe or update membership",
            description = "Subscribes a customer to a gym plan, calculates total in LKR, and generates a billing invoice [201 Created]")
    public ResponseEntity<Membership> subscribeOrUpdate(@Valid @RequestBody MembershipRequest request) {
        Membership created = membershipService.subscribeOrUpdate(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel membership", description = "Cancels an active membership [204 No Content]")
    public ResponseEntity<Void> cancelMembership(@PathVariable Long id) {
        membershipService.cancelMembership(id);
        return ResponseEntity.noContent().build();
    }
}
