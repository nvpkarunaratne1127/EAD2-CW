package lk.nibm.kd.hdse262ft.trainer.service;

import lk.nibm.kd.hdse262ft.trainer.dto.TrainerDTO;
import lk.nibm.kd.hdse262ft.trainer.entity.Trainer;
import lk.nibm.kd.hdse262ft.trainer.repository.TrainerRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    // Constructor
    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    // CREATE
    public TrainerDTO createTrainer(TrainerDTO trainerDTO) {

        Trainer trainer = new Trainer();

        trainer.setName(trainerDTO.getName());
        trainer.setEmail(trainerDTO.getEmail());
        trainer.setPhone(trainerDTO.getPhone());
        trainer.setSpecialization(trainerDTO.getSpecialization());
        trainer.setExperience(trainerDTO.getExperience());

        Trainer savedTrainer = trainerRepository.save(trainer);

        return convertToDTO(savedTrainer);
    }

    // GET ALL
    public List<TrainerDTO> getAllTrainers() {

        return trainerRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public TrainerDTO getTrainerById(Long id) {

        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Trainer not found with ID: " + id));

        return convertToDTO(trainer);
    }

    // UPDATE
    public TrainerDTO updateTrainer(Long id, TrainerDTO trainerDTO) {

        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Trainer not found with ID: " + id));

        trainer.setName(trainerDTO.getName());
        trainer.setEmail(trainerDTO.getEmail());
        trainer.setPhone(trainerDTO.getPhone());
        trainer.setSpecialization(trainerDTO.getSpecialization());
        trainer.setExperience(trainerDTO.getExperience());

        Trainer updatedTrainer = trainerRepository.save(trainer);

        return convertToDTO(updatedTrainer);
    }

    // DELETE
    public void deleteTrainer(Long id) {

        if (!trainerRepository.existsById(id)) {
            throw new RuntimeException(
                    "Trainer not found with ID: " + id);
        }

        trainerRepository.deleteById(id);
    }

    // Entity → DTO
    private TrainerDTO convertToDTO(Trainer trainer) {

        return new TrainerDTO(
                trainer.getId(),
                trainer.getName(),
                trainer.getEmail(),
                trainer.getPhone(),
                trainer.getSpecialization(),
                trainer.getExperience()
        );
    }
}
