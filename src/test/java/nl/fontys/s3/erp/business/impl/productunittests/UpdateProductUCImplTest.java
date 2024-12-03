package nl.fontys.s3.erp.business.impl.productunittests;

import nl.fontys.s3.erp.business.dtos.productdto.UpdateBabyStrollerRequest;
import nl.fontys.s3.erp.business.dtos.productdto.UpdateProductRequest;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.exceptions.ProductDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.ProductExistsBySKU;
import nl.fontys.s3.erp.business.impl.productsimpl.UpdateProductUseCaseImpl;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
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
class UpdateProductUCImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private UpdateProductUseCaseImpl updateProductUseCase;

    @Test
    void updateProduct_throwsPermissionDenied_whenDepartmentsNull() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(null);
        UpdateProductRequest request = UpdateProductRequest.builder()
                .id(1L)
                .build();

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> updateProductUseCase.updateProduct(request));

        // Verify: No repository interactions
        verifyNoInteractions(productRepository);
    }

    @Test
    void updateProduct_throwsProductDoesNotExist_whenIdNotFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        UpdateProductRequest request = UpdateProductRequest.builder()
                .id(1L)
                .build();

        // Act & Assert
        assertThrows(ProductDoesNotExist.class, () -> updateProductUseCase.updateProduct(request));

        // Verify: Repository is called to check existence
        verify(productRepository).findById(1L);
    }


    @Test
    void updateProduct_throwsProductExistsBySKU_whenSkuExists() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));

        ManufacturerEntity manufacturer1 = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .city("city")
                .country(Country.BULGARIA)
                .build();

        ProductEntity existingProduct = BabyStrollersEntity.builder()
                .productId(1L)
                .sku("12345678")
                .name("Product 1")
                .shortName("P1")
                .manufacturer(manufacturer1)
                .description("Test description 1")
                .costPrice(BigDecimal.valueOf(100.00))
                .weight(BigDecimal.valueOf(5.0))
                .imageUrl("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
                .foldable(true)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.existsBySku("87654321")).thenReturn(true); // Simulate a different product with this SKU

        UpdateProductRequest request = UpdateBabyStrollerRequest.builder()
                .id(1L)
                .sku("87654321")
                .costPrice(BigDecimal.valueOf(250.00))
                .weight(BigDecimal.valueOf(3.5))
                .imageURL("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE.toString())
                .foldable(true)
                .build();

        // Act & Assert
        assertThrows(ProductExistsBySKU.class, () -> updateProductUseCase.updateProduct(request));

        // Verify: SKU validation is performed
        verify(productRepository).existsBySku("87654321");
    }

    @Test
    void updateProduct_throwsIllegalArgumentException_whenInvalidStrollerType() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));

        BabyStrollersEntity product = BabyStrollersEntity.builder()
                .productId(1L)
                .sku("12345678")
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        UpdateBabyStrollerRequest request = UpdateBabyStrollerRequest.builder()
                .id(1L)
                .sku("12345678")
                .costPrice(BigDecimal.valueOf(250.00))
                .weight(BigDecimal.valueOf(3.5))
                .imageURL("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller("INVALID_TYPE")
                .foldable(true)
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> updateProductUseCase.updateProduct(request));

        // Verify: Repository is called to retrieve product
        verify(productRepository).findById(1L);
    }
    @Test
    void updateProduct_updatesSuccessfully_whenValidRequest() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));

        ManufacturerEntity manufacturer1 = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .city("city")
                .country(Country.BULGARIA)
                .build();

        BabyStrollersEntity product = BabyStrollersEntity.builder()
                .productId(1L)
                .sku("12345678")
                .name("Old Stroller")
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


        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.existsBySku("12345678")).thenReturn(false);

        UpdateBabyStrollerRequest request = UpdateBabyStrollerRequest.builder()
                .id(1L)
                .sku("12345678")
                .name("Updated Stroller")
                .costPrice(BigDecimal.valueOf(250.00))
                .maxWeightCapacity(20.0)
                .weight(BigDecimal.valueOf(3.5))
                .imageURL("image.jpg")
                .maxWeightCapacity(20.0)
                .ageLimit(36)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE.name())
                .foldable(true)
                .build();

        // Act
        updateProductUseCase.updateProduct(request);

        // Assert
        assertEquals("Updated Stroller", product.getName());
        assertEquals(20.0, product.getMaxWeightCapacity());
        assertEquals(36, product.getAgeLimit());
        assertEquals(TypeOfStroller.THREE_IN_ONE, product.getTypeOfStroller());
        assertTrue(product.isFoldable());

        // Verify: Repository save is called
        verify(productRepository).save(product);
    }

}
