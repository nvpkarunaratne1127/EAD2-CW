package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.Customer;
import service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
@Tag(name = "Customer Resource", description = "Customer registration and profile CRUD")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new customer [201 Created]")
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        return new ResponseEntity<>(service.register(customer), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all registered customers [200 OK]")
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID [200 OK]")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer details [200 OK]")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        return ResponseEntity.ok(service.update(id, customer));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer [204 No Content]")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
