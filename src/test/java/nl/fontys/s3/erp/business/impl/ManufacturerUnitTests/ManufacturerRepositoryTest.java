package nl.fontys.s3.erp.business.impl.ManufacturerUnitTests;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ManufacturerRepositoryTest {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private EntityManager em;

    @Test
    void save_shouldSaveManufacturer() {
        ManufacturerEntity manufacturer = createManufacturer("Test1", Country.CHINA, "Varna");

        em.persist(manufacturer);
        em.flush();

        ManufacturerEntity savedManufacturer = em.find(ManufacturerEntity.class, manufacturer.getId());

        assertNotNull(savedManufacturer.getId());
        assertEquals("Test1", savedManufacturer.getCompanyName());
        assertEquals(Country.CHINA, savedManufacturer.getCountry());
        assertEquals("Varna", savedManufacturer.getCity());
    }

    private ManufacturerEntity createManufacturer(String companyName, Country country, String city) {
        return ManufacturerEntity.builder()
                .companyName(companyName)
                .country(country)
                .city(city)
                .build();
    }
}
