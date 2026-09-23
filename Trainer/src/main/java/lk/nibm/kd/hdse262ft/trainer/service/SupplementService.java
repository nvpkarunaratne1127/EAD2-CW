package lk.nibm.kd.hdse262ft.trainer.service;

import lk.nibm.kd.hdse262ft.trainer.dto.SupplementDTO;
import lk.nibm.kd.hdse262ft.trainer.entity.Supplement;
import lk.nibm.kd.hdse262ft.trainer.repository.SupplementRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplementService {

    private final SupplementRepository supplementRepository;

    public SupplementService(SupplementRepository supplementRepository) {
        this.supplementRepository = supplementRepository;
    }

    // CREATE
    public SupplementDTO createSupplement(SupplementDTO supplementDTO) {

        Supplement supplement = new Supplement();

        supplement.setName(supplementDTO.getName());
        supplement.setDescription(supplementDTO.getDescription());
        supplement.setCategory(supplementDTO.getCategory());
        supplement.setPrice(supplementDTO.getPrice());
        supplement.setStockQuantity(supplementDTO.getStockQuantity());

        Supplement savedSupplement = supplementRepository.save(supplement);

        return convertToDTO(savedSupplement);
    }

    // READ ALL
    public List<SupplementDTO> getAllSupplements() {

        return supplementRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // READ BY ID
    public SupplementDTO getSupplementById(Long id) {

        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Supplement not found with ID: " + id));

        return convertToDTO(supplement);
    }

    // UPDATE
    public SupplementDTO updateSupplement(
            Long id,
            SupplementDTO supplementDTO) {

        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Supplement not found with ID: " + id));

        supplement.setName(supplementDTO.getName());
        supplement.setDescription(supplementDTO.getDescription());
        supplement.setCategory(supplementDTO.getCategory());
        supplement.setPrice(supplementDTO.getPrice());
        supplement.setStockQuantity(supplementDTO.getStockQuantity());

        Supplement updatedSupplement =
                supplementRepository.save(supplement);

        return convertToDTO(updatedSupplement);
    }

    // DELETE
    public void deleteSupplement(Long id) {

        if (!supplementRepository.existsById(id)) {
            throw new RuntimeException(
                    "Supplement not found with ID: " + id);
        }

        supplementRepository.deleteById(id);
    }

    // Convert Entity to DTO
    private SupplementDTO convertToDTO(Supplement supplement) {

        return new SupplementDTO(
                supplement.getId(),
                supplement.getName(),
                supplement.getDescription(),
                supplement.getCategory(),
                supplement.getPrice(),
                supplement.getStockQuantity()
        );
    }
}