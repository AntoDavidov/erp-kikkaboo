package nl.fontys.s3.erp.business.impl.ProductsUnitTests;

import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.business.ProductsUseCases.DeleteProductUseCase;
import nl.fontys.s3.erp.business.impl.ProductsImpl.DeleteProductUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteProductUCImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductUseCaseImpl deleteProductUseCase;

    @Test
    void deleteProduct_HappyFlow() {
        long productId = 1L;
        deleteProductUseCase.deleteProduct(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProduct_UnhappyFlow_ProductNotFound() {
        long nonExistingProductId = 99L;
        doNothing().when(productRepository).deleteById(nonExistingProductId);
        deleteProductUseCase.deleteProduct(nonExistingProductId);
        verify(productRepository).deleteById(nonExistingProductId);
    }
}
