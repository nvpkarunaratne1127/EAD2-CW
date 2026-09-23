package lk.nibm.kd.hdse262ft.trainer.service;

import lk.nibm.kd.hdse262ft.trainer.dto.SupplementOrderDTO;
import lk.nibm.kd.hdse262ft.trainer.entity.SupplementOrder;
import lk.nibm.kd.hdse262ft.trainer.repository.SupplementOrderRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplementOrderServiceTest {

    @Mock
    private SupplementOrderRepository supplementOrderRepository;

    @InjectMocks
    private SupplementOrderService supplementOrderService;

    // CREATE TEST
    @Test
    void createSupplementOrder() {

        SupplementOrderDTO dto = new SupplementOrderDTO(
                null,
                1L,
                1L,
                2,
                24000.00,
                "Pending"
        );

        SupplementOrder savedOrder = new SupplementOrder();

        savedOrder.setCustomerId(1L);
        savedOrder.setSupplementId(1L);
        savedOrder.setQuantity(2);
        savedOrder.setTotalPrice(24000.00);
        savedOrder.setOrderStatus("Pending");

        when(supplementOrderRepository.save(any(SupplementOrder.class)))
                .thenReturn(savedOrder);

        SupplementOrderDTO result =
                supplementOrderService.createSupplementOrder(dto);

        assertNotNull(result);
        assertEquals(1L, result.getCustomerId());
        assertEquals(1L, result.getSupplementId());
        assertEquals(2, result.getQuantity());
        assertEquals("Pending", result.getOrderStatus());

        verify(supplementOrderRepository, times(1))
                .save(any(SupplementOrder.class));
    }

    // GET BY ID TEST
    @Test
    void getSupplementOrderById() {

        SupplementOrder order = new SupplementOrder();

        order.setCustomerId(1L);
        order.setSupplementId(1L);
        order.setQuantity(2);
        order.setTotalPrice(24000.00);
        order.setOrderStatus("Pending");

        when(supplementOrderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        SupplementOrderDTO result =
                supplementOrderService.getSupplementOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getCustomerId());
        assertEquals(2, result.getQuantity());
        assertEquals("Pending", result.getOrderStatus());

        verify(supplementOrderRepository, times(1))
                .findById(1L);
    }

    // NOT FOUND TEST
    @Test
    void getSupplementOrderByIdWhenNotFound() {

        when(supplementOrderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> supplementOrderService.getSupplementOrderById(1L)
        );

        verify(supplementOrderRepository, times(1))
                .findById(1L);
    }

    // DELETE TEST
    @Test
    void deleteSupplementOrder() {

        when(supplementOrderRepository.existsById(1L))
                .thenReturn(true);

        supplementOrderService.deleteSupplementOrder(1L);

        verify(supplementOrderRepository, times(1))
                .existsById(1L);

        verify(supplementOrderRepository, times(1))
                .deleteById(1L);
    }
}