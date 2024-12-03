package nl.fontys.s3.erp.business.productsusecases;

import nl.fontys.s3.erp.domain.products.Product;

public interface GetProductUseCase {
   Product getProduct(long id);
}
