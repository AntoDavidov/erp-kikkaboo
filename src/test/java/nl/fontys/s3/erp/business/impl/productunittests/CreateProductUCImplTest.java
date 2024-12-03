package nl.fontys.s3.erp.business.impl.productunittests;

import nl.fontys.s3.erp.business.dtos.productdto.CreateBabyStrollerRequest;
import nl.fontys.s3.erp.business.dtos.productdto.CreateProductResponse;
import nl.fontys.s3.erp.business.manufacturerusecases.ManufacturerIdValidator;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.exceptions.ProductExistsBySKU;
import nl.fontys.s3.erp.business.impl.productsimpl.CreateProductUseCaseImpl;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
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
public class CreateProductUCImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private ManufacturerIdValidator manufacturerIdValidator;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private CreateProductUseCaseImpl createProductUseCase;

    @Test
    void createProduct_throwsPermissionDenied_whenDepartmentsNull() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(null);
        CreateBabyStrollerRequest request = CreateBabyStrollerRequest.builder()
                .sku("12345678")
                .build();

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> createProductUseCase.createProduct(request));

        // Verify: No interactions with repositories
        verifyNoInteractions(productRepository, manufacturerRepository);
    }

    @Test
    void createProduct_throwsProductExistsBySKU_whenSkuAlreadyExists() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));
        when(productRepository.existsBySku("12345678")).thenReturn(true);

        CreateBabyStrollerRequest request = CreateBabyStrollerRequest.builder()
                .sku("12345678")
                .build();

        // Act & Assert
        assertThrows(ProductExistsBySKU.class, () -> createProductUseCase.createProduct(request));

        // Verify: No further repository interactions
        verify(productRepository, never()).save(any());
    }


    @Test
    void createProduct_throwsManufacturerDoesNotExist_whenManufacturerNotFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));
        when(productRepository.existsBySku("12345678")).thenReturn(false);
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());

        CreateBabyStrollerRequest request = CreateBabyStrollerRequest.builder()
                .sku("12345678")
                .ManufacturerId(1L)
                .build();

        // Act & Assert
        assertThrows(ManufacturerDoesNotExist.class, () -> createProductUseCase.createProduct(request));

        // Verify: Manufacturer validation is performed
        verify(manufacturerIdValidator).validateManufacturerId(1L);
        verify(productRepository, never()).save(any());
    }
    @Test
    void createProduct_savesSuccessfully_whenValidRequest() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));
        when(productRepository.existsBySku("12345678")).thenReturn(false);

        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .build();

        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturer));

        CreateBabyStrollerRequest request = CreateBabyStrollerRequest.builder()
                .sku("12345678")
                .name("Comfy Stroller")
                .shortName("Stroller")
                .description("A comfortable stroller")
                .costPrice(BigDecimal.valueOf(100.00))
                .ManufacturerId(1L)
                .weight(BigDecimal.valueOf(5.5))
                .imageURL("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
                .foldable(true)
                .build();

        BabyStrollersEntity savedEntity = BabyStrollersEntity.builder()
                .productId(1L)
                .sku("12345678")
                .build();

        when(productRepository.save(any(BabyStrollersEntity.class))).thenReturn(savedEntity);

        // Act
        CreateProductResponse response = createProductUseCase.createProduct(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getProductId());

        // Verify: Manufacturer validation and product save
        verify(manufacturerIdValidator).validateManufacturerId(1L);
        verify(productRepository).save(any(BabyStrollersEntity.class));
    }
}
