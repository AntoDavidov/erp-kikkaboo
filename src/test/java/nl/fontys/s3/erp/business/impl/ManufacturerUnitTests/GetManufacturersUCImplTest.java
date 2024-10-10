package nl.fontys.s3.erp.business.impl.ManufacturerUnitTests;

import nl.fontys.s3.erp.business.impl.ManufacturersImpl.GetManufacturersUseCaseImpl;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.GetManufacturersResponse;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetManufacturersUCImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    GetManufacturersUseCaseImpl getManufacturersUCImpl;

    @Test
    void getManufacturer_ShouldGiveAllManufacturers_HappyFlow() {
        // Step 1: Mock entities
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

        // Step 2: Mock the repository behavior
        when(manufacturerRepository.findAll()).thenReturn(List.of(manufacturerEntity1, manufacturerEntity2));

        // Step 3: Call the method under test
        GetManufacturersResponse response = getManufacturersUCImpl.getManufacturers();

        // Step 4: Validate the expected response
        List<Manufacturer> manufacturerList = response.getManufacturers();
        assertEquals(2, manufacturerList.size());
        assertEquals("Weizhang", manufacturerList.get(0).getCompanyName());
        assertEquals("TRAKIQ", manufacturerList.get(1).getCompanyName());

        // Step 5: Verify interaction with the repository
        verify(manufacturerRepository).findAll();
    }

    @Test
    void getManufacturers_ShouldReturnEmptyList_UnhappyFlow() {
        // Step 1: Mock the repository to return an empty list
        when(manufacturerRepository.findAll()).thenReturn(Collections.emptyList());

        // Step 2: Call the method under test
        GetManufacturersResponse response = getManufacturersUCImpl.getManufacturers();

        // Step 3: Validate that the returned list is empty
        List<Manufacturer> manufacturerList = response.getManufacturers();
        assertEquals(0, manufacturerList.size());

        // Step 4: Verify interaction with the repository
        verify(manufacturerRepository).findAll();
    }
}
