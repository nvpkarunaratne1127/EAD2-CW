package lk.nibm.kd.hdse262ft.trainer.controller;

import lk.nibm.kd.hdse262ft.trainer.dto.SupplementDTO;
import lk.nibm.kd.hdse262ft.trainer.service.SupplementService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplements")
@CrossOrigin(origins = "*")
public class SupplementController {

    private final SupplementService supplementService;

    public SupplementController(SupplementService supplementService) {
        this.supplementService = supplementService;
    }

    // CREATE SUPPLEMENT
    @PostMapping
    public ResponseEntity<SupplementDTO> createSupplement(
            @RequestBody SupplementDTO supplementDTO) {

        SupplementDTO createdSupplement =
                supplementService.createSupplement(supplementDTO);

        return new ResponseEntity<>(
                createdSupplement,
                HttpStatus.CREATED
        );
    }

    // GET ALL SUPPLEMENTS
    @GetMapping
    public ResponseEntity<List<SupplementDTO>> getAllSupplements() {

        List<SupplementDTO> supplements =
                supplementService.getAllSupplements();

        return ResponseEntity.ok(supplements);
    }

    // GET SUPPLEMENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SupplementDTO> getSupplementById(
            @PathVariable Long id) {

        SupplementDTO supplement =
                supplementService.getSupplementById(id);

        return ResponseEntity.ok(supplement);
    }

    // UPDATE SUPPLEMENT
    @PutMapping("/{id}")
    public ResponseEntity<SupplementDTO> updateSupplement(
            @PathVariable Long id,
            @RequestBody SupplementDTO supplementDTO) {

        SupplementDTO updatedSupplement =
                supplementService.updateSupplement(
                        id,
                        supplementDTO
                );

        return ResponseEntity.ok(updatedSupplement);
    }

    // DELETE SUPPLEMENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplement(
            @PathVariable Long id) {

        supplementService.deleteSupplement(id);

        return ResponseEntity.noContent().build();
    }
}