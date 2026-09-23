package lk.nibm.kd.hdse262ft.trainer.controller;

import lk.nibm.kd.hdse262ft.trainer.dto.SupplementOrderDTO;
import lk.nibm.kd.hdse262ft.trainer.service.SupplementOrderService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupplementOrderController.class)
class SupplementOrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SupplementOrderService supplementOrderService;

    // CREATE ORDER
    @Test
    void createSupplementOrder() throws Exception {

        SupplementOrderDTO order = new SupplementOrderDTO(
                1L,
                1L,
                1L,
                2,
                24000.00,
                "Pending"
        );

        when(supplementOrderService.createSupplementOrder(
                any(SupplementOrderDTO.class)))
                .thenReturn(order);

        mockMvc.perform(post("/api/supplement-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "customerId": 1,
                                    "supplementId": 1,
                                    "quantity": 2,
                                    "totalPrice": 24000.00,
                                    "orderStatus": "Pending"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.orderStatus").value("Pending"));
    }

    // GET ALL ORDERS
    @Test
    void getAllSupplementOrders() throws Exception {

        SupplementOrderDTO order = new SupplementOrderDTO(
                1L,
                1L,
                1L,
                2,
                24000.00,
                "Pending"
        );

        when(supplementOrderService.getAllSupplementOrders())
                .thenReturn(Arrays.asList(order));

        mockMvc.perform(get("/api/supplement-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].quantity").value(2));
    }

    // GET ORDER BY ID
    @Test
    void getSupplementOrderById() throws Exception {

        SupplementOrderDTO order = new SupplementOrderDTO(
                1L,
                1L,
                1L,
                2,
                24000.00,
                "Pending"
        );

        when(supplementOrderService.getSupplementOrderById(1L))
                .thenReturn(order);

        mockMvc.perform(get("/api/supplement-orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.orderStatus").value("Pending"));
    }

    // UPDATE ORDER
    @Test
    void updateSupplementOrder() throws Exception {

        SupplementOrderDTO order = new SupplementOrderDTO(
                1L,
                1L,
                1L,
                3,
                36000.00,
                "Confirmed"
        );

        when(supplementOrderService.updateSupplementOrder(
                eq(1L),
                any(SupplementOrderDTO.class)))
                .thenReturn(order);

        mockMvc.perform(put("/api/supplement-orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "customerId": 1,
                                    "supplementId": 1,
                                    "quantity": 3,
                                    "totalPrice": 36000.00,
                                    "orderStatus": "Confirmed"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(3))
                .andExpect(jsonPath("$.orderStatus").value("Confirmed"));
    }

    // DELETE ORDER
    @Test
    void deleteSupplementOrder() throws Exception {

        doNothing().when(supplementOrderService)
                .deleteSupplementOrder(1L);

        mockMvc.perform(delete("/api/supplement-orders/1"))
                .andExpect(status().isNoContent());

        verify(supplementOrderService, times(1))
                .deleteSupplementOrder(1L);
    }
}