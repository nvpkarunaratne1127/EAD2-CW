package controller;

import dto.DashboardStatsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.OwnerDashboardService;

@RestController
@RequestMapping("/api/owner/dashboard")
@Tag(name = "Owner – Dashboard", description = "Aggregated KPIs for the owner dashboard")
public class OwnerDashboardController {

    private final OwnerDashboardService service;

    public OwnerDashboardController(OwnerDashboardService service) {
        this.service = service;
    }

    @GetMapping("/stats")
    @Operation(summary = "Owner dashboard KPIs: equipment, revenue, pending invoices")
    public DashboardStatsDTO stats() {
        return service.getDashboardStats();
    }
}