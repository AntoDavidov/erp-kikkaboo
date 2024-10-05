package nl.fontys.s3.erp.business.ProductsUseCases;

import nl.fontys.s3.erp.business.DTOs.ProductDTOs.UpdateProductRequest;

public interface UpdateProductUseCase {
    void updateProduct(UpdateProductRequest request);
}
