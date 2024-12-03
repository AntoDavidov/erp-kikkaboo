package nl.fontys.s3.erp.business.impl.manufacturerunittests;

import nl.fontys.s3.erp.business.dtos.manufacturerdto.CreateManufacturerRequest;
import nl.fontys.s3.erp.business.dtos.manufacturerdto.CreateManufacturerResponse;
import nl.fontys.s3.erp.business.exceptions.ManufacturerAlreadyExists;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.manufacturersimpl.CreateManufacturerUseCaseImpl;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateManufacturerUCImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private CreateManufacturerUseCaseImpl createManufacturerUseCase;

    @Test
    void createManufacturer_throwsPermissionDenied_whenDepartmentsNull() {
        // Arrange: No need to stub repository calls since the execution halts before they are called
        when(accessToken.getDepartments()).thenReturn(null);

        CreateManufacturerRequest request = CreateManufacturerRequest.builder()
                .companyName("Test Company")
                .city("Test City")
                .country(Country.BULGARIA)
                .build();

        // Act & Assert
        assertThrows(PermissionDenied.class,
                () -> createManufacturerUseCase.createManufacturer(request));

        // Verify: Ensure no unnecessary interactions with manufacturerRepository
        verifyNoInteractions(manufacturerRepository);
    }


    @Test
    void createManufacturer_throwsPermissionDenied_whenTradeDepartmentMissing() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("SALES"));
        CreateManufacturerRequest request = CreateManufacturerRequest.builder()
                .companyName("Test Company")
                .city("Test City")
                .country(Country.CHINA)
                .build();

        // Act & Assert
        assertThrows(PermissionDenied.class,
                () -> createManufacturerUseCase.createManufacturer(request));
        verify(manufacturerRepository, never()).existsByCompanyNameCustom(any());
    }

    @Test
    void createManufacturer_throwsManufacturerAlreadyExists_whenCompanyNameExists() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));
        when(manufacturerRepository.existsByCompanyNameCustom("Test Company")).thenReturn(true);
        CreateManufacturerRequest request = CreateManufacturerRequest.builder()
                .companyName("Test Company")
                .city("Test City")
                .country(Country.CHINA)
                .build();

        // Act & Assert
        assertThrows(ManufacturerAlreadyExists.class,
                () -> createManufacturerUseCase.createManufacturer(request));
        verify(manufacturerRepository, never()).save(any());
    }

    @Test
    void createManufacturer_savesAndReturnsResponse_whenValidRequest() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));
        when(manufacturerRepository.existsByCompanyNameCustom("Test Company")).thenReturn(false);

        ManufacturerEntity savedEntity = ManufacturerEntity.builder()
                .id(1L)
                .companyName("Test Company")
                .city("Test City")
                .country(Country.BULGARIA)
                .build();

        when(manufacturerRepository.save(any(ManufacturerEntity.class))).thenReturn(savedEntity);

        CreateManufacturerRequest request = CreateManufacturerRequest.builder()
                .companyName("Test Company")
                .city("Test City")
                .country(Country.BULGARIA)
                .build();

        // Act
        CreateManufacturerResponse response = createManufacturerUseCase.createManufacturer(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getManufacturerId());
        verify(manufacturerRepository).save(any(ManufacturerEntity.class));
    }
}
