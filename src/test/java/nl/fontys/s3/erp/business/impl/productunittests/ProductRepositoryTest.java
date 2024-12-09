package nl.fontys.s3.erp.business.impl.productunittests;


import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
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
    private ManufacturerRepository manufacturerRepository;

    private ManufacturerEntity manufacturer1;
    private ManufacturerEntity manufacturer2;

    @BeforeEach
    void setUp(){
        manufacturer1 = manufacturerRepository.save(ManufacturerEntity.builder()
                .companyName("ExampleCompany")
                .country(Country.BULGARIA)
                .city("ExampleCity")
                .build());

        manufacturer2 = manufacturerRepository.save(ManufacturerEntity.builder()
                .companyName("KikkaBoo")
                .country(Country.BULGARIA)
                .city("ExampleCity")
                .build());

        productRepository.save(BabyStrollersEntity.builder()
                .productId(1L)
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
                .manufacturer(manufacturer1)
                .build());

        productRepository.save(BabyStrollersEntity.builder()
                .productId(2L)
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
                .manufacturer(manufacturer2)
                .build());
    }
    @Test
    void existsBySku_returnsTrue_whenSkuExists() {
        // Act
        boolean exists = productRepository.existsBySku("12345678");

        // Assert
        assertTrue(exists);
    }
    @Test
    void existsBySku_returnsFalse_whenSkuDoesNotExist() {
        // Act
        boolean exists = productRepository.existsBySku("99999999");

        // Assert
        assertFalse(exists);
    }
    @Test
    void findAllByManufacturerName_returnsProducts_whenManufacturerExists() {
        // Act
        List<ProductEntity> products = productRepository.findAllByManufacturerName("KikkaBoo");

        // Assert
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product 2", products.get(0).getName());
    }
    @Test
    void findAllByManufacturerName_returnsEmptyList_whenManufacturerDoesNotExist() {
        // Act
        List<ProductEntity> products = productRepository.findAllByManufacturerName("NonExistentManufacturer");

        // Assert
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }
}

