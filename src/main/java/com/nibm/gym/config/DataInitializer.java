package com.nibm.gym.config;

import com.nibm.gym.model.*;
import com.nibm.gym.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final EquipmentRepository equipmentRepository;
    private final SupplementRepository supplementRepository;
    private final MembershipRepository membershipRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final SupplementOrderRepository supplementOrderRepository;
    private final InvoiceRepository invoiceRepository;

    public DataInitializer(UserRepository userRepository,
                           TrainerRepository trainerRepository,
                           EquipmentRepository equipmentRepository,
                           SupplementRepository supplementRepository,
                           MembershipRepository membershipRepository,
                           WorkoutPlanRepository workoutPlanRepository,
                           SupplementOrderRepository supplementOrderRepository,
                           InvoiceRepository invoiceRepository) {
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.equipmentRepository = equipmentRepository;
        this.supplementRepository = supplementRepository;
        this.membershipRepository = membershipRepository;
        this.workoutPlanRepository = workoutPlanRepository;
        this.supplementOrderRepository = supplementOrderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // Data already seeded
        }

        // ==========================================
        // 1. Seed Users (3 Main Roles)
        // ==========================================
        User owner = userRepository.save(new User(
                "admin", "admin123", "Saman Perera (Owner)", "owner@fitpulse.lk", "0771122334", Role.OWNER
        ));

        User trainerUser1 = userRepository.save(new User(
                "kasun", "trainer123", "Kasun Mendis", "kasun@fitpulse.lk", "0775566778", Role.TRAINER
        ));
        User trainerUser2 = userRepository.save(new User(
                "dinuka", "trainer123", "Dinuka Fernando", "dinuka@fitpulse.lk", "0712233445", Role.TRAINER
        ));
        User trainerUser3 = userRepository.save(new User(
                "anushka", "trainer123", "Anushka Jayasinghe", "anushka@fitpulse.lk", "0768899001", Role.TRAINER
        ));

        User customer1 = userRepository.save(new User(
                "kamal", "customer123", "Kamal Bandara", "kamal@gmail.com", "0771234567", Role.CUSTOMER
        ));
        User customer2 = userRepository.save(new User(
                "nimal", "customer123", "Nimal Wickramasinghe", "nimal@gmail.com", "0719876543", Role.CUSTOMER
        ));
        User customer3 = userRepository.save(new User(
                "sunil", "customer123", "Sunil Senanayake", "sunil@gmail.com", "0763322110", Role.CUSTOMER
        ));
        User customer4 = userRepository.save(new User(
                "ruwan", "customer123", "Ruwan Alwis", "ruwan@gmail.com", "0754433221", Role.CUSTOMER
        ));

        // ==========================================
        // 2. Seed Trainers Profiles
        // ==========================================
        Trainer trainer1 = trainerRepository.save(new Trainer(
                trainerUser1, "Strength & Powerlifting Coach", 6,
                "Certified CSCS trainer specializing in heavy compound lifts, barbell mechanics, and athletic strength.",
                new BigDecimal("2000.00")
        ));
        Trainer trainer2 = trainerRepository.save(new Trainer(
                trainerUser2, "Bodybuilding & Hypertrophy Specialist", 8,
                "Former national physique competitor with expertise in muscle hypertrophy, body recomposition, and diet planning.",
                new BigDecimal("2000.00")
        ));
        Trainer trainer3 = trainerRepository.save(new Trainer(
                trainerUser3, "Cardiovascular & HIIT Conditioning", 4,
                "Functional training coach helping clients achieve peak endurance, fat loss, and mobility through HIIT and cardio.",
                new BigDecimal("2000.00")
        ));

        // ==========================================
        // 3. Seed Equipment (All 5 Categories)
        // ==========================================
        // Dumbbells
        equipmentRepository.save(new Equipment(
                "Hex Rubber Dumbbells Rack (Pairs)", EquipmentCategory.DUMBBELL, 28,
                "Pairs from 2.5kg to 35kg with knurled chrome handles", "AVAILABLE",
                "High-durability hexagonal rubber dumbbells to prevent rolling and protect floor."
        ));
        equipmentRepository.save(new Equipment(
                "Pro Heavy Urethane Dumbbells (Pairs)", EquipmentCategory.DUMBBELL, 14,
                "Pairs from 37.5kg to 50kg for heavy pressing and rows", "AVAILABLE",
                "Solid steel core coated in premium German urethane with aggressive knurling."
        ));
        equipmentRepository.save(new Equipment(
                "Quick-Adjust Smart Dumbbells (Pair)", EquipmentCategory.DUMBBELL, 4,
                "Adjustable from 5kg to 40kg per hand in 2.5kg increments", "AVAILABLE",
                "Dial-based quick weight switching ideal for circuit and superset workouts."
        ));

        // Weight Plates
        equipmentRepository.save(new Equipment(
                "Olympic Color Bumper Plates Set", EquipmentCategory.WEIGHT_PLATE, 40,
                "5kg (Grey), 10kg (Green), 15kg (Yellow), 20kg (Blue), 25kg (Red)", "AVAILABLE",
                "IWF standard 450mm diameter Olympic virgin rubber bumper plates with low bounce."
        ));
        equipmentRepository.save(new Equipment(
                "Cast Iron Tri-Grip Olympic Plates", EquipmentCategory.WEIGHT_PLATE, 55,
                "1.25kg, 2.5kg, 5kg, 10kg, 15kg, 20kg plates", "AVAILABLE",
                "Heavy-duty cast iron plates with triple ergonomic grip handles for easy loading."
        ));

        // Bars
        equipmentRepository.save(new Equipment(
                "Men's Olympic Barbell 20kg", EquipmentCategory.BAR, 8,
                "7ft / 20kg / 28mm shaft with 8 needle bearings, 1500lb capacity", "AVAILABLE",
                "Competition grade hardened chrome barbell for bench press, squats, and deadlifts."
        ));
        equipmentRepository.save(new Equipment(
                "Olympic EZ Curl Bar 10kg", EquipmentCategory.BAR, 6,
                "47-inch / 10kg / 28mm with angled ergonomic grips", "AVAILABLE",
                "Curved shaft reduces wrist strain during bicep curls, skull crushers, and upright rows."
        ));
        equipmentRepository.save(new Equipment(
                "Olympic Hex Trap / Shrug Bar 25kg", EquipmentCategory.BAR, 3,
                "Dual high & low knurled handles, 700lb rating", "AVAILABLE",
                "Places hands in neutral position to reduce lower back torque during deadlifts."
        ));
        equipmentRepository.save(new Equipment(
                "Multi-Grip Swiss Football Bar 15kg", EquipmentCategory.BAR, 2,
                "4 different grip widths (neutral and angled)", "AVAILABLE",
                "Spares shoulders during heavy chest pressing and overhead pressing."
        ));

        // Machine Kinds
        equipmentRepository.save(new Equipment(
                "Dual Adjustable Pulley Cable Crossover", EquipmentCategory.MACHINE, 2,
                "Dual 100kg weight stacks with 32 height adjustments", "AVAILABLE",
                "Multi-functional station for chest flyes, cable crossovers, tricep pushdowns, and face pulls."
        ));
        equipmentRepository.save(new Equipment(
                "45-Degree Plate-Loaded Leg Press", EquipmentCategory.MACHINE, 2,
                "Linear bearing carriage with 600kg plate loading capacity", "AVAILABLE",
                "Heavy quad builder with safety lockout pins and oversized adjustable footplate."
        ));
        equipmentRepository.save(new Equipment(
                "Smith Machine with Counterbalance", EquipmentCategory.MACHINE, 2,
                "Ultra-smooth vertical guide rods with zero-weight starting load", "AVAILABLE",
                "Enables controlled squats, shoulder presses, and incline presses with safety stops."
        ));
        equipmentRepository.save(new Equipment(
                "Lat Pulldown & Seated Cable Row Combo", EquipmentCategory.MACHINE, 3,
                "120kg pin-selected weight stack with thigh pads", "AVAILABLE",
                "Essential back machine for wide-grip lat pulldowns and seated cable rows."
        ));
        equipmentRepository.save(new Equipment(
                "Commercial Hack Squat Machine", EquipmentCategory.MACHINE, 1,
                "Heavy duty 45-degree angle with contoured shoulder pads", "AVAILABLE",
                "Isolates quadriceps with minimal spinal compression."
        ));

        // Resistance Bands
        equipmentRepository.save(new Equipment(
                "Powerlifting Loop Resistance Bands (Set of 5)", EquipmentCategory.RESISTANCE_BAND, 16,
                "Red (15-35lbs), Black (25-65lbs), Purple (35-85lbs), Green (50-125lbs), Blue (65-175lbs)", "AVAILABLE",
                "100% natural layered latex bands for pull-up assistance, mobility, and band squats."
        ));
        equipmentRepository.save(new Equipment(
                "Tube Resistance Bands with Foam Handles & Door Anchor", EquipmentCategory.RESISTANCE_BAND, 20,
                "11-piece set ranging from 10lbs to 50lbs with ankle straps", "AVAILABLE",
                "Versatile portable resistance system for warming up, rotator cuff, and rehab."
        ));

        // ==========================================
        // 4. Seed Supplements (All Categories)
        // ==========================================
        // Creatine
        Supplement sup1 = supplementRepository.save(new Supplement(
                "Micronized Creatine Monohydrate", SupplementCategory.CREATINE,
                "Optimum Nutrition", new BigDecimal("8500.00"), 25,
                "300g (60 servings x 5g)",
                "Pure Creapure micronized creatine monohydrate to support ATP regeneration, explosive power, and muscle fullness."
        ));
        Supplement sup2 = supplementRepository.save(new Supplement(
                "Platinum 100% Pure Creatine", SupplementCategory.CREATINE,
                "MuscleTech", new BigDecimal("9800.00"), 18,
                "400g (80 servings x 5g)",
                "Ultra-pure HPLC tested creatine powder to build 4x more lean muscle and increase strength."
        ));

        // Whey Protein
        Supplement sup3 = supplementRepository.save(new Supplement(
                "100% Gold Standard Whey (Double Rich Chocolate)", SupplementCategory.WHEY_PROTEIN,
                "Optimum Nutrition", new BigDecimal("26500.00"), 15,
                "5 lbs / 2.27kg (74 servings)",
                "World's #1 selling whey protein powder featuring whey protein isolate and 5.5g naturally occurring BCAAs."
        ));
        Supplement sup4 = supplementRepository.save(new Supplement(
                "ISO100 Hydrolyzed 100% Whey Isolate (Gourmet Chocolate)", SupplementCategory.WHEY_PROTEIN,
                "Dymatize", new BigDecimal("32000.00"), 10,
                "5 lbs / 2.3kg (71 servings)",
                "Super fast-digesting and absorbing hydrolyzed whey isolate with 25g protein and less than 1g sugar."
        ));

        // Protein (Plant & Casein)
        Supplement sup5 = supplementRepository.save(new Supplement(
                "Organic Plant-Based Protein (Creamy Chocolate Fudge)", SupplementCategory.PROTEIN,
                "Orgain", new BigDecimal("14500.00"), 12,
                "1kg (20 servings)",
                "21g clean organic pea, brown rice, and chia seed protein with zero added sugar and 5g fiber."
        ));
        Supplement sup6 = supplementRepository.save(new Supplement(
                "100% Gold Standard Micellar Casein (Chocolate Supreme)", SupplementCategory.PROTEIN,
                "Optimum Nutrition", new BigDecimal("24000.00"), 8,
                "4 lbs / 1.8kg (55 servings)",
                "Slow-digesting anti-catabolic nighttime protein that feeds muscles for up to 8 hours while sleeping."
        ));

        // Pre-Workout
        Supplement sup7 = supplementRepository.save(new Supplement(
                "C4 Original Explosive Pre-Workout (Icy Blue Razz)", SupplementCategory.PRE_WORKOUT,
                "Cellucor", new BigDecimal("11500.00"), 20,
                "30 Servings (195g)",
                "Formulated with 150mg caffeine, CarnoSyn Beta-Alanine, and Creatine Nitrate for explosive energy and pumps."
        ));
        Supplement sup8 = supplementRepository.save(new Supplement(
                "Gold Standard Advanced Pre-Workout (Fruit Punch)", SupplementCategory.PRE_WORKOUT,
                "Optimum Nutrition", new BigDecimal("12500.00"), 14,
                "30 Servings (300g)",
                "Premium energy formula with 175mg natural caffeine, 3g Creatine, and 1.5g Beta-Alanine for focus and endurance."
        ));

        // ==========================================
        // 5. Seed Memberships with Dynamic Pricing in LKR
        // ==========================================
        // Customer 1: Base (2500) + Trainer Kasun (2000) = 4500 LKR
        Membership mem1 = new Membership(customer1, trainer1, true, false);
        mem1.setStartDate(LocalDate.now().minusDays(10));
        mem1.setEndDate(LocalDate.now().plusDays(20));
        mem1.setStatus("ACTIVE");
        membershipRepository.save(mem1);

        // Customer 2: Base (2500) + Trainer Dinuka (2000) + Treadmill (1000) = 5500 LKR
        Membership mem2 = new Membership(customer2, trainer2, true, true);
        mem2.setStartDate(LocalDate.now().minusDays(5));
        mem2.setEndDate(LocalDate.now().plusDays(25));
        mem2.setStatus("ACTIVE");
        membershipRepository.save(mem2);

        // Customer 3: Base (2500) + Treadmill (1000) = 3500 LKR
        Membership mem3 = new Membership(customer3, null, false, true);
        mem3.setStartDate(LocalDate.now().minusDays(12));
        mem3.setEndDate(LocalDate.now().plusDays(18));
        mem3.setStatus("ACTIVE");
        membershipRepository.save(mem3);

        // Customer 4: Base only = 2500 LKR
        Membership mem4 = new Membership(customer4, null, false, false);
        mem4.setStartDate(LocalDate.now().minusDays(2));
        mem4.setEndDate(LocalDate.now().plusDays(28));
        mem4.setStatus("ACTIVE");
        membershipRepository.save(mem4);

        // ==========================================
        // 6. Seed Workout Plans
        // ==========================================
        workoutPlanRepository.save(new WorkoutPlan(
                trainer1, customer1,
                "Monday: Heavy Squats (5x5), Romanian Deadlifts (4x8), Calves (4x15)\n" +
                "Wednesday: Bench Press (5x5), Barbell Rows (4x8), Overhead Press (3x8)\n" +
                "Friday: Conventional Deadlift (3x5), Pull-Ups (4x10), Dips (3x12)",
                "Protein target: 160g daily. Consume 5g Micronized Creatine post-workout with 500ml water. Carbs centered around workout window.",
                "Starting weight: 76kg. Squat increased from 80kg to 105kg over 6 weeks. Excellent form adherence."
        ));

        workoutPlanRepository.save(new WorkoutPlan(
                trainer2, customer2,
                "Push Day: Incline Dumbbell Press (4x10), Cable Flyes (3x12), Lateral Raises (5x15), Tricep Pushdowns (4x12)\n" +
                "Pull Day: Lat Pulldown (4x10), Seated Cable Row (4x12), Facepulls (4x15), Incline Dumbbell Curls (4x12)\n" +
                "Legs Day: Leg Press (4x12), Hack Squat (3x10), Leg Extensions (3x15), Hamstring Curls (4x12)",
                "Caloric surplus: 2,700 kcal. 1 scoop Gold Standard Whey after workout. Keep hydration above 3.5L per day.",
                "Client gained 2.5kg of lean muscle in 8 weeks. Body fat decreased by 1.8%."
        ));

        // ==========================================
        // 7. Seed Initial Invoices & Orders
        // ==========================================
        invoiceRepository.save(new Invoice(
                "INV-MEM-001", customer1, "Monthly Membership: Base (2500 LKR) + Personal Trainer (Kasun Mendis)",
                new BigDecimal("4500.00"), "PAID"
        ));
        invoiceRepository.save(new Invoice(
                "INV-MEM-002", customer2, "Monthly Membership: Base (2500 LKR) + Personal Trainer (Dinuka Fernando) + Treadmill Pass",
                new BigDecimal("5500.00"), "PAID"
        ));
        invoiceRepository.save(new Invoice(
                "INV-MEM-003", customer3, "Monthly Membership: Base (2500 LKR) + Treadmill Pass",
                new BigDecimal("3500.00"), "PAID"
        ));
        invoiceRepository.save(new Invoice(
                "INV-MEM-004", customer4, "Monthly Membership: Base (2500 LKR)",
                new BigDecimal("2500.00"), "PAID"
        ));

        // Seed sample supplement order & invoice
        supplementOrderRepository.save(new SupplementOrder(
                customer1, sup3, 1, new BigDecimal("26500.00"), "PAID"
        ));
        invoiceRepository.save(new Invoice(
                "INV-SUP-001", customer1, "Supplement Purchase: 100% Gold Standard Whey (1 unit)",
                new BigDecimal("26500.00"), "PAID"
        ));

        supplementOrderRepository.save(new SupplementOrder(
                customer2, sup1, 1, new BigDecimal("8500.00"), "PAID"
        ));
        invoiceRepository.save(new Invoice(
                "INV-SUP-002", customer2, "Supplement Purchase: Micronized Creatine Monohydrate (1 unit)",
                new BigDecimal("8500.00"), "PAID"
        ));
    }
}
