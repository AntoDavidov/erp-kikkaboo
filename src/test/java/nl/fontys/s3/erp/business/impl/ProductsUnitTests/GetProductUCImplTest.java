package nl.fontys.s3.erp.business.impl.ProductsUnitTests;

import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.exceptions.ProductDoesNotExist;
import nl.fontys.s3.erp.business.impl.converters.ProductConverter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.Product;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import nl.fontys.s3.erp.business.impl.productsimpl.GetProductUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetProductUCImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private GetProductUseCaseImpl getProductUseCase;

    @Test
    void getProduct_throwsPermissionDenied_whenDepartmentsNull() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(null);

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> getProductUseCase.getProduct(1L));

        // Verify: No repository interaction
        verifyNoInteractions(productRepository);
    }


    @Test
    void getProduct_throwsPermissionDenied_whenProductDepartmentMissing() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("SALES"));

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> getProductUseCase.getProduct(1L));

        // Verify: No repository interaction
        verifyNoInteractions(productRepository);
    }
    @Test
    void getProduct_throwsProductDoesNotExist_whenIdNotFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductDoesNotExist.class, () -> getProductUseCase.getProduct(1L));

        // Verify: Repository is called to check existence
        verify(productRepository).findById(1L);
    }
    @Test
    void getProduct_returnsProduct_whenValidRequest() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));

        ManufacturerEntity manufacturer1 = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .city("city")
                .country(Country.BULGARIA)
                .build();

        ProductEntity productEntity = BabyStrollersEntity.builder()
                .productId(2L)
                .sku("87654321")
                .name("Product 2")
                .shortName("P2")
                .manufacturer(manufacturer1)
                .description("Test description 2")
                .costPrice(BigDecimal.valueOf(200.00))
                .weight(BigDecimal.valueOf(3.5))
                .imageUrl("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
                .foldable(true)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        Product expectedProduct = ProductConverter.convert(productEntity);

        // Act
        Product actualProduct = getProductUseCase.getProduct(1L);

        // Assert
        assertNotNull(actualProduct);
        assertEquals(expectedProduct, actualProduct);

        // Verify: Repository and converter interactions
        verify(productRepository).findById(1L);
    }
}
