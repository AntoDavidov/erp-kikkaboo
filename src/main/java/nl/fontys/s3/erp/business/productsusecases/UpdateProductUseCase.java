package nl.fontys.s3.erp.business.productsusecases;

import nl.fontys.s3.erp.business.dtos.productdto.UpdateProductRequest;

public interface UpdateProductUseCase {
    void updateProduct(UpdateProductRequest request);
}
