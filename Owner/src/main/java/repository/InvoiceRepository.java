package repository;

import model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByCustomerId(Long customerId);

    List<Invoice> findByPaymentStatus(String paymentStatus);

    List<Invoice> findByCategory(String category);

    boolean existsByInvoiceNo(String invoiceNo);

    /** Total revenue = sum of PAID invoices only */
    @Query("SELECT COALESCE(SUM(i.amountLkr), 0) FROM Invoice i WHERE i.paymentStatus = 'PAID'")
    double calculateTotalPaidRevenue();

    /** Total pending = sum of PENDING invoices */
    @Query("SELECT COALESCE(SUM(i.amountLkr), 0) FROM Invoice i WHERE i.paymentStatus = 'PENDING'")
    double calculatePendingRevenue();

    /** Count by status */
    long countByPaymentStatus(String paymentStatus);
}