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
                .companyName("KikkaBoo")
                .city("Sofia")
                .country(Country.BULGARIA)
                .build();
        em.persist(manufacturer);
        em.flush();

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
}
