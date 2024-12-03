package nl.fontys.s3.erp.business.impl.ManufacturerUnitTests;

import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.GetManufacturersResponse;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.ManufacturersImpl.GetManufacturersUseCaseImpl;
import nl.fontys.s3.erp.business.impl.converters.ManufacturerConverter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetManufacturersUseCaseImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private GetManufacturersUseCaseImpl getManufacturersUseCase;

    @Test
    void getManufacturers_throwsPermissionDenied_whenDepartmentsNull() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(null);

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> getManufacturersUseCase.getManufacturers());

        // Verify: No interaction with the repository
        verifyNoInteractions(manufacturerRepository);
    }

    @Test
    void getManufacturers_throwsPermissionDenied_whenTradeDepartmentMissing() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("SALES"));

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> getManufacturersUseCase.getManufacturers());

        // Verify: No interaction with the repository
        verifyNoInteractions(manufacturerRepository);
    }

    @Test
    void getManufacturers_returnsEmptyList_whenNoManufacturersFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));
        when(manufacturerRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        GetManufacturersResponse response = getManufacturersUseCase.getManufacturers();

        // Assert
        assertNotNull(response);
        assertTrue(response.getManufacturers().isEmpty());

        // Verify: Interaction with the repository
        verify(manufacturerRepository).findAll();
    }

    @Test
    void getManufacturers_returnsListOfManufacturers_whenManufacturersExist() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));

        ManufacturerEntity manufacturerEntity1 = ManufacturerEntity.builder()
                .id(1L)
                .companyName("Manufacturer 1")
                .city("City 1")
                .country(Country.CHINA)
                .build();

        ManufacturerEntity manufacturerEntity2 = ManufacturerEntity.builder()
                .id(2L)
                .companyName("Manufacturer 2")
                .city("City 2")
                .country(Country.BULGARIA)
                .build();

        List<ManufacturerEntity> entities = List.of(manufacturerEntity1, manufacturerEntity2);

        when(manufacturerRepository.findAll()).thenReturn(entities);

        // Directly use the ManufacturerConverter in the test
        Manufacturer manufacturer1 = ManufacturerConverter.convert(manufacturerEntity1);
        Manufacturer manufacturer2 = ManufacturerConverter.convert(manufacturerEntity2);

        // Act
        GetManufacturersResponse response = getManufacturersUseCase.getManufacturers();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getManufacturers().size());
        assertEquals(manufacturer1, response.getManufacturers().get(0));
        assertEquals(manufacturer2, response.getManufacturers().get(1));

        // Verify: Interaction with the repository
        verify(manufacturerRepository).findAll();
    }
}
