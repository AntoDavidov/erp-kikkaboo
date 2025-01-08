package nl.fontys.s3.erp.business.impl.productunittests;

import nl.fontys.s3.erp.business.dtos.productdto.GetAllProductsRequest;
import nl.fontys.s3.erp.business.dtos.productdto.GetAllProductsResponse;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.productsimpl.GetProductsUseCaseImpl;
import nl.fontys.s3.erp.business.impl.converters.ProductConverter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import nl.fontys.s3.erp.domain.users.Status;
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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductsUCImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private GetProductsUseCaseImpl getProductsUseCase;

    @Test
    void getAllProducts_throwsPermissionDenied_whenDepartmentsNull() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(null);
        GetAllProductsRequest request = new GetAllProductsRequest(null);

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> getProductsUseCase.getAllProducts(request));

        // Verify: No repository interactions
        verifyNoInteractions(productRepository);
    }

    @Test
    void getAllProducts_returnsEmptyList_whenNoProductsFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        GetAllProductsRequest request = new GetAllProductsRequest(null);

        // Act
        GetAllProductsResponse response = getProductsUseCase.getAllProducts(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.getProducts().isEmpty());

        // Verify: Repository interactions
        verify(productRepository).findAll();
    }

    @Test
    void getAllProducts_returnsAllProducts_whenNoFilterProvided() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));

        ManufacturerEntity manufacturer1 = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .city("city")
                .country(Country.BULGARIA)
                .status(Status.ACTIVE)
                .build();

        ProductEntity productEntity1 = BabyStrollersEntity.builder()
                .productId(1L)
                .sku("12345678")
                .name("Product 1")
                .shortName("P1")
                .manufacturer(manufacturer1)
                .description("Test description 1")
                .costPrice(BigDecimal.valueOf(100.00))
                .weight(BigDecimal.valueOf(5.5))
                .imageUrl("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
                .foldable(true)
                .build();

        ProductEntity productEntity2 = BabyStrollersEntity.builder()
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

        List<ProductEntity> entities = List.of(productEntity1, productEntity2);

        when(productRepository.findAll()).thenReturn(entities);

        GetAllProductsRequest request = new GetAllProductsRequest(null);

        // Act
        GetAllProductsResponse response = getProductsUseCase.getAllProducts(request);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getProducts().size());
        assertEquals(ProductConverter.convert(productEntity1), response.getProducts().get(0));
        assertEquals(ProductConverter.convert(productEntity2), response.getProducts().get(1));

        // Verify: Repository interactions
        verify(productRepository).findAll();
    }
    @Test
    void getAllProducts_filtersProductsByManufacturerName() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));

        ManufacturerEntity manufacturer1 = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .city("city")
                .country(Country.BULGARIA)
                .status(Status.ACTIVE)
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

        List<ProductEntity> entities = List.of(productEntity);

        when(productRepository.findAllByManufacturerName("KikkaBoo")).thenReturn(entities);

        GetAllProductsRequest request = new GetAllProductsRequest("KikkaBoo");

        // Act
        GetAllProductsResponse response = getProductsUseCase.getAllProducts(request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getProducts().size());
        assertEquals(ProductConverter.convert(productEntity), response.getProducts().get(0));

        // Verify: Repository interactions
        verify(productRepository).findAllByManufacturerName("KikkaBoo");
    }
}
