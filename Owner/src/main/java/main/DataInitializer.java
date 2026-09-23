package main;

import model.Customer;
import model.Equipment;
import model.EquipmentCategory;
import model.Invoice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import repository.CustomerRepository;
import repository.EquipmentRepository;
import repository.InvoiceRepository;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EquipmentRepository equipmentRepository;
    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;

    public DataInitializer(EquipmentRepository equipmentRepository,
                           CustomerRepository customerRepository,
                           InvoiceRepository invoiceRepository) {
        this.equipmentRepository = equipmentRepository;
        this.customerRepository = customerRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void run(String... args) {
        // 1. Seed Dummy Equipment if empty
        if (equipmentRepository.count() == 0) {
            equipmentRepository.save(Equipment.builder()
                    .name("Hex Rubber Dumbbell Pairs (2.5kg - 35kg)")
                    .category(EquipmentCategory.DUMBBELL)
                    .quantity(28)
                    .weightSpecs("2.5kg - 35kg knurled steel")
                    .status("AVAILABLE")
                    .location("Zone A - Free Weights")
                    .build());

            equipmentRepository.save(Equipment.builder()
                    .name("Men's Olympic Barbell 20kg")
                    .category(EquipmentCategory.BAR)
                    .quantity(8)
                    .weightSpecs("7ft / 20kg / 28mm shaft / 1500lb limit")
                    .status("AVAILABLE")
                    .location("Zone A - Bench Press Racks")
                    .build());

            equipmentRepository.save(Equipment.builder()
                    .name("Olympic Virgin Rubber Bumper Plates")
                    .category(EquipmentCategory.WEIGHT_PLATE)
                    .quantity(40)
                    .weightSpecs("5kg, 10kg, 15kg, 20kg, 25kg")
                    .status("AVAILABLE")
                    .location("Zone B - Deadlift Platform")
                    .build());

            equipmentRepository.save(Equipment.builder()
                    .name("Dual Adjustable Cable Crossover")
                    .category(EquipmentCategory.MACHINE)
                    .quantity(2)
                    .weightSpecs("Dual 100kg weight stacks")
                    .status("AVAILABLE")
                    .location("Zone C - Resistance Machines")
                    .build());

            equipmentRepository.save(Equipment.builder()
                    .name("Powerlifting Loop Resistance Bands (Set of 5)")
                    .category(EquipmentCategory.RESISTANCE_BAND)
                    .quantity(15)
                    .weightSpecs("15lbs to 125lbs resistance")
                    .status("AVAILABLE")
                    .location("Zone D - Mobility Station")
                    .build());

            equipmentRepository.save(Equipment.builder()
                    .name("45-Degree Plate-Loaded Leg Press")
                    .category(EquipmentCategory.MACHINE)
                    .quantity(1)
                    .weightSpecs("Linear bearing 600kg capacity")
                    .status("UNDER_MAINTENANCE")
                    .location("Zone C - Heavy Press")
                    .build());
        }

        // 2. Seed Dummy Customers if empty
        if (customerRepository.count() == 0) {
            customerRepository.save(Customer.builder()
                    .name("Kamal Bandara")
                    .email("kamal@gmail.com")
                    .password("customer123")
                    .phone("0771234567")
                    .build());

            customerRepository.save(Customer.builder()
                    .name("Nimal Wickramasinghe")
                    .email("nimal@gmail.com")
                    .password("customer123")
                    .phone("0719876543")
                    .build());

            customerRepository.save(Customer.builder()
                    .name("Sunil Senanayake")
                    .email("sunil@gmail.com")
                    .password("customer123")
                    .phone("0763322110")
                    .build());
        }

        // 3. Seed Dummy Invoices if empty
        if (invoiceRepository.count() == 0) {
            invoiceRepository.save(Invoice.builder()
                    .invoiceNo("INV-2026-0001")
                    .customerName("Kamal Bandara")
                    .customerId(1L)
                    .description("Monthly Membership: Base (2500 LKR) + Personal Trainer (2000 LKR)")
                    .amountLkr(4500.0)
                    .issuedDate(LocalDate.now().minusDays(10))
                    .paymentStatus("PAID")
                    .category("MEMBERSHIP")
                    .build());

            invoiceRepository.save(Invoice.builder()
                    .invoiceNo("INV-2026-0002")
                    .customerName("Nimal Wickramasinghe")
                    .customerId(2L)
                    .description("Monthly Membership: Base (2500 LKR) + Trainer + Treadmill Pass")
                    .amountLkr(5500.0)
                    .issuedDate(LocalDate.now().minusDays(5))
                    .paymentStatus("PAID")
                    .category("MEMBERSHIP")
                    .build());

            invoiceRepository.save(Invoice.builder()
                    .invoiceNo("INV-2026-0003")
                    .customerName("Sunil Senanayake")
                    .customerId(3L)
                    .description("Monthly Membership: Base Gym Entry")
                    .amountLkr(2500.0)
                    .issuedDate(LocalDate.now().minusDays(2))
                    .paymentStatus("PAID")
                    .category("MEMBERSHIP")
                    .build());
        }
    }
}
