package nl.fontys.s3.erp.business.impl.ProductsUnitTests;

import nl.fontys.s3.erp.business.DTOs.ProductDTOs.GetAllProductsRequest;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.GetAllProductsResponse;
import nl.fontys.s3.erp.business.impl.ProductsImpl.GetProductsUseCaseImpl;
import nl.fontys.s3.erp.domain.products.Product;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProductsUCImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private GetProductsUseCaseImpl getProductsUseCaseImpl;

    @Test
    void getAllProducts_ByManufacturerName_HappyFlow() {
        // Arrange
        GetAllProductsRequest request = new GetAllProductsRequest();
        request.setCompanyName("KikkaBoo");

        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .companyName("KikkaBoo")
                .city("Sofia")
                .build();

        BabyStrollersEntity product1 = BabyStrollersEntity.builder()
                .sku("12345678")
                .name("BabyStroller 1")
                .costPrice(100.00)
                .wholeSalePrice(110.00)
                .recommendedRetailPrice(180.00)
                .manufacturer(manufacturer)
                .build();

        when(productRepository.findAllByManufacturerName("KikkaBoo")).thenReturn(List.of(product1));

        // Act
        GetAllProductsResponse response = getProductsUseCaseImpl.getAllProducts(request);
        List<Product> productList = response.getProducts();

        // Assert
        assertEquals(1, productList.size());
        assertEquals("BabyStroller 1", productList.get(0).getName());
        verify(productRepository).findAllByManufacturerName("KikkaBoo");
    }

    @Test
    void getAllProducts_NoManufacturerName_HappyFlow() {
        GetAllProductsRequest request = new GetAllProductsRequest();

        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .companyName("KikkaBoo")
                .city("Sofia")
                .build();

        BabyStrollersEntity product1 = BabyStrollersEntity.builder()
                .sku("12345678")
                .name("BabyStroller 1")
                .costPrice(100.00)
                .wholeSalePrice(110.00)
                .recommendedRetailPrice(180.00)
                .manufacturer(manufacturer)
                .build();

        BabyStrollersEntity product2 = BabyStrollersEntity.builder()
                .sku("87654321")
                .name("BabyStroller 2")
                .costPrice(120.00)
                .wholeSalePrice(130.00)
                .recommendedRetailPrice(200.00)
                .manufacturer(manufacturer)
                .build();

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // Act
        GetAllProductsResponse response = getProductsUseCaseImpl.getAllProducts(request);
        List<Product> productList = response.getProducts();

        // Assert
        assertEquals(2, productList.size());
        assertEquals("BabyStroller 1", productList.get(0).getName());
        assertEquals("BabyStroller 2", productList.get(1).getName());
        verify(productRepository).findAll();
    }

    @Test
    void getAllProducts_NoProducts_UnhappyFlow() {
        // Arrange
        GetAllProductsRequest request = new GetAllProductsRequest();
        when(productRepository.findAll()).thenReturn(List.of());

        // Act
        GetAllProductsResponse response = getProductsUseCaseImpl.getAllProducts(request);
        List<Product> productList = response.getProducts();

        // Assert
        assertEquals(0, productList.size());
        verify(productRepository).findAll();
    }
}
