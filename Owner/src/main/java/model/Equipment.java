package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "equipment")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Equipment name is required")
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Category is required")
    @Column(nullable = false)
    private EquipmentCategory category;

    @Min(value = 0, message = "Quantity cannot be negative")
    @Column(nullable = false)
    private int quantity;

    /** Free-text specs e.g. "20kg", "Adjustable 5-25kg", "2.2m olympic bar" */
    private String weightSpecs;

    /** e.g. AVAILABLE, IN_USE, UNDER_MAINTENANCE, RETIRED */
    @Column(nullable = false)
    @Builder.Default
    private String status = "AVAILABLE";

    /** Optional: location/rack number inside the gym */
    private String location;
}