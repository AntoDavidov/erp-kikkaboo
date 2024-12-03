package nl.fontys.s3.erp.business.impl.productunittests;

import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.business.impl.productsimpl.DeleteProductUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteProductUCImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private DeleteProductUseCaseImpl deleteProductUseCase;

    @Test
    void deleteProduct_throwsPermissionDenied_whenDepartmentsNull() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(null);

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> deleteProductUseCase.deleteProduct(1L));

        // Verify: No interaction with the repository
        verifyNoInteractions(productRepository);
    }


    @Test
    void deleteProduct_throwsPermissionDenied_whenTradeDepartmentMissing() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("SALES"));

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> deleteProductUseCase.deleteProduct(1L));

        // Verify: No interaction with the repository
        verifyNoInteractions(productRepository);
    }

    @Test
    void deleteProduct_deletesSuccessfully_whenValidRequest() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("PRODUCT"));

        // Act
        deleteProductUseCase.deleteProduct(1L);

        // Assert
        // Verify: Repository delete method is called
        verify(productRepository).deleteById(1L);
    }
}
