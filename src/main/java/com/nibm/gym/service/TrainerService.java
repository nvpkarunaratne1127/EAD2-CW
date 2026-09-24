package com.nibm.gym.service;

import com.nibm.gym.exception.BadRequestException;
import com.nibm.gym.exception.ResourceNotFoundException;
import com.nibm.gym.model.Membership;
import com.nibm.gym.model.Role;
import com.nibm.gym.model.Trainer;
import com.nibm.gym.model.User;
import com.nibm.gym.repository.MembershipRepository;
import com.nibm.gym.repository.TrainerRepository;
import com.nibm.gym.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    public TrainerService(TrainerRepository trainerRepository, UserRepository userRepository, MembershipRepository membershipRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    @Transactional(readOnly = true)
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Trainer getTrainerByUserId(Long userId) {
        return trainerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer profile not found for user id: " + userId));
    }

    public Trainer createTrainer(Long userId, Trainer trainerDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (user.getRole() != Role.TRAINER) {
            user.setRole(Role.TRAINER);
            userRepository.save(user);
        }

        if (trainerRepository.findByUser(user).isPresent()) {
            throw new BadRequestException("Trainer profile already exists for user: " + user.getUsername());
        }

        trainerDetails.setUser(user);
        return trainerRepository.save(trainerDetails);
    }

    public Trainer updateTrainer(Long id, Trainer trainerDetails) {
        Trainer existing = getTrainerById(id);
        existing.setSpecialization(trainerDetails.getSpecialization());
        existing.setExperienceYears(trainerDetails.getExperienceYears());
        existing.setBio(trainerDetails.getBio());
        if (trainerDetails.getMonthlyRateLkr() != null) {
            existing.setMonthlyRateLkr(trainerDetails.getMonthlyRateLkr());
        }
        return trainerRepository.save(existing);
    }

    public void deleteTrainer(Long id) {
        Trainer trainer = getTrainerById(id);
        trainerRepository.delete(trainer);
    }

    @Transactional(readOnly = true)
    public List<Membership> getAssignedClients(Long trainerId) {
        return membershipRepository.findByTrainerId(trainerId);
    }
}
