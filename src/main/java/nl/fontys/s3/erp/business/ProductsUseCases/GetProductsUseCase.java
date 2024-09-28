package nl.fontys.s3.erp.business.ProductsUseCases;

import nl.fontys.s3.erp.business.DTOs.GetAllProductsRequest;
import nl.fontys.s3.erp.business.DTOs.GetAllProductsResponse;

public interface GetProductsUseCase {
    GetAllProductsResponse getAllProducts(GetAllProductsRequest request);
}
