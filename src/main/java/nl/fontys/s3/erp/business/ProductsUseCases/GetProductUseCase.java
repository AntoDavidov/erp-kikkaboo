package nl.fontys.s3.erp.business.ProductsUseCases;

import nl.fontys.s3.erp.domain.products.Product;

import java.util.Optional;

public interface GetProductUseCase {
   Product getProduct(long id);
}
