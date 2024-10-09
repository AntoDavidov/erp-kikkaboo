package nl.fontys.s3.erp.business.ProductsUseCases;

import nl.fontys.s3.erp.business.DTOs.ProductDTOs.CreateProductRequest;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.CreateProductResponse;

public interface CreateProductUseCase {
    CreateProductResponse createProduct(CreateProductRequest createProductRequest);

}
