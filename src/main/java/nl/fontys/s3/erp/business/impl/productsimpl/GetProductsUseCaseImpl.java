package nl.fontys.s3.erp.business.impl.productsimpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.dtos.productdto.GetAllProductsRequest;
import nl.fontys.s3.erp.business.dtos.productdto.GetAllProductsResponse;
import nl.fontys.s3.erp.business.productsusecases.GetProductsUseCase;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.converters.ProductConverter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.products.Product;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class GetProductsUseCaseImpl implements GetProductsUseCase{
    private final ProductRepository productRepository;
    private final AccessToken accessToken;

    @Override
    public GetAllProductsResponse getAllProducts(final GetAllProductsRequest request) {
        if(accessToken.getDepartments() == null || !accessToken.getDepartments().contains("PRODUCT")) {
            throw new PermissionDenied("get all products.");
        }
        List<ProductEntity> results;
        if(StringUtils.hasText(request.getCompanyName())){
            results = productRepository.findAllByManufacturerName(request.getCompanyName());
        }else {
            results = productRepository.findAll();
        }

        final GetAllProductsResponse response = new GetAllProductsResponse();
        List<Product> products = results
                .stream()
                .map(ProductConverter::convert)
                .toList();
        response.setProducts(products);

        return response;

    }
}
