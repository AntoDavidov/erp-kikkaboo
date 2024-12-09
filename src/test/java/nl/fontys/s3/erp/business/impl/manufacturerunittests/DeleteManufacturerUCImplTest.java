package nl.fontys.s3.erp.business.impl.manufacturerunittests;

import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.manufacturersimpl.DeleteManufacturerUseCaseImpl;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.TypeOfStroller;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteManufacturerUCImplTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private AccessToken accessToken;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteManufacturerUseCaseImpl deleteManufacturerUseCase;

    @Test
    void deleteManufacturer_throwsPermissionDenied_whenDepartmentsNull() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(null);

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> deleteManufacturerUseCase.deleteManufacturer(1L));

        // Verify: No interaction with repositories
        verifyNoInteractions(manufacturerRepository);
        verifyNoInteractions(productRepository);
    }

    @Test
    void deleteManufacturer_throwsPermissionDenied_whenTradeDepartmentMissing() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("SALES"));

        // Act & Assert
        assertThrows(PermissionDenied.class, () -> deleteManufacturerUseCase.deleteManufacturer(1L));

        // Verify: No interaction with the repository
        verifyNoInteractions(manufacturerRepository);
        verifyNoInteractions(productRepository);
    }
    @Test
    void deleteManufacturer_throwsManufacturerDoesNotExist_whenIdNotFound() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));
        when(manufacturerRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ManufacturerDoesNotExist.class, () -> deleteManufacturerUseCase.deleteManufacturer(1L));

        // Verify: `deleteById` should not be called
        verify(manufacturerRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteManufacturer_deletesSuccessfully_whenNoProducts() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));

        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .products(List.of())
                .build();

        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturer));

        // Act
        deleteManufacturerUseCase.deleteManufacturer(1L);

        // Assert
        verify(manufacturerRepository).deleteById(1L);
        verifyNoInteractions(productRepository);
    }

    @Test
    void deleteManufacturer_reassignsProductsAndDeletesManufacturer() {
        // Arrange
        when(accessToken.getDepartments()).thenReturn(Set.of("TRADE"));

        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .country(Country.BULGARIA)
                .city("PANAMA")
                .products(List.of(new BabyStrollersEntity(), new BabyStrollersEntity()))
                .build();

        ManufacturerEntity placeholder = ManufacturerEntity.builder()
                .id(-1L)
                .companyName("UNKNOWN")
                .country(Country.BULGARIA)
                .city("PANAMA")
                .build();

        ProductEntity product1 = BabyStrollersEntity.builder()
                .productId(1L)
                .manufacturer(manufacturer)
                .sku("87654321")
                .name("Product 2")
                .shortName("P2")
                .description("Test description 2")
                .costPrice(BigDecimal.valueOf(200.00))
                .weight(BigDecimal.valueOf(3.5))
                .imageUrl("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
                .foldable(true)
                .build();
        ProductEntity product2 = BabyStrollersEntity.builder()
                .productId(2L)
                .manufacturer(manufacturer)
                .sku("87654321")
                .name("Product 2")
                .shortName("P2")
                .description("Test description 2")
                .costPrice(BigDecimal.valueOf(200.00))
                .weight(BigDecimal.valueOf(3.5))
                .imageUrl("image.jpg")
                .maxWeightCapacity(15.0)
                .ageLimit(3)
                .typeOfStroller(TypeOfStroller.THREE_IN_ONE)
                .foldable(true)
                .build();

        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturer));
        when(manufacturerRepository.getPlaceholderManufacturer(-1L)).thenReturn(placeholder);
        when(productRepository.findAllByManufacturerName("KikkaBoo")).thenReturn(List.of(product1, product2));

        // Act
        deleteManufacturerUseCase.deleteManufacturer(1L);

        // Assert
        assertEquals(placeholder, product1.getManufacturer());
        assertEquals(placeholder, product2.getManufacturer());
        verify(productRepository).saveAll(List.of(product1, product2));
        verify(manufacturerRepository).deleteById(1L);
    }
}
