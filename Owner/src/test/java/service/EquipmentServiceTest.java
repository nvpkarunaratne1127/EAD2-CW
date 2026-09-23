package service;

import dto.EquipmentDTO;
import exception.BadRequestException;
import exception.ResourceNotFoundException;
import model.Equipment;
import model.EquipmentCategory;
import repository.EquipmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @Mock
    private EquipmentRepository repository;

    @InjectMocks
    private EquipmentService service;

    // ---------- getById ----------
    @Test
    void getById_returnsEquipment_whenExists() {
        Equipment eq = Equipment.builder()
                .id(1L).name("20kg Dumbbell")
                .category(EquipmentCategory.DUMBBELL)
                .quantity(10).status("AVAILABLE").build();
        when(repository.findById(1L)).thenReturn(Optional.of(eq));

        EquipmentDTO dto = service.getById(1L);

        assertEquals("20kg Dumbbell", dto.getName());
        assertEquals(EquipmentCategory.DUMBBELL, dto.getCategory());
        assertEquals(10, dto.getQuantity());
    }

    @Test
    void getById_throws_whenMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(99L));
    }

    // ---------- create ----------
    @Test
    void create_savesAndReturnsDTO() {
        EquipmentDTO in = EquipmentDTO.builder()
                .name("Olympic Bar").category(EquipmentCategory.BAR)
                .quantity(5).weightSpecs("2.2m").build();
        Equipment saved = Equipment.builder()
                .id(1L).name("Olympic Bar").category(EquipmentCategory.BAR)
                .quantity(5).weightSpecs("2.2m").status("AVAILABLE").build();
        when(repository.save(any(Equipment.class))).thenReturn(saved);

        EquipmentDTO out = service.create(in);

        assertEquals(1L, out.getId());
        assertEquals("Olympic Bar", out.getName());
        assertEquals("AVAILABLE", out.getStatus());
    }

    @Test
    void create_rejectsNegativeQuantity() {
        EquipmentDTO in = EquipmentDTO.builder()
                .name("Bad").category(EquipmentCategory.MACHINE).quantity(-3).build();

        assertThrows(BadRequestException.class, () -> service.create(in));
        verify(repository, never()).save(any());
    }

    // ---------- update ----------
    @Test
    void update_overwritesFields() {
        Equipment existing = Equipment.builder()
                .id(2L).name("Old").category(EquipmentCategory.DUMBBELL)
                .quantity(1).build();
        when(repository.findById(2L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Equipment.class))).thenAnswer(i -> i.getArgument(0));

        EquipmentDTO in = EquipmentDTO.builder()
                .name("New Name").category(EquipmentCategory.MACHINE)
                .quantity(7).weightSpecs("New Spec").status("IN_USE").build();

        EquipmentDTO out = service.update(2L, in);

        assertEquals("New Name", out.getName());
        assertEquals(7, out.getQuantity());
        assertEquals("IN_USE", out.getStatus());
    }

    // ---------- adjustQuantity ----------
    @Test
    void adjustQuantity_addsStock() {
        Equipment eq = Equipment.builder().id(3L).name("Plate").quantity(10).build();
        when(repository.findById(3L)).thenReturn(Optional.of(eq));
        when(repository.save(any(Equipment.class))).thenAnswer(i -> i.getArgument(0));

        EquipmentDTO out = service.adjustQuantity(3L, 5);
        assertEquals(15, out.getQuantity());
    }

    @Test
    void adjustQuantity_throws_whenDeductBelowZero() {
        Equipment eq = Equipment.builder().id(4L).name("Plate").quantity(2).build();
        when(repository.findById(4L)).thenReturn(Optional.of(eq));

        assertThrows(BadRequestException.class, () -> service.adjustQuantity(4L, -5));
    }

    // ---------- delete ----------
    @Test
    void delete_removesExisting() {
        when(repository.existsById(5L)).thenReturn(true);
        service.delete(5L);
        verify(repository).deleteById(5L);
    }

    @Test
    void delete_throws_whenMissing() {
        when(repository.existsById(6L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(6L));
        verify(repository, never()).deleteById(anyLong());
    }
}
