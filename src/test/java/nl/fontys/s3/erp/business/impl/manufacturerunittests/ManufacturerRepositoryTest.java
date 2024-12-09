package nl.fontys.s3.erp.business.impl.manufacturerunittests;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ManufacturerRepositoryTest {

    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private EntityManager em;

    @Test
    void existsByCompanyNameCustom_returnsTrue_whenManufacturerExists() {
        // Arrange
        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .city("Sofia")
                .country(Country.BULGARIA)
                .build();
        manufacturerRepository.saveAndFlush(manufacturer);

        // Act
        boolean exists = manufacturerRepository.existsByCompanyNameCustom("KikkaBoo");

        // Assert
        assertTrue(exists);
    }

    @Test
    void existsByCompanyNameCustom_returnsFalse_whenManufacturerDoesNotExist() {
        // Act
        boolean exists = manufacturerRepository.existsByCompanyNameCustom("NonExistentCompany");

        // Assert
        assertFalse(exists);
    }
    @Test
    void getPlaceholderManufacturer_returnsPlaceholder_whenExists() {
        // Arrange
        ManufacturerEntity placeholder = ManufacturerEntity.builder()
                .id(1L)
                .companyName("Placeholder")
                .city("Unknown City")
                .country(Country.UNKNOWN)
                .build();
        manufacturerRepository.saveAndFlush(placeholder);

        ManufacturerEntity persisted = manufacturerRepository.findById(1L).orElse(null);
        assertNotNull(persisted, "Placeholder manufacturer was not saved correctly");

        // Act
        ManufacturerEntity result = manufacturerRepository.getPlaceholderManufacturer(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Placeholder", result.getCompanyName());
    }
    @Test
    void getPlaceholderManufacturer_returnsNull_whenPlaceholderDoesNotExist() {
        // Act
        ManufacturerEntity result = manufacturerRepository.getPlaceholderManufacturer(-1L);

        // Assert
        assertNull(result);
    }
}
