package nl.fontys.s3.erp.business.impl.ManufacturerUnitTests;

import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.impl.ManufacturersImpl.DeleteManufacturerUseCaseImpl;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteManufacturerUCImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private DeleteManufacturerUseCaseImpl deleteManufacturerUseCase;

    @Test
    void deleteManufacturer_HappyFlow() {
        when(manufacturerRepository.existsById(1L)).thenReturn(true);
        deleteManufacturerUseCase.deleteManufacturer(1L);
        verify(manufacturerRepository).deleteById(1L);
    }

    @Test
    void deleteManufacturer_NotFound_UnhappyFlow() {
        when(manufacturerRepository.existsById(1L)).thenReturn(false);
        assertThrows(ManufacturerDoesNotExist.class, () -> {
            deleteManufacturerUseCase.deleteManufacturer(1L);
        });
        verify(manufacturerRepository, never()).deleteById(anyLong());
    }
}
