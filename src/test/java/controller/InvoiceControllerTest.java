package controller;

import dto.InvoiceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import service.InvoiceService;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @MockitoBean private InvoiceService service;

    @Test
    void getAll_returnsList() throws Exception {
        when(service.getAll()).thenReturn(List.of(
                InvoiceDTO.builder()
                        .id(1L).invoiceNo("INV-2026-0001")
                        .customerName("Nimal").customerId(10L)
                        .description("Premium membership")
                        .amountLkr(4500).issuedDate(LocalDate.now())
                        .paymentStatus("PAID").category("MEMBERSHIP")
                        .build()
        ));

        mockMvc.perform(get("/api/owner/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].invoiceNo").value("INV-2026-0001"))
                .andExpect(jsonPath("$[0].amountLkr").value(4500));
    }

    @Test
    void create_returns201() throws Exception {
        InvoiceDTO in = InvoiceDTO.builder()
                .customerName("Nimal").customerId(10L)
                .description("Supplement order")
                .amountLkr(3500).category("SUPPLEMENT")
                .build();
        InvoiceDTO out = InvoiceDTO.builder()
                .id(1L).invoiceNo("INV-2026-0001")
                .customerName("Nimal").customerId(10L)
                .description("Supplement order")
                .amountLkr(3500).issuedDate(LocalDate.now())
                .paymentStatus("PENDING").category("SUPPLEMENT")
                .build();

        when(service.create(any(InvoiceDTO.class))).thenReturn(out);

        mockMvc.perform(post("/api/owner/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(in)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.invoiceNo").value("INV-2026-0001"));
    }

    @Test
    void markPaid_returnsUpdated() throws Exception {
        when(service.markPaid(eq(1L))).thenReturn(
                InvoiceDTO.builder().id(1L).paymentStatus("PAID").build()
        );

        mockMvc.perform(patch("/api/owner/invoices/1/pay"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentStatus").value("PAID"));
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/api/owner/invoices/1"))
                .andExpect(status().isNoContent());
    }
}