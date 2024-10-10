package nl.fontys.s3.erp.business.impl.ManufacturerUnitTests;

import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.CreateManufacturerRequest;
import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.CreateManufacturerResponse;
import nl.fontys.s3.erp.business.exceptions.ManufacturerAlreadyExists;
import nl.fontys.s3.erp.business.impl.ManufacturersImpl.CreateManufacturerUseCaseImpl;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateManufacturerUCImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private CreateManufacturerUseCaseImpl createManufacturerUseCase;

    @Test
    void createManufacturer_HappyFlow() {
        // Step 1: Setup request and expected entity
        CreateManufacturerRequest request = new CreateManufacturerRequest(Country.BULGARIA, "Sofia", "KikkaBoo");

        ManufacturerEntity manufacturerEntity = ManufacturerEntity.builder()
                .companyName("KikkaBoo")
                .country(Country.BULGARIA)
                .city("Sofia")
                .build();

        // Step 2: Mock repository behavior
        when(manufacturerRepository.existsByCompanyName("KikkaBoo")).thenReturn(false);
        when(manufacturerRepository.save(any(ManufacturerEntity.class))).thenReturn(manufacturerEntity);

        // Step 3: Call the method under test
        CreateManufacturerResponse response = createManufacturerUseCase.createManufacturer(request);

        // Step 4: Validate the response
        assertEquals("KikkaBoo", manufacturerEntity.getCompanyName());
        verify(manufacturerRepository).existsByCompanyName("KikkaBoo");
        verify(manufacturerRepository).save(any(ManufacturerEntity.class));
    }

    @Test
    void createManufacturer_AlreadyExists_UnhappyFlow() {
        // Step 1: Setup request
        CreateManufacturerRequest request = new CreateManufacturerRequest(Country.BULGARIA, "Sofia", "KikkaBoo");

        // Step 2: Mock repository behavior to simulate existing manufacturer
        when(manufacturerRepository.existsByCompanyName("KikkaBoo")).thenReturn(true);

        // Step 3: Call the method under test and expect exception
        assertThrows(ManufacturerAlreadyExists.class, () -> {
            createManufacturerUseCase.createManufacturer(request);
        });

        // Step 4: Verify interactions with repository
        verify(manufacturerRepository).existsByCompanyName("KikkaBoo");
        verify(manufacturerRepository, never()).save(any(ManufacturerEntity.class));
    }
}