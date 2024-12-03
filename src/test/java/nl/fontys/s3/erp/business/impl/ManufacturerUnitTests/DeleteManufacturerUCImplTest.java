package nl.fontys.s3.erp.business.impl.ManufacturerUnitTests;

import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.ManufacturersImpl.DeleteManufacturerUseCaseImpl;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteManufacturerUCImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private DeleteManufacturerUseCaseImpl deleteManufacturerUseCase;

    @Test
    void deleteManufacturer_throwsPermissionDenied_whenDepartmentsNull() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(null);

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> deleteManufacturerUseCase.deleteManufacturer(1L));

        // Verify: No interaction with the repository
        verifyNoInteractions(manufacturerRepository);
    }

    @Test
    void deleteManufacturer_throwsPermissionDenied_whenTradeDepartmentMissing() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("SALES"));

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> deleteManufacturerUseCase.deleteManufacturer(1L));

        // Verify: No interaction with the repository
        verifyNoInteractions(manufacturerRepository);
    }
    @Test
    void deleteManufacturer_throwsManufacturerDoesNotExist_whenIdNotFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));
        when(manufacturerRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ManufacturerDoesNotExist.class, () -> deleteManufacturerUseCase.deleteManufacturer(1L));

        // Verify: `deleteById` should not be called
        verify(manufacturerRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteManufacturer_deletesSuccessfully_whenValidRequest() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));
        when(manufacturerRepository.existsById(1L)).thenReturn(true);

        // Act
        deleteManufacturerUseCase.deleteManufacturer(1L);

        // Assert: Verify repository interaction
        verify(manufacturerRepository).deleteById(1L);
    }
}
