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
        CreateManufacturerRequest request = new CreateManufacturerRequest(Country.BULGARIA, "KikkaBoo", "Sofia");

        ManufacturerEntity manufacturerEntity = ManufacturerEntity.builder()
                .companyName("KikkaBoo")
                .country(Country.BULGARIA)
                .city("Sofia")
                .build();

        when(manufacturerRepository.existsByCompanyName(anyString())).thenReturn(false);  // Use anyString() for flexibility
        when(manufacturerRepository.save(any(ManufacturerEntity.class))).thenReturn(manufacturerEntity);

        CreateManufacturerResponse response = createManufacturerUseCase.createManufacturer(request);

        assertEquals("KikkaBoo", manufacturerEntity.getCompanyName());
        verify(manufacturerRepository).existsByCompanyName("KikkaBoo");
        verify(manufacturerRepository).save(any(ManufacturerEntity.class));
    }

    @Test
    void createManufacturer_AlreadyExists_UnhappyFlow() {
        CreateManufacturerRequest request = new CreateManufacturerRequest(Country.BULGARIA, "KikkaBoo", "Sofia");
        when(manufacturerRepository.existsByCompanyName(anyString())).thenReturn(true);  // Use anyString() for flexibility

        assertThrows(ManufacturerAlreadyExists.class, () -> {
            createManufacturerUseCase.createManufacturer(request);
        });

        verify(manufacturerRepository).existsByCompanyName("KikkaBoo");
        verify(manufacturerRepository, never()).save(any(ManufacturerEntity.class));
    }
}
