package nl.fontys.s3.erp.business.impl.ManufacturerUnitTests;

import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.UpdateManufacturerRequest;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.ManufacturersImpl.UpdateManufacturerUseCaseImpl;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UpdateManufacturerUCImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private UpdateManufacturerUseCaseImpl updateManufacturerUseCase;

    @Test
    void updateManufacturer_throwsPermissionDenied_whenDepartmentsNull() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(null);
        UpdateManufacturerRequest request = UpdateManufacturerRequest.builder()
                .manufacturerId(1L)
                .city("New City")
                .country(Country.BULGARIA)
                .companyName("New Company Name")
                .build();

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> updateManufacturerUseCase.updateManufacturer(request));

        // Verify: No repository interaction
        verifyNoInteractions(manufacturerRepository);
    }

    @Test
    void updateManufacturer_throwsPermissionDenied_whenTradeDepartmentMissing() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("SALES"));
        UpdateManufacturerRequest request = UpdateManufacturerRequest.builder()
                .manufacturerId(1L)
                .city("New City")
                .country(Country.BULGARIA)
                .companyName("New Company Name")
                .build();

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> updateManufacturerUseCase.updateManufacturer(request));

        // Verify: No repository interaction
        verifyNoInteractions(manufacturerRepository);
    }
    @Test
    void updateManufacturer_throwsManufacturerDoesNotExist_whenIdNotFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());

        UpdateManufacturerRequest request = UpdateManufacturerRequest.builder()
                .manufacturerId(1L)
                .city("New City")
                .country(Country.CHINA)
                .companyName("New Company Name")
                .build();

        // Act & Assert
        assertThrows(ManufacturerDoesNotExist.class, () -> updateManufacturerUseCase.updateManufacturer(request));

        // Verify: `save` is never called
        verify(manufacturerRepository, never()).save(any());
    }
    @Test
    void updateManufacturer_updatesAndSavesSuccessfully_whenValidRequest() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));

        ManufacturerEntity manufacturerEntity = ManufacturerEntity.builder()
                .id(1L)
                .companyName("Old Company Name")
                .city("Old City")
                .country(Country.CHINA)
                .build();

        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturerEntity));

        UpdateManufacturerRequest request = UpdateManufacturerRequest.builder()
                .manufacturerId(1L)
                .city("New City")
                .country(Country.BULGARIA)
                .companyName("New Company Name")
                .build();

        // Act
        updateManufacturerUseCase.updateManufacturer(request);

        // Assert
        assertEquals("New City", manufacturerEntity.getCity());
        assertEquals(Country.BULGARIA, manufacturerEntity.getCountry());
        assertEquals("New Company Name", manufacturerEntity.getCompanyName());

        // Verify: Repository save method is called
        verify(manufacturerRepository).save(manufacturerEntity);
    }
}
