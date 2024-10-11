package nl.fontys.s3.erp.business.impl.ProductsUnitTests;

import nl.fontys.s3.erp.business.exceptions.ProductDoesNotExist;
import nl.fontys.s3.erp.business.impl.converters.ProductConverter;
import nl.fontys.s3.erp.domain.products.BabyStrollers;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.products.Product;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import nl.fontys.s3.erp.business.impl.ProductsImpl.GetProductUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetProductUCImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductUseCaseImpl getProductUseCase;

    @Test
    void getProduct_HappyFlow() {
        ManufacturerEntity manufacturer = ManufacturerEntity.builder()
                .id(1L)
                .companyName("KikkaBoo")
                .city("Sofia")
                .country(Country.BULGARIA)
                .build();

        BabyStrollersEntity productEntity = BabyStrollersEntity.builder()
                .productId(1L)
                .name("Baby Stroller Model X")
                .sku("12345678")
                .manufacturer(manufacturer)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        Product product = getProductUseCase.getProduct(1L);

        assertNotNull(product);
        assertEquals("Baby Stroller Model X", product.getName());
        assertEquals("12345678", product.getSku());
        assertNotNull(product.getManufacturer());
        assertEquals("KikkaBoo", product.getManufacturer().getCompanyName());

        verify(productRepository).findById(1L);
    }


    @Test
    void getProduct_UnhappyFlow_ProductDoesNotExist() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductDoesNotExist.class, () -> getProductUseCase.getProduct(1L));

        verify(productRepository).findById(1L);
    }
}
