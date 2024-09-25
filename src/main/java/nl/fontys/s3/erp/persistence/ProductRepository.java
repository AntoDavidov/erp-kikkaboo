package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.ProductEntity;

import java.util.List;


public interface ProductRepository{

    ProductEntity save(ProductEntity product);

    ProductEntity findById(long id);

    void deleteById(long id);

    boolean existsById(long id);

    boolean existsBySKU(int sku);

    List<ProductEntity> findAll();

    int count();




}
