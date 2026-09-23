package lk.nibm.kd.hdse262ft.trainer.controller;

import lk.nibm.kd.hdse262ft.trainer.dto.SupplementOrderDTO;
import lk.nibm.kd.hdse262ft.trainer.service.SupplementOrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplement-orders")
@CrossOrigin(origins = "*")
public class SupplementOrderController {

    private final SupplementOrderService supplementOrderService;

    public SupplementOrderController(
            SupplementOrderService supplementOrderService) {

        this.supplementOrderService = supplementOrderService;
    }

    // CREATE ORDER
    @PostMapping
    public ResponseEntity<SupplementOrderDTO> createSupplementOrder(
            @RequestBody SupplementOrderDTO dto) {

        SupplementOrderDTO createdOrder =
                supplementOrderService.createSupplementOrder(dto);

        return new ResponseEntity<>(
                createdOrder,
                HttpStatus.CREATED
        );
    }

    // GET ALL ORDERS
    @GetMapping
    public ResponseEntity<List<SupplementOrderDTO>> getAllSupplementOrders() {

        List<SupplementOrderDTO> orders =
                supplementOrderService.getAllSupplementOrders();

        return ResponseEntity.ok(orders);
    }

    // GET ORDER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SupplementOrderDTO> getSupplementOrderById(
            @PathVariable Long id) {

        SupplementOrderDTO order =
                supplementOrderService.getSupplementOrderById(id);

        return ResponseEntity.ok(order);
    }

    // UPDATE ORDER
    @PutMapping("/{id}")
    public ResponseEntity<SupplementOrderDTO> updateSupplementOrder(
            @PathVariable Long id,
            @RequestBody SupplementOrderDTO dto) {

        SupplementOrderDTO updatedOrder =
                supplementOrderService.updateSupplementOrder(id, dto);

        return ResponseEntity.ok(updatedOrder);
    }

    // DELETE ORDER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplementOrder(
            @PathVariable Long id) {

        supplementOrderService.deleteSupplementOrder(id);

        return ResponseEntity.noContent().build();
    }
}