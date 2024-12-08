package nl.fontys.s3.erp.business.productsusecases;

import nl.fontys.s3.erp.business.dtos.productdto.GetAllProductsRequest;
import nl.fontys.s3.erp.business.dtos.productdto.GetAllProductsResponse;

public interface GetProductsUseCase {
    GetAllProductsResponse getAllProducts(GetAllProductsRequest request);
}
