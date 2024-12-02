package nl.fontys.s3.erp.business.impl.ProductsImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ProductsUseCases.GetProductUseCase;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.exceptions.ProductDoesNotExist;
import nl.fontys.s3.erp.business.impl.converters.ProductConverter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.products.Product;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetProductUseCaseImpl implements GetProductUseCase {
    private final AccessToken accessToken;
    private ProductRepository productRepository;

    @Override
    public Product getProduct(long id) {
        if(accessToken.getDepartments() == null || !accessToken.getDepartments().contains("PRODUCT")) {
            throw new PermissionDenied("get product failed.");
        }
        return productRepository.findById(id)
                .map(ProductConverter::convert)
                .orElseThrow(ProductDoesNotExist::new);
    }

}
