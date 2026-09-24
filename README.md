# FitPulse Gym Management System (NIBM HDSE 26.2FT - EAD02)

**National Institute of Business Management (NIBM) — School of Computing and Engineering**  
**Module:** Enterprise Application Development 02 (EAD 02)  
**Batch:** HDSE 26.2FT  
**Assessment Type:** CW01 &bull; RESTful API &amp; React Enterprise System  

---

## 📌 Project Overview
FitPulse is a full-featured, enterprise-grade Gym Management System built with a **Java Spring Boot 3 RESTful API** and a **Modern React UI**. It adheres to the official NIBM EAD 02 coursework specifications and REST architectural constraints, providing comprehensive CRUD operations, role-based workflows, dynamic Sri Lankan Rupee (LKR) pricing calculations, and interactive Swagger API documentation.

---

## 🌟 Key Features

### 1. Three Core User Roles
- 👑 **Owner (Gym Admin):**
  - Live revenue analytics and financial stats in LKR.
  - Full CRUD management of all Gym Equipment inventory.
  - Full CRUD management of Supplement Store products and stock levels.
  - Member management and trainer assignment monitoring.
  - Complete invoice generation and transaction auditing.
- 🏋️ **Trainer:**
  - View assigned gym-goer clients.
  - Prescribe and update custom workout training splits, diet guidelines, and progress notes.
  - Reference available gym equipment to create tailored client exercise plans.
- 🏃 **Customer / Gym Goer:**
  - **Dynamic Membership Pricing Calculator in LKR:**
    - Base gym floor access: **2,500 LKR / month**
    - Personal Trainer coaching add-on: **+2,000 LKR** (Total: **4,500 LKR / month**)
    - Treadmill &amp; Cardio zone add-on: **+1,000 LKR** (Total: **3,500 LKR** or **5,500 LKR** with trainer)
    - Real-time calculation and one-click package activation.
  - View assigned personal coach profile and personalized workout split/diet plan.
  - Browse gym equipment catalog.
  - Browse and purchase supplements (Whey Protein, Creatine, Pre-workout, etc.) with real-time stock deduction.
  - Access personal billing invoices and receipts in LKR.

### 2. Equipment Inventory Categories
- **Dumbbells:** Hex Rubber Dumbbell pairs (2.5kg - 35kg), Pro Urethane dumbbells (37.5kg - 50kg), Quick-Adjust SelectTech pairs.
- **Weight Plates:** Competition Olympic Bumper plates (5kg - 25kg), Cast Iron Tri-Grip plates.
- **Bars:** Olympic Barbells (20kg / 7ft), Olympic EZ Curl Bars (10kg), Hex Trap/Shrug Bars (25kg), Multi-Grip Swiss Football Bars.
- **Machine Kinds:** Dual Cable Crossover, 45-Degree Leg Press, Smith Machine with Counterbalance, Lat Pulldown &amp; Low Row combo, Commercial Hack Squat.
- **Resistance Bands:** Heavy Duty Powerlifting Loop Bands (Set of 5), Elastic Tube Bands with handles &amp; door anchor.

### 3. Supplement Store
- **Creatine:** Optimum Nutrition Micronized Creatine Monohydrate (300g / 60 servings), MuscleTech Platinum 100% Pure Creatine (400g).
- **Whey Protein:** 100% Gold Standard Whey 5 lbs (Double Rich Chocolate), Dymatize ISO100 Hydrolyzed Whey Isolate 5 lbs.
- **Protein Powders:** Orgain Organic Plant-Based Protein 1kg, Optimum Nutrition 100% Micellar Casein 4 lbs.
- **Pre-Workout:** Cellucor C4 Original Explosive Pre-Workout (30 Servings), Optimum Nutrition Gold Standard Pre-Workout.

---

## 🛠️ Technology Stack & Architecture

- **Backend:**
  - Java 21 / 26
  - Spring Boot 3.3.4 (Spring Web, Spring Data JPA, Spring Validation)
  - SpringDoc OpenAPI 3 / Swagger UI (`springdoc-openapi-starter-webmvc-ui` 2.6.0)
  - JUnit 5 &amp; Spring Boot Test (MockMvc)
  - Dual Database Support:
    - **In-Memory H2 Database** (Zero-configuration out-of-the-box run)
    - **MySQL / MariaDB** (Production profile with `database/gym_management_db.sql`)
- **Frontend:**
  - React 18 with Vite
  - Modern Athletic Design System (Dark mode `#090d16`, cyan `#06b6d4`, emerald `#10b981`, glassmorphism cards)
  - Lucide React Iconography
- **IDE:**
  - JetBrains IntelliJ IDEA (Native Maven layout with pre-configured `.idea` settings)

---

## 👥 Default Demo Credentials

| Role | Username | Password | Full Name | Notes |
| :--- | :--- | :--- | :--- | :--- |
| **Owner** | `admin` | `admin123` | Saman Perera | Full administrative &amp; financial control |
| **Trainer** | `kasun` | `trainer123` | Kasun Mendis | Strength &amp; Conditioning Coach |
| **Trainer** | `dinuka` | `trainer123` | Dinuka Fernando | Bodybuilding &amp; Hypertrophy Specialist |
| **Trainer** | `anushka` | `trainer123` | Anushka Jayasinghe | Cardio &amp; HIIT Conditioning Coach |
| **Customer** | `kamal` | `customer123` | Kamal Bandara | Subscribed: Base + Trainer Kasun (4,500 LKR) |
| **Customer** | `nimal` | `customer123` | Nimal Wickramasinghe | Subscribed: Base + Trainer + Treadmill (5,500 LKR) |
| **Customer** | `sunil` | `customer123` | Sunil Senanayake | Subscribed: Base + Treadmill (3,500 LKR) |
| **Customer** | `ruwan` | `customer123` | Ruwan Alwis | Subscribed: Base Only (2,500 LKR) |

*Tip: The web interface includes a **1-Click Quick Role Switcher** at the bottom right corner for viva demonstration!*

---

## 🚀 How to Run the Project

### Option 1: Run directly in IntelliJ IDEA (Recommended)
1. Open **IntelliJ IDEA**.
2. Select **Open** and choose the `P6` project folder.
3. IntelliJ will automatically detect the `pom.xml` file.
4. Locate the pre-configured run configuration `GymManagementApplication` and click the **Run** (green triangle) button.
5. The application will start at:
   - **Frontend UI:** [http://localhost:8080](http://localhost:8080)
   - **Swagger UI API Documentation:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - **H2 Database Console:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:mem:gymdb`, User: `sa`, Password: empty)

### Option 2: Run via Command Line / Terminal
```powershell
# 1. Start the Spring Boot Application
mvn spring-boot:run
```
*(Or use the bundled IntelliJ maven path if `mvn` is not on your system PATH)*:
```powershell
& "C:\Program Files\JetBrains\IntelliJ IDEA 2026.2.0.1\plugins\maven-plugin\lib\maven3\bin\mvn.cmd" spring-boot:run
```

### Option 3: Optional MySQL / XAMPP Setup
If you want to run against a real MySQL server:
1. Start MySQL in **XAMPP Control Panel**.
2. Open **phpMyAdmin** (`http://localhost/phpmyadmin`).
3. Import the provided `database/gym_management_db.sql` file.
4. Start Spring Boot with the MySQL profile:
   ```powershell
   mvn spring-boot:run -Dspring-boot.run.profiles=mysql
   ```

---

## 🧪 Automated Testing
Run the comprehensive JUnit 5 and MockMvc test suite:
```powershell
mvn test
```
**Test Coverage Includes:**
- Dynamic LKR Pricing Calculation unit tests (`MembershipServiceTest`):
  - Base price: 2,500 LKR
  - Base + Trainer add-on: 4,500 LKR
  - Base + Treadmill add-on: 3,500 LKR
  - Base + Trainer + Treadmill: 5,500 LKR
- REST API Controller integration tests (`EquipmentControllerTest`, `AuthControllerTest`).

---

## 📖 REST API Endpoints Table

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/login` | Authenticate user credentials &amp; return role | `200 OK` |
| `POST` | `/api/auth/register` | Register a new customer account | `201 Created` |
| `GET` | `/api/auth/users` | List all users across all roles | `200 OK` |
| `GET` | `/api/equipment` | Retrieve all gym equipment (filter by `category`) | `200 OK` |
| `POST` | `/api/equipment` | Add new equipment item | `201 Created` |
| `PUT` | `/api/equipment/{id}` | Update equipment quantity or specs | `200 OK` |
| `DELETE` | `/api/equipment/{id}` | Delete equipment item | `204 No Content` |
| `GET` | `/api/supplements` | Retrieve supplement catalog (filter by `category`) | `200 OK` |
| `POST` | `/api/supplements` | Add new supplement to store | `201 Created` |
| `PUT` | `/api/supplements/{id}` | Update supplement details or stock | `200 OK` |
| `DELETE` | `/api/supplements/{id}` | Remove supplement from store | `204 No Content` |
| `POST` | `/api/supplements/order` | Place customer order, deduct stock, create invoice | `201 Created` |
| `GET` | `/api/trainers` | List all certified gym trainers | `200 OK` |
| `GET` | `/api/trainers/{id}/clients` | List assigned clients for a trainer | `200 OK` |
| `POST` | `/api/memberships/calculate` | Calculate dynamic fee (Base: 2500, Trainer: +2000, Treadmill: +1000) | `200 OK` |
| `POST` | `/api/memberships` | Subscribe or upgrade gym package &amp; issue invoice | `201 Created` |
| `GET` | `/api/workout-plans/customer/{id}` | Retrieve customer's custom workout &amp; diet plan | `200 OK` |
| `POST` | `/api/workout-plans` | Trainer assigns/updates workout &amp; diet split | `200 OK` |
| `GET` | `/api/billing/stats` | Retrieve total revenue &amp; dashboard metrics in LKR | `200 OK` |
| `GET` | `/api/billing/invoices` | Retrieve all billing invoices (Owner) | `200 OK` |
| `GET` | `/api/billing/invoices/customer/{id}` | Retrieve personal customer invoices | `200 OK` |

---

## 📬 Postman Collection
Import `postman_collection.json` into Postman to test all endpoints with pre-configured requests and sample payloads.
