package nl.fontys.s3.erp.business.ProductsUseCases;

import nl.fontys.s3.erp.business.DTOs.ProductDTOs.GetAllProductsRequest;
import nl.fontys.s3.erp.business.DTOs.ProductDTOs.GetAllProductsResponse;

public interface GetProductsUseCase {
    GetAllProductsResponse getAllProducts(GetAllProductsRequest request);
}
