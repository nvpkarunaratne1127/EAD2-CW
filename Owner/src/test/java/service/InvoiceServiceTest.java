package service;

import dto.InvoiceDTO;
import exception.BadRequestException;
import exception.ResourceNotFoundException;
import model.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.InvoiceRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock private InvoiceRepository repository;

    @InjectMocks private InvoiceService service;

    @Test
    void getById_returnsInvoice_whenExists() {
        Invoice inv = Invoice.builder()
                .id(1L).invoiceNo("INV-2026-0001")
                .customerName("Nimal").amountLkr(4500)
                .paymentStatus("PAID").build();
        when(repository.findById(1L)).thenReturn(Optional.of(inv));

        InvoiceDTO dto = service.getById(1L);

        assertEquals("INV-2026-0001", dto.getInvoiceNo());
        assertEquals(4500, dto.getAmountLkr());
    }

    @Test
    void getById_throws_whenMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void create_rejectsNegativeAmount() {
        InvoiceDTO in = InvoiceDTO.builder()
                .customerName("Nimal").customerId(1L)
                .description("Bad").amountLkr(-100).build();

        assertThrows(BadRequestException.class, () -> service.create(in));
        verify(repository, never()).save(any());
    }

    @Test
    void markPaid_setsStatusPaid() {
        Invoice inv = Invoice.builder().id(1L).paymentStatus("PENDING").build();
        when(repository.findById(1L)).thenReturn(Optional.of(inv));
        when(repository.save(any(Invoice.class))).thenAnswer(i -> i.getArgument(0));

        InvoiceDTO out = service.markPaid(1L);

        assertEquals("PAID", out.getPaymentStatus());
    }

    @Test
    void delete_throws_whenMissing() {
        when(repository.existsById(5L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(5L));
    }
}