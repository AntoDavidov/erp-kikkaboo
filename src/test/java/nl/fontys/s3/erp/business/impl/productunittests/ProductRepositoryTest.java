package nl.fontys.s3.erp.business.impl.productunittests;


import jakarta.persistence.EntityManager;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private EntityManager em;

    @Test
    void save_shouldSaveBabyStrollerProduct() {
        // Arrange
        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .companyName("Test Company")
                .country(Country.BULGARIA)
                .city("Plovdiv")
                .build();
        manufacturer = manufacturerRepository.save(manufacturer);

        // Arrange
        BabyStrollersEntity babyStroller = BabyStrollersEntity.builder()
                .sku("12345678")
                .name("Test Stroller")
                .shortName("Stroller")
                .description("A test stroller")
                .costPrice(BigDecimal.valueOf(110))
                .weight(BigDecimal.valueOf(15))
                .manufacturer(manufacturer)
                .maxWeightCapacity(15)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.PUSHCHAIR)
                .foldable(true)
                .build();

        // Act
        BabyStrollersEntity savedProduct = (BabyStrollersEntity) productRepository.save(babyStroller);

        // Assert
        assertThat(savedProduct.getProductId()).isNotNull();
        assertThat(savedProduct.getSku()).isEqualTo("12345678");
        assertThat(savedProduct.getManufacturer().getCompanyName()).isEqualTo("Test Company");
    }
}

