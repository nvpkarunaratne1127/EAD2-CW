-- ===================================================================
-- NIBM HDSE 26.2FT - Enterprise Application Development 02 (EAD 02)
-- Gym Management System Database Script
-- Compatible with MySQL 8.x / MariaDB (XAMPP phpMyAdmin)
-- ===================================================================

CREATE DATABASE IF NOT EXISTS gym_management_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gym_management_db;

-- 1. Drop existing tables in reverse dependency order
DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS workout_plans;
DROP TABLE IF EXISTS supplement_orders;
DROP TABLE IF EXISTS memberships;
DROP TABLE IF EXISTS trainers;
DROP TABLE IF EXISTS supplements;
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS users;

-- 2. Users Table (3 Main Roles: OWNER, TRAINER, CUSTOMER)
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 3. Trainers Table
CREATE TABLE trainers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    specialization VARCHAR(100) NOT NULL,
    experience_years INT DEFAULT 0,
    bio TEXT,
    monthly_rate_lkr DECIMAL(10,2) NOT NULL DEFAULT 2000.00,
    CONSTRAINT fk_trainers_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 4. Equipment Table (Dumbbells, Weight Plates, Bars, Machine Kinds, Resistance Bands)
CREATE TABLE equipment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(30) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    weight_specs VARCHAR(100),
    status VARCHAR(30) NOT NULL DEFAULT 'AVAILABLE',
    description TEXT
) ENGINE=InnoDB;

-- 5. Supplements Table (Creatine, Whey Protein, Protein, Pre-workout)
CREATE TABLE supplements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(30) NOT NULL,
    brand VARCHAR(100),
    price_lkr DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    serving_size VARCHAR(50),
    description TEXT
) ENGINE=InnoDB;

-- 6. Memberships Table (Dynamic LKR Pricing: 2500 Base + 2000 Trainer + 1000 Treadmill)
CREATE TABLE memberships (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    trainer_id BIGINT,
    base_fee_lkr DECIMAL(10,2) NOT NULL DEFAULT 2500.00,
    has_trainer BOOLEAN NOT NULL DEFAULT FALSE,
    has_treadmill BOOLEAN NOT NULL DEFAULT FALSE,
    total_monthly_fee_lkr DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    start_date DATE,
    end_date DATE,
    CONSTRAINT fk_memberships_customer FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_memberships_trainer FOREIGN KEY (trainer_id) REFERENCES trainers(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- 7. Supplement Orders Table
CREATE TABLE supplement_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    supplement_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    total_price_lkr DECIMAL(10,2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PAID',
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_orders_supplement FOREIGN KEY (supplement_id) REFERENCES supplements(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 8. Workout Plans Table (Trainer assigned client notes)
CREATE TABLE workout_plans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trainer_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL UNIQUE,
    routine_notes TEXT,
    diet_notes TEXT,
    progress_notes TEXT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_workout_trainer FOREIGN KEY (trainer_id) REFERENCES trainers(id) ON DELETE CASCADE,
    CONSTRAINT fk_workout_customer FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 9. Invoices Table
CREATE TABLE invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_no VARCHAR(50) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount_lkr DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PAID',
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_invoices_customer FOREIGN KEY (customer_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===================================================================
-- SEED DATA
-- ===================================================================

-- Users
INSERT INTO users (id, username, password, full_name, email, phone, role) VALUES
(1, 'admin', 'admin123', 'Saman Perera (Gym Owner)', 'owner@fitpulse.lk', '0771122334', 'OWNER'),
(2, 'kasun', 'trainer123', 'Kasun Mendis', 'kasun@fitpulse.lk', '0775566778', 'TRAINER'),
(3, 'dinuka', 'trainer123', 'Dinuka Fernando', 'dinuka@fitpulse.lk', '0712233445', 'TRAINER'),
(4, 'anushka', 'trainer123', 'Anushka Jayasinghe', 'anushka@fitpulse.lk', '0768899001', 'TRAINER'),
(5, 'kamal', 'customer123', 'Kamal Bandara', 'kamal@gmail.com', '0771234567', 'CUSTOMER'),
(6, 'nimal', 'customer123', 'Nimal Wickramasinghe', 'nimal@gmail.com', '0719876543', 'CUSTOMER'),
(7, 'sunil', 'customer123', 'Sunil Senanayake', 'sunil@gmail.com', '0763322110', 'CUSTOMER'),
(8, 'ruwan', 'customer123', 'Ruwan Alwis', 'ruwan@gmail.com', '0754433221', 'CUSTOMER');

-- Trainers
INSERT INTO trainers (id, user_id, specialization, experience_years, bio, monthly_rate_lkr) VALUES
(1, 2, 'Strength & Powerlifting Coach', 6, 'Certified CSCS trainer specializing in heavy compound lifts, barbell mechanics, and athletic strength.', 2000.00),
(2, 3, 'Bodybuilding & Hypertrophy Specialist', 8, 'Former national physique competitor with expertise in muscle hypertrophy, body recomposition, and diet planning.', 2000.00),
(3, 4, 'Cardiovascular & HIIT Conditioning', 4, 'Functional training coach helping clients achieve peak endurance, fat loss, and mobility through HIIT and cardio.', 2000.00);

-- Equipment
INSERT INTO equipment (name, category, quantity, weight_specs, status, description) VALUES
('Hex Rubber Dumbbells Rack (Pairs)', 'DUMBBELL', 28, 'Pairs from 2.5kg to 35kg with knurled chrome handles', 'AVAILABLE', 'High-durability hexagonal rubber dumbbells to prevent rolling and protect floor.'),
('Pro Heavy Urethane Dumbbells (Pairs)', 'DUMBBELL', 14, 'Pairs from 37.5kg to 50kg for heavy pressing and rows', 'AVAILABLE', 'Solid steel core coated in premium German urethane with aggressive knurling.'),
('Quick-Adjust Smart Dumbbells (Pair)', 'DUMBBELL', 4, 'Adjustable from 5kg to 40kg per hand in 2.5kg increments', 'AVAILABLE', 'Dial-based quick weight switching ideal for circuit and superset workouts.'),
('Olympic Color Bumper Plates Set', 'WEIGHT_PLATE', 40, '5kg (Grey), 10kg (Green), 15kg (Yellow), 20kg (Blue), 25kg (Red)', 'AVAILABLE', 'IWF standard 450mm diameter Olympic virgin rubber bumper plates with low bounce.'),
('Cast Iron Tri-Grip Olympic Plates', 'WEIGHT_PLATE', 55, '1.25kg, 2.5kg, 5kg, 10kg, 15kg, 20kg plates', 'AVAILABLE', 'Heavy-duty cast iron plates with triple ergonomic grip handles for easy loading.'),
('Men\'s Olympic Barbell 20kg', 'BAR', 8, '7ft / 20kg / 28mm shaft with 8 needle bearings, 1500lb capacity', 'AVAILABLE', 'Competition grade hardened chrome barbell for bench press, squats, and deadlifts.'),
('Olympic EZ Curl Bar 10kg', 'BAR', 6, '47-inch / 10kg / 28mm with angled ergonomic grips', 'AVAILABLE', 'Curved shaft reduces wrist strain during bicep curls, skull crushers, and upright rows.'),
('Olympic Hex Trap / Shrug Bar 25kg', 'BAR', 3, 'Dual high & low knurled handles, 700lb rating', 'AVAILABLE', 'Places hands in neutral position to reduce lower back torque during deadlifts.'),
('Multi-Grip Swiss Football Bar 15kg', 'BAR', 2, '4 different grip widths (neutral and angled)', 'AVAILABLE', 'Spares shoulders during heavy chest pressing and overhead pressing.'),
('Dual Adjustable Pulley Cable Crossover', 'MACHINE', 2, 'Dual 100kg weight stacks with 32 height adjustments', 'AVAILABLE', 'Multi-functional station for chest flyes, cable crossovers, tricep pushdowns, and face pulls.'),
('45-Degree Plate-Loaded Leg Press', 'MACHINE', 2, 'Linear bearing carriage with 600kg plate loading capacity', 'AVAILABLE', 'Heavy quad builder with safety lockout pins and oversized adjustable footplate.'),
('Smith Machine with Counterbalance', 'MACHINE', 2, 'Ultra-smooth vertical guide rods with zero-weight starting load', 'AVAILABLE', 'Enables controlled squats, shoulder presses, and incline presses with safety stops.'),
('Lat Pulldown & Seated Cable Row Combo', 'MACHINE', 3, '120kg pin-selected weight stack with thigh pads', 'AVAILABLE', 'Essential back machine for wide-grip lat pulldowns and seated cable rows.'),
('Commercial Hack Squat Machine', 'MACHINE', 1, 'Heavy duty 45-degree angle with contoured shoulder pads', 'AVAILABLE', 'Isolates quadriceps with minimal spinal compression.'),
('Powerlifting Loop Resistance Bands (Set of 5)', 'RESISTANCE_BAND', 16, 'Red (15-35lbs), Black (25-65lbs), Purple (35-85lbs), Green (50-125lbs)', 'AVAILABLE', '100% natural layered latex bands for pull-up assistance, mobility, and band squats.'),
('Tube Resistance Bands with Foam Handles & Door Anchor', 'RESISTANCE_BAND', 20, '11-piece set ranging from 10lbs to 50lbs with ankle straps', 'AVAILABLE', 'Versatile portable resistance system for warming up, rotator cuff, and rehab.');

-- Supplements
INSERT INTO supplements (name, category, brand, price_lkr, stock_quantity, serving_size, description) VALUES
('Micronized Creatine Monohydrate', 'CREATINE', 'Optimum Nutrition', 8500.00, 25, '300g (60 servings x 5g)', 'Pure Creapure micronized creatine monohydrate to support ATP regeneration, explosive power, and muscle fullness.'),
('Platinum 100% Pure Creatine', 'CREATINE', 'MuscleTech', 9800.00, 18, '400g (80 servings x 5g)', 'Ultra-pure HPLC tested creatine powder to build 4x more lean muscle and increase strength.'),
('100% Gold Standard Whey (Double Rich Chocolate)', 'WHEY_PROTEIN', 'Optimum Nutrition', 26500.00, 15, '5 lbs / 2.27kg (74 servings)', 'World\'s #1 selling whey protein powder featuring whey protein isolate and 5.5g naturally occurring BCAAs.'),
('ISO100 Hydrolyzed 100% Whey Isolate (Gourmet Chocolate)', 'WHEY_PROTEIN', 'Dymatize', 32000.00, 10, '5 lbs / 2.3kg (71 servings)', 'Super fast-digesting and absorbing hydrolyzed whey isolate with 25g protein and less than 1g sugar.'),
('Organic Plant-Based Protein (Creamy Chocolate Fudge)', 'PROTEIN', 'Orgain', 14500.00, 12, '1kg (20 servings)', '21g clean organic pea, brown rice, and chia seed protein with zero added sugar and 5g fiber.'),
('100% Gold Standard Micellar Casein (Chocolate Supreme)', 'PROTEIN', 'Optimum Nutrition', 24000.00, 8, '4 lbs / 1.8kg (55 servings)', 'Slow-digesting anti-catabolic nighttime protein that feeds muscles for up to 8 hours while sleeping.'),
('C4 Original Explosive Pre-Workout (Icy Blue Razz)', 'PRE_WORKOUT', 'Cellucor', 11500.00, 20, '30 Servings (195g)', 'Formulated with 150mg caffeine, CarnoSyn Beta-Alanine, and Creatine Nitrate for explosive energy and pumps.'),
('Gold Standard Advanced Pre-Workout (Fruit Punch)', 'PRE_WORKOUT', 'Optimum Nutrition', 12500.00, 14, '30 Servings (300g)', 'Premium energy formula with 175mg natural caffeine, 3g Creatine, and 1.5g Beta-Alanine for focus and endurance.');

-- Memberships
INSERT INTO memberships (id, customer_id, trainer_id, base_fee_lkr, has_trainer, has_treadmill, total_monthly_fee_lkr, status, start_date, end_date) VALUES
(1, 5, 1, 2500.00, TRUE, FALSE, 4500.00, 'ACTIVE', CURDATE() - INTERVAL 10 DAY, CURDATE() + INTERVAL 20 DAY),
(2, 6, 2, 2500.00, TRUE, TRUE, 5500.00, 'ACTIVE', CURDATE() - INTERVAL 5 DAY, CURDATE() + INTERVAL 25 DAY),
(3, 7, NULL, 2500.00, FALSE, TRUE, 3500.00, 'ACTIVE', CURDATE() - INTERVAL 12 DAY, CURDATE() + INTERVAL 18 DAY),
(4, 8, NULL, 2500.00, FALSE, FALSE, 2500.00, 'ACTIVE', CURDATE() - INTERVAL 2 DAY, CURDATE() + INTERVAL 28 DAY);

-- Workout Plans
INSERT INTO workout_plans (trainer_id, customer_id, routine_notes, diet_notes, progress_notes) VALUES
(1, 5, 
'Monday: Heavy Squats (5x5), Romanian Deadlifts (4x8), Calves (4x15)\nWednesday: Bench Press (5x5), Barbell Rows (4x8), Overhead Press (3x8)\nFriday: Conventional Deadlift (3x5), Pull-Ups (4x10), Dips (3x12)',
'Protein target: 160g daily. Consume 5g Micronized Creatine post-workout with 500ml water. Carbs centered around workout window.',
'Starting weight: 76kg. Squat increased from 80kg to 105kg over 6 weeks. Excellent form adherence.'
),
(2, 6,
'Push Day: Incline Dumbbell Press (4x10), Cable Flyes (3x12), Lateral Raises (5x15), Tricep Pushdowns (4x12)\nPull Day: Lat Pulldown (4x10), Seated Cable Row (4x12), Facepulls (4x15), Incline Dumbbell Curls (4x12)\nLegs Day: Leg Press (4x12), Hack Squat (3x10), Leg Extensions (3x15), Hamstring Curls (4x12)',
'Caloric surplus: 2,700 kcal. 1 scoop Gold Standard Whey after workout. Keep hydration above 3.5L per day.',
'Client gained 2.5kg of lean muscle in 8 weeks. Body fat decreased by 1.8%.'
);

-- Invoices
INSERT INTO invoices (invoice_no, customer_id, description, amount_lkr, payment_status, invoice_date) VALUES
('INV-MEM-001', 5, 'Monthly Membership: Base (2500 LKR) + Personal Trainer (Kasun Mendis)', 4500.00, 'PAID', NOW() - INTERVAL 10 DAY),
('INV-MEM-002', 6, 'Monthly Membership: Base (2500 LKR) + Personal Trainer (Dinuka Fernando) + Treadmill Pass', 5500.00, 'PAID', NOW() - INTERVAL 5 DAY),
('INV-MEM-003', 7, 'Monthly Membership: Base (2500 LKR) + Treadmill Pass', 3500.00, 'PAID', NOW() - INTERVAL 12 DAY),
('INV-MEM-004', 8, 'Monthly Membership: Base (2500 LKR)', 2500.00, 'PAID', NOW() - INTERVAL 2 DAY),
('INV-SUP-001', 5, 'Supplement Purchase: 100% Gold Standard Whey (1 unit)', 26500.00, 'PAID', NOW() - INTERVAL 7 DAY),
('INV-SUP-002', 6, 'Supplement Purchase: Micronized Creatine Monohydrate (1 unit)', 8500.00, 'PAID', NOW() - INTERVAL 3 DAY);

-- Supplement Orders
INSERT INTO supplement_orders (customer_id, supplement_id, quantity, total_price_lkr, payment_status, order_date) VALUES
(5, 3, 1, 26500.00, 'PAID', NOW() - INTERVAL 7 DAY),
(6, 1, 1, 8500.00, 'PAID', NOW() - INTERVAL 3 DAY);
