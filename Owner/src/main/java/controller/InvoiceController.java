package controller;

import dto.InvoiceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/api/owner/invoices")
@CrossOrigin(origins = "*")
@Tag(name = "Owner – Invoices", description = "Invoice CRUD and revenue tracking (Owner only)")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List all invoices")
    public List<InvoiceDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one invoice by id")
    public InvoiceDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get all invoices for a customer")
    public List<InvoiceDTO> getByCustomer(@PathVariable Long customerId) {
        return service.getByCustomer(customerId);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Filter by payment status (PAID, PENDING, OVERDUE, CANCELLED)")
    public List<InvoiceDTO> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new invoice")
    public InvoiceDTO create(@Valid @RequestBody InvoiceDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update invoice details")
    public InvoiceDTO update(@PathVariable Long id, @Valid @RequestBody InvoiceDTO dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}/pay")
    @Operation(summary = "Mark invoice as PAID")
    public InvoiceDTO markPaid(@PathVariable Long id) {
        return service.markPaid(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an invoice")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
