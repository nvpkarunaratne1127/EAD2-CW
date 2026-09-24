package com.nibm.gym.repository;

import com.nibm.gym.model.Invoice;
import com.nibm.gym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByCustomerOrderByInvoiceDateDesc(User customer);
    List<Invoice> findByCustomerIdOrderByInvoiceDateDesc(Long customerId);
    List<Invoice> findAllByOrderByInvoiceDateDesc();

    @Query("SELECT COALESCE(SUM(i.amountLkr), 0) FROM Invoice i WHERE i.paymentStatus = 'PAID'")
    BigDecimal calculateTotalPaidRevenue();
}
