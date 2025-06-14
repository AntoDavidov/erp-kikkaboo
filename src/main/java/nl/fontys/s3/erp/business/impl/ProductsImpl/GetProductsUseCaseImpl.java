package nl.fontys.s3.erp.business.impl.ProductsImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.GetAllProductsRequest;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.GetAllProductsResponse;
import nl.fontys.s3.erp.business.ProductsUseCases.GetProductsUseCase;
import nl.fontys.s3.erp.business.impl.converters.ProductConverter;
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

    @Override
    public GetAllProductsResponse getAllProducts(final GetAllProductsRequest request) {
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
