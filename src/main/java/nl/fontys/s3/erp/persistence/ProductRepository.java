package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.ProductEntity;

import java.util.List;
import java.util.Optional;


public interface ProductRepository{

    ProductEntity save(ProductEntity product);

    List<ProductEntity> findAllByManufacturerName(String manufacturerName);

    ProductEntity findById(long id);

    void deleteById(long id);

    boolean existsById(long id);

    boolean existsBySKU(String sku);

    List<ProductEntity> findAll();

    int count();




}
