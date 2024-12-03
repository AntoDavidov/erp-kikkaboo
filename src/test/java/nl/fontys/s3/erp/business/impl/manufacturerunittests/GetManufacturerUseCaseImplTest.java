package nl.fontys.s3.erp.business.impl.manufacturerunittests;

import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.converters.ManufacturerConverter;
import nl.fontys.s3.erp.business.impl.manufacturersimpl.GetManufacturerUseCaseImpl;
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

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetManufacturerUseCaseImplTest {
    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private GetManufacturerUseCaseImpl getManufacturerUseCase;

    @Test
    void getManufacturer_throwsPermissionDenied_whenDepartmentsNotAuthorized() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("SALES"));

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> getManufacturerUseCase.getManufacturer(1L));

        // Verify: No repository interactions
        verifyNoInteractions(manufacturerRepository);
    }
    @Test
    void getManufacturer_throwsManufacturerDoesNotExist_whenIdNotFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ManufacturerDoesNotExist.class, () -> getManufacturerUseCase.getManufacturer(1L));

        // Verify: Interaction with repository
        verify(manufacturerRepository).findById(1L);
    }
    @Test
    void getManufacturer_returnsManufacturer_whenManufacturerExists() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));

        ManufacturerEntity manufacturerEntity = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .city("Sofia")
                .country(Country.BULGARIA)
                .build();

        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturerEntity));

        Manufacturer expectedManufacturer = ManufacturerConverter.convert(manufacturerEntity);

        // Act
        Manufacturer result = getManufacturerUseCase.getManufacturer(1L);

        // Assert
        assertNotNull(result);
        assertEquals(expectedManufacturer, result);

        // Verify: Interaction with repository
        verify(manufacturerRepository).findById(1L);
    }
}
