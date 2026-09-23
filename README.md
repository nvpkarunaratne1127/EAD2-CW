# FitPulse Gym Management System (EAD 02 Coursework)

National Institute of Business Management (NIBM) — School of Computing and Engineering  
**Module:** Enterprise Application Development 02 (EAD 02)  
**Batch:** HDSE 26.2FT  

---

## 📌 Architecture & Modules

This repository contains the complete microservice-architecture enterprise system:

- **[Owner & Customer](./Owner)** (Port `8080`):
  - **Equipment CRUD:** Dumbbells, Weight Plates, Barbells, Machines, Resistance Bands.
  - **Invoices & Billing:** Total paid revenue stats and financial audit receipts.
  - **Customer Profile & Self-Registration:** Customer registration with unique email checks.
  - **Swagger API Documentation:** Live at `http://localhost:8080/swagger-ui.html`
- **[Trainer](./Trainer)** (Port `8081`):
  - **Certified Trainers CRUD:** Specialization, experience, and profile management.
  - **Workout & Diet Plans CRUD:** Custom training splits and nutritional guidelines.
  - **Supplement Store CRUD:** Creatine, Whey Protein, and Pre-workout catalog and ordering.
- **[Frontend](./frontend)** (Port `5173`):
  - Modern Single-Page Application built with React 18 & Vite.
  - Unified 3-Module Dashboard: Owner Management, Trainer Management, and Customer Dynamic Pricing.

---

## 🚀 How to Run in 1-Click

Simply double-click:
```bash
start-all.bat
```
This automatically boots all backend services and opens the React web app at `http://localhost:5173`!

To stop all services:
```bash
stop-all.bat
```
