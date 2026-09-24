package com.nibm.gym.controller;

import com.nibm.gym.dto.SupplementOrderRequest;
import com.nibm.gym.model.Supplement;
import com.nibm.gym.model.SupplementCategory;
import com.nibm.gym.model.SupplementOrder;
import com.nibm.gym.service.SupplementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplements")
@Tag(name = "Supplement Resource", description = "Endpoints for managing gym supplements: Creatine, Whey Protein, Protein, Pre-workout, and ordering")
public class SupplementController {

    private final SupplementService supplementService;

    public SupplementController(SupplementService supplementService) {
        this.supplementService = supplementService;
    }

    @GetMapping
    @Operation(summary = "Get all supplements", description = "Retrieves all supplements in the catalog, optionally filtered by category (CREATINE, WHEY_PROTEIN, PROTEIN, PRE_WORKOUT, OTHER)")
    public ResponseEntity<List<Supplement>> getAllSupplements(@RequestParam(required = false) SupplementCategory category) {
        if (category != null) {
            return ResponseEntity.ok(supplementService.getSupplementsByCategory(category));
        }
        return ResponseEntity.ok(supplementService.getAllSupplements());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get supplement by ID", description = "Retrieves a supplement item by its unique ID")
    public ResponseEntity<Supplement> getSupplementById(@PathVariable Long id) {
        return ResponseEntity.ok(supplementService.getSupplementById(id));
    }

    @PostMapping
    @Operation(summary = "Create supplement", description = "Adds a new supplement to the catalog [201 Created]")
    public ResponseEntity<Supplement> createSupplement(@Valid @RequestBody Supplement supplement) {
        Supplement created = supplementService.createSupplement(supplement);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update supplement", description = "Updates details, stock level, or price in LKR for a supplement [200 OK]")
    public ResponseEntity<Supplement> updateSupplement(@PathVariable Long id, @Valid @RequestBody Supplement supplement) {
        Supplement updated = supplementService.updateSupplement(id, supplement);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete supplement", description = "Removes a supplement from the catalog [204 No Content]")
    public ResponseEntity<Void> deleteSupplement(@PathVariable Long id) {
        supplementService.deleteSupplement(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/order")
    @Operation(summary = "Purchase supplement", description = "Places a customer order for a supplement, decrements inventory stock, and generates an invoice in LKR [201 Created]")
    public ResponseEntity<SupplementOrder> placeOrder(@Valid @RequestBody SupplementOrderRequest request) {
        SupplementOrder order = supplementService.placeOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    @Operation(summary = "Get all supplement orders", description = "Retrieves all supplement order history across all customers")
    public ResponseEntity<List<SupplementOrder>> getAllOrders() {
        return ResponseEntity.ok(supplementService.getAllOrders());
    }

    @GetMapping("/orders/customer/{customerId}")
    @Operation(summary = "Get customer's orders", description = "Retrieves supplement order history for a specific customer")
    public ResponseEntity<List<SupplementOrder>> getCustomerOrders(@PathVariable Long customerId) {
        return ResponseEntity.ok(supplementService.getCustomerOrders(customerId));
    }
}
