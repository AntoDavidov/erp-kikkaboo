package nl.fontys.s3.erp.business.impl.ManufacturerUnitTests;

import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.impl.ManufacturersImpl.GetManufacturerUseCaseImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetManufacturerUCImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private GetManufacturerUseCaseImpl getManufacturerByIdUseCase;

    @Test
    void getManufacturerById_HappyFlow() {
        ManufacturerEntity manufacturerEntity = ManufacturerEntity.builder()
                .companyName("KikkaBoo")
                .country(Country.BULGARIA)
                .city("Sofia")
                .build();


        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturerEntity));
        Manufacturer manufacturer = getManufacturerByIdUseCase.getManufacturer(1L);
        assertNotNull(manufacturer);
        assertEquals("KikkaBoo", manufacturer.getCompanyName());
        verify(manufacturerRepository).findById(1L);
    }

    @Test
    void getManufacturerById_NotFound_UnhappyFlow() {
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ManufacturerDoesNotExist.class, () -> {
            getManufacturerByIdUseCase.getManufacturer(1L);
        });
        verify(manufacturerRepository).findById(1L);
    }
}
