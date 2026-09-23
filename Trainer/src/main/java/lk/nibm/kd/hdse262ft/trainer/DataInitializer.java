package lk.nibm.kd.hdse262ft.trainer;

import lk.nibm.kd.hdse262ft.trainer.entity.Supplement;
import lk.nibm.kd.hdse262ft.trainer.entity.Trainer;
import lk.nibm.kd.hdse262ft.trainer.entity.WorkoutPlan;
import lk.nibm.kd.hdse262ft.trainer.repository.SupplementRepository;
import lk.nibm.kd.hdse262ft.trainer.repository.TrainerRepository;
import lk.nibm.kd.hdse262ft.trainer.repository.WorkoutPlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TrainerRepository trainerRepository;
    private final SupplementRepository supplementRepository;
    private final WorkoutPlanRepository workoutPlanRepository;

    public DataInitializer(TrainerRepository trainerRepository,
                           SupplementRepository supplementRepository,
                           WorkoutPlanRepository workoutPlanRepository) {
        this.trainerRepository = trainerRepository;
        this.supplementRepository = supplementRepository;
        this.workoutPlanRepository = workoutPlanRepository;
    }

    @Override
    public void run(String... args) {
        // 1. Seed Dummy Trainers if empty
        if (trainerRepository.count() == 0) {
            Trainer t1 = new Trainer();
            t1.setName("Kasun Mendis");
            t1.setEmail("kasun@fitpulse.lk");
            t1.setPhone("0775566778");
            t1.setSpecialization("Strength & Powerlifting Coach");
            t1.setExperience("6 Years");
            trainerRepository.save(t1);

            Trainer t2 = new Trainer();
            t2.setName("Dinuka Fernando");
            t2.setEmail("dinuka@fitpulse.lk");
            t2.setPhone("0712233445");
            t2.setSpecialization("Bodybuilding & Hypertrophy Specialist");
            t2.setExperience("8 Years");
            trainerRepository.save(t2);

            Trainer t3 = new Trainer();
            t3.setName("Anushka Jayasinghe");
            t3.setEmail("anushka@fitpulse.lk");
            t3.setPhone("0768899001");
            t3.setSpecialization("Cardiovascular & HIIT Conditioning");
            t3.setExperience("4 Years");
            trainerRepository.save(t3);
        }

        // 2. Seed Dummy Supplements if empty
        if (supplementRepository.count() == 0) {
            Supplement s1 = new Supplement();
            s1.setName("Optimum Nutrition Micronized Creatine Monohydrate (300g)");
            s1.setCategory("CREATINE");
            s1.setDescription("Pure Creapure micronized creatine monohydrate for ATP regeneration and power.");
            s1.setPrice(8500.0);
            s1.setStockQuantity(25);
            supplementRepository.save(s1);

            Supplement s2 = new Supplement();
            s2.setName("100% Gold Standard Whey 5 lbs (Double Rich Chocolate)");
            s2.setCategory("WHEY_PROTEIN");
            s2.setDescription("World's #1 selling whey protein powder featuring whey protein isolate and 5.5g BCAAs.");
            s2.setPrice(26500.0);
            s2.setStockQuantity(15);
            supplementRepository.save(s2);

            Supplement s3 = new Supplement();
            s3.setName("Cellucor C4 Original Explosive Pre-Workout (30 Servings)");
            s3.setCategory("PRE_WORKOUT");
            s3.setDescription("150mg caffeine, CarnoSyn Beta-Alanine, and Creatine Nitrate for explosive energy.");
            s3.setPrice(11500.0);
            s3.setStockQuantity(20);
            supplementRepository.save(s3);
        }

        // 3. Seed Dummy Workout Plans if empty
        if (workoutPlanRepository.count() == 0) {
            WorkoutPlan wp1 = new WorkoutPlan();
            wp1.setPlanName("Powerlifting 5x5 Strength Split");
            wp1.setDescription("Mon: Heavy Squats 5x5, Wed: Bench Press 5x5, Fri: Deadlifts 3x5");
            wp1.setGoal("Build maximum compound strength and barbell mechanics");
            wp1.setDurationWeeks(8);
            wp1.setTrainerId(1L);
            workoutPlanRepository.save(wp1);

            WorkoutPlan wp2 = new WorkoutPlan();
            wp2.setPlanName("Hypertrophy Push-Pull-Legs Routine");
            wp2.setDescription("Push (Chest/Delts/Triceps), Pull (Lats/Rows/Biceps), Legs (Quads/Hams/Calves)");
            wp2.setGoal("Lean muscle mass hypertrophy and progressive overload");
            wp2.setDurationWeeks(12);
            wp2.setTrainerId(2L);
            workoutPlanRepository.save(wp2);
        }
    }
}
