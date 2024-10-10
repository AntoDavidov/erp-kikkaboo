package nl.fontys.s3.erp.business.impl.ManufacturerUnitTests;

import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.UpdateManufacturerRequest;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.impl.ManufacturersImpl.UpdateManufacturerUseCaseImpl;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UpdateManufacturerUCImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private UpdateManufacturerUseCaseImpl updateManufacturerUseCase;

    @Test
    void updateManufacturer_HappyFlow() {
        UpdateManufacturerRequest request = new UpdateManufacturerRequest(1L, "KikkaBoo", Country.BULGARIA, "Plovdiv");
        ManufacturerEntity existingManufacturer = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .city("Sofia")
                .country(Country.BULGARIA)
                .build();

        when(manufacturerRepository.findById(anyLong())).thenReturn(Optional.of(existingManufacturer));

        // Act
        updateManufacturerUseCase.updateManufacturer(request);

        // Assert
        verify(manufacturerRepository).save(existingManufacturer);
        assertEquals("Plovdiv", existingManufacturer.getCity());  // Correct expected city to "Plovdiv"
    }

    @Test
    void updateManufacturer_NotFound_UnhappyFlow() {
        UpdateManufacturerRequest request = new UpdateManufacturerRequest(1L, "KikkaBoo", Country.BULGARIA, "Plovdiv");

        when(manufacturerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ManufacturerDoesNotExist.class, () -> {
            updateManufacturerUseCase.updateManufacturer(request);
        });

        // Verify that save was never called
        verify(manufacturerRepository, never()).save(any(ManufacturerEntity.class));
    }
}
