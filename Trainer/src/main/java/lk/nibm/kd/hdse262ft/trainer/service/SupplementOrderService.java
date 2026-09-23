package lk.nibm.kd.hdse262ft.trainer.service;

import lk.nibm.kd.hdse262ft.trainer.dto.SupplementOrderDTO;
import lk.nibm.kd.hdse262ft.trainer.entity.SupplementOrder;
import lk.nibm.kd.hdse262ft.trainer.repository.SupplementOrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplementOrderService {

    private final SupplementOrderRepository supplementOrderRepository;

    public SupplementOrderService(
            SupplementOrderRepository supplementOrderRepository) {

        this.supplementOrderRepository = supplementOrderRepository;
    }

    // CREATE
    public SupplementOrderDTO createSupplementOrder(
            SupplementOrderDTO dto) {

        SupplementOrder order = new SupplementOrder();

        order.setCustomerId(dto.getCustomerId());
        order.setSupplementId(dto.getSupplementId());
        order.setQuantity(dto.getQuantity());
        order.setTotalPrice(dto.getTotalPrice());
        order.setOrderStatus(dto.getOrderStatus());

        SupplementOrder savedOrder =
                supplementOrderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    // GET ALL
    public List<SupplementOrderDTO> getAllSupplementOrders() {

        return supplementOrderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public SupplementOrderDTO getSupplementOrderById(Long id) {

        SupplementOrder order =
                supplementOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplement order not found with ID: " + id));

        return convertToDTO(order);
    }

    // UPDATE
    public SupplementOrderDTO updateSupplementOrder(
            Long id,
            SupplementOrderDTO dto) {

        SupplementOrder order =
                supplementOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplement order not found with ID: " + id));

        order.setCustomerId(dto.getCustomerId());
        order.setSupplementId(dto.getSupplementId());
        order.setQuantity(dto.getQuantity());
        order.setTotalPrice(dto.getTotalPrice());
        order.setOrderStatus(dto.getOrderStatus());

        SupplementOrder updatedOrder =
                supplementOrderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    // DELETE
    public void deleteSupplementOrder(Long id) {

        if (!supplementOrderRepository.existsById(id)) {
            throw new RuntimeException(
                    "Supplement order not found with ID: " + id);
        }

        supplementOrderRepository.deleteById(id);
    }

    // ENTITY → DTO
    private SupplementOrderDTO convertToDTO(
            SupplementOrder order) {

        return new SupplementOrderDTO(
                order.getId(),
                order.getCustomerId(),
                order.getSupplementId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderStatus()
        );
    }
}