package nl.fontys.s3.erp.business.productsusecases;

import nl.fontys.s3.erp.business.dtos.productdto.CreateProductRequest;
import nl.fontys.s3.erp.business.dtos.productdto.CreateProductResponse;

public interface CreateProductUseCase {
    CreateProductResponse createProduct(CreateProductRequest createProductRequest);

}
