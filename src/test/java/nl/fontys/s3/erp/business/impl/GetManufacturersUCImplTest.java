package nl.fontys.s3.erp.business.impl;

import nl.fontys.s3.erp.business.impl.ManufacturersImpl.GetManufacturersUseCaseImpl;
import nl.fontys.s3.erp.business.impl.converters.ManufacturerConverter;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.persistence.DTOs.GetManufacturersResponse;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetManufacturersUCImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    GetManufacturersUseCaseImpl getManufacturersUCImpl;

    @Test
    void getManufacturer_ShouldGiveAllManufacturers() {
        //step 1: setup expectation mock behavior
        ManufacturerEntity manufacturerEntity1 = ManufacturerEntity.builder()
                .companyName("Weizhang")
                .country(Country.CHINA)
                .city("Guangzhou")
                .build();

        ManufacturerEntity manufacturerEntity2 = ManufacturerEntity.builder()
                .companyName("TRAKIQ")
                .country(Country.BULGARIA)
                .city("Plovdiv")
                .build();

        // Step 2: Mock the repository to return the entities
        when(manufacturerRepository.findAll()).thenReturn(List.of(manufacturerEntity1, manufacturerEntity2));

        //step 3: call method under test
        GetManufacturersResponse response = getManufacturersUCImpl.getManufacturers();

        // Step 4: Get the manufacturers from the response
        List<Manufacturer> manufacturerList = response.getManufacturers();

        //step 5: validate expected mock behavior
        assertEquals(2, manufacturerList.size());
        assertEquals("Weizhang", manufacturerList.get(0).getCompanyName());
        assertEquals("TRAKIQ", manufacturerList.get(1).getCompanyName());
        verify(manufacturerRepository).findAll();
    }
}
