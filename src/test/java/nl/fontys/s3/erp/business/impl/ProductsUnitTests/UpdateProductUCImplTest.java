package nl.fontys.s3.erp.business.impl.ProductsUnitTests;

import nl.fontys.s3.erp.business.DTOs.ProductDTOs.UpdateBabyStrollerRequest;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.UpdateProductRequest;
import nl.fontys.s3.erp.business.exceptions.ProductDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.ProductExistsBySKU;
import nl.fontys.s3.erp.business.impl.ProductsImpl.UpdateProductUseCaseImpl;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateProductUCImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductUseCaseImpl updateProductUseCase;

    @Test
    void updateProduct_HappyFlow() {
        UpdateBabyStrollerRequest request = UpdateBabyStrollerRequest.builder()
                .id(1L)
                .sku("12345678")
                .name("New Stroller")
                .shortName("Stroller")
                .description("Updated description")
                .costPrice(200)
                .recommendedRetailPrice(300)
                .wholeSalePrice(250)
                .imageURL("imageUrl")
                .weight(4)
                .maxWeightCapacity(25)
                .ageLimit(36)
                .typeOfStroller("THREE_IN_ONE")
                .foldable(true)
                .build();

        BabyStrollersEntity existingProduct = BabyStrollersEntity.builder()
                .productId(1L)
                .sku("12345678")
                .name("Old Stroller")
                .shortName("Old")
                .description("Old description")
                .costPrice(150)
                .recommendedRetailPrice(250)
                .wholeSalePrice(200)
                .weight(5)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.existsBySKU(request.getSku())).thenReturn(false);

        updateProductUseCase.updateProduct(request);

        verify(productRepository).save(existingProduct);
        assertEquals(request.getName(), existingProduct.getName());
        assertEquals(request.getDescription(), existingProduct.getDescription());
        assertEquals(request.getCostPrice(), existingProduct.getCostPrice());
        verify(productRepository).save(existingProduct);
    }

    @Test
    void updateProduct_ProductDoesNotExist_UnhappyFlow() {
        UpdateProductRequest request = UpdateProductRequest.builder()
                .id(1L)
                .sku("12345678")
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductDoesNotExist.class, () -> updateProductUseCase.updateProduct(request));
        verify(productRepository, never()).save(any(ProductEntity.class));
    }

    @Test
    void updateProduct_ProductExistsBySKU_UnhappyFlow() {
        UpdateBabyStrollerRequest request = UpdateBabyStrollerRequest.builder()
                .id(1L)
                .sku("87654321")
                .name("New Stroller")
                .build();

        BabyStrollersEntity existingProduct = BabyStrollersEntity.builder()
                .productId(1L)
                .sku("12345678")
                .name("Old Stroller")
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.existsBySKU(request.getSku())).thenReturn(true);  // SKU already exists

        assertThrows(ProductExistsBySKU.class, () -> updateProductUseCase.updateProduct(request));
        verify(productRepository, never()). save(any(ProductEntity.class));
    }
}
