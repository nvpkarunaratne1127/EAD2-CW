package service;

import exception.ResourceNotFoundException;
import exception.BadRequestException;
import model.Customer;
import repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer register(Customer customer) {
        if (repository.existsByEmail(customer.getEmail())) {
            throw new BadRequestException("Email " + customer.getEmail() + " is already registered");
        }
        return repository.save(customer);
    }

    @Transactional(readOnly = true)
    public List<Customer> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    public Customer update(Long id, Customer updated) {
        Customer existing = getById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
            existing.setPassword(updated.getPassword());
        }
        return repository.save(existing);
    }

    public void delete(Long id) {
        Customer existing = getById(id);
        repository.delete(existing);
    }
}
