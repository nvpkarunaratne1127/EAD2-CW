package dto;


import model.EquipmentCategory;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EquipmentDTO {

    private Long id;

    @NotBlank(message = "Equipment name is required")
    private String name;

    @NotNull(message = "Category is required")
    private EquipmentCategory category;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    private String weightSpecs;

    private String status;

    private String location;
}
