package lk.nibm.kd.hdse262ft.trainer.service;

import lk.nibm.kd.hdse262ft.trainer.dto.SupplementDTO;
import lk.nibm.kd.hdse262ft.trainer.entity.Supplement;
import lk.nibm.kd.hdse262ft.trainer.repository.SupplementRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplementServiceTest {

    @Mock
    private SupplementRepository supplementRepository;

    @InjectMocks
    private SupplementService supplementService;


    // CREATE TEST
    @Test
    void createSupplement() {

        SupplementDTO dto = new SupplementDTO(
                null,
                "Whey Protein",
                "Protein supplement",
                "Protein",
                12000.00,
                20
        );

        Supplement savedSupplement = new Supplement();

        savedSupplement.setName("Whey Protein");
        savedSupplement.setDescription("Protein supplement");
        savedSupplement.setCategory("Protein");
        savedSupplement.setPrice(12000.00);
        savedSupplement.setStockQuantity(20);

        when(supplementRepository.save(any(Supplement.class)))
                .thenReturn(savedSupplement);

        SupplementDTO result =
                supplementService.createSupplement(dto);

        assertNotNull(result);
        assertEquals("Whey Protein", result.getName());
        assertEquals("Protein", result.getCategory());

        verify(supplementRepository, times(1))
                .save(any(Supplement.class));
    }


    // GET BY ID TEST
    @Test
    void getSupplementById() {

        Supplement supplement = new Supplement();

        supplement.setName("Whey Protein");
        supplement.setDescription("Protein supplement");
        supplement.setCategory("Protein");
        supplement.setPrice(12000.00);
        supplement.setStockQuantity(20);

        when(supplementRepository.findById(1L))
                .thenReturn(Optional.of(supplement));

        SupplementDTO result =
                supplementService.getSupplementById(1L);

        assertNotNull(result);
        assertEquals("Whey Protein", result.getName());
        assertEquals("Protein", result.getCategory());

        verify(supplementRepository, times(1))
                .findById(1L);
    }


    // NOT FOUND TEST
    @Test
    void getSupplementByIdWhenNotFound() {

        when(supplementRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> supplementService.getSupplementById(1L)
        );

        verify(supplementRepository, times(1))
                .findById(1L);
    }


    // DELETE TEST
    @Test
    void deleteSupplement() {

        when(supplementRepository.existsById(1L))
                .thenReturn(true);

        supplementService.deleteSupplement(1L);

        verify(supplementRepository, times(1))
                .existsById(1L);

        verify(supplementRepository, times(1))
                .deleteById(1L);
    }
}