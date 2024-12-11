package nl.fontys.s3.erp.business.impl.productunittests;


import jakarta.persistence.EntityManager;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager em;

    @Test
    void existsBySku_returnsTrue_whenProductExists() {
        // Arrange
        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .companyName("KikkaBoo")
                .city("Sofia")
                .country(Country.BULGARIA)
                .build();
        em.persist(manufacturer);

        ProductEntity product = BabyStrollersEntity.builder()
                .sku("12345678")
                .name("Product 1")
                .shortName("P1")
                .description("Test description 2")
                .costPrice(BigDecimal.valueOf(200.00))
                .weight(BigDecimal.valueOf(3.5))
                .imageUrl("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
                .foldable(true)
                .manufacturer(manufacturer)
                .build();
        em.persist(product);
        em.flush();

        // Act
        boolean exists = productRepository.existsBySku("12345678");

        // Assert
        assertTrue(exists);
    }
    @Test
    void existsBySku_returnsFalse_whenProductDoesNotExist() {
        // Act
        boolean exists = productRepository.existsBySku("87654321");

        // Assert
        assertFalse(exists);
    }
    @Test
    void findAllByManufacturerName_returnsProducts_whenProductsExist() {
        // Arrange
        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .companyName("KikkaBoo")
                .city("Sofia")
                .country(Country.BULGARIA)
                .build();
        em.persist(manufacturer);

        ProductEntity product1 = BabyStrollersEntity.builder()
                .sku("12345678")
                .name("Product 1")
                .shortName("P1")
                .description("Test description 2")
                .costPrice(BigDecimal.valueOf(200.00))
                .weight(BigDecimal.valueOf(3.5))
                .imageUrl("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
                .foldable(true)
                .manufacturer(manufacturer)
                .build();
        ProductEntity product2 = BabyStrollersEntity.builder()
                .sku("87654321")
                .name("Product 2")
                .shortName("P2")
                .description("Test description 2")
                .costPrice(BigDecimal.valueOf(200.00))
                .weight(BigDecimal.valueOf(3.5))
                .imageUrl("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
                .foldable(true)
                .manufacturer(manufacturer)
                .build();
        em.persist(product1);
        em.persist(product2);
        em.flush();

        // Act
        List<ProductEntity> products = productRepository.findAllByManufacturerName("KikkaBoo");

        // Assert
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("12345678", products.get(0).getSku());
        assertEquals("87654321", products.get(1).getSku());
    }

    @Test
    void findAllByManufacturerName_returnsEmptyList_whenNoProductsExist() {
        // Act
        List<ProductEntity> products = productRepository.findAllByManufacturerName("NonExistentManufacturer");

        // Assert
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }
}

