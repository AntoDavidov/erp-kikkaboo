package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.ProductEntity;


public interface ProductRepository{

    ProductEntity save(ProductEntity product);


}
