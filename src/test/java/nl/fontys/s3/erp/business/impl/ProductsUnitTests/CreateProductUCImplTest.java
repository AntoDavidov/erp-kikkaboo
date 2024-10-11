package nl.fontys.s3.erp.business.impl.ProductsImpl;

import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.CreateManufacturerRequest;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.CreateBabyStrollerRequest;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.CreateProductRequest;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.CreateProductResponse;
import nl.fontys.s3.erp.business.ManufacturerUseCases.ManufacturerIdValidator;
import nl.fontys.s3.erp.business.exceptions.ProductExistsBySKU;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateProductUCImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private CreateProductUseCaseImpl createProductUseCase;

    @Test
    void createBabyStroller_HappyFlow() {
        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .build();

        CreateBabyStrollerRequest request = CreateBabyStrollerRequest.builder()
                .sku("12345678")
                .name("Baby Stroller")
                .shortName("Stroller")
                .description("High quality baby stroller")
                .costPrice(100.0)
                .recommendedRetailPrice(200.0)
                .wholeSalePrice(150.0)
                .weight(10)
                .ManufacturerId(1L)
                .maxWeightCapacity(20)
                .ageLimit(2)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
                .foldable(true)
                .build();

        when(productRepository.existsBySKU("12345678")).thenReturn(false);
        when(manufacturerRepository.findById(1L)).thenReturn(java.util.Optional.of(manufacturer));
        when(productRepository.save(any(BabyStrollersEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CreateProductResponse response = createProductUseCase.createProduct(request);

        assertEquals("12345678", request.getSku());
        verify(productRepository).save(any(BabyStrollersEntity.class));
    }

    @Test
    void createBabyStroller_ProductExistsBySKU_UnhappyFlow() {
        CreateBabyStrollerRequest request = CreateBabyStrollerRequest.builder()
                .sku("12345678")
                .name("Baby Stroller")
                .ManufacturerId(1L)
                .build();

        when(productRepository.existsBySKU("12345678")).thenReturn(true);

        assertThrows(ProductExistsBySKU.class, () -> createProductUseCase.createProduct(request));
        verify(productRepository, never()).save(any(BabyStrollersEntity.class));
    }

    @Test
    void createUnsupportedProductType_UnhappyFlow() {
        CreateProductRequest unsupportedRequest = mock(CreateProductRequest.class);

        assertThrows(IllegalArgumentException.class, () -> createProductUseCase.createProduct(unsupportedRequest));
        verify(productRepository, never()).save(any());
    }
}
