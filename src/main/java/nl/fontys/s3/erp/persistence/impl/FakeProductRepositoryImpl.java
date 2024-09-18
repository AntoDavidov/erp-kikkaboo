package nl.fontys.s3.erp.persistence.impl;


import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FakeProductRepositoryImpl implements ProductRepository {

    private static long NEXT_ID = 1;
    private final List<ProductEntity> savedProducts;

    public FakeProductRepositoryImpl() {
        this.savedProducts = new ArrayList<ProductEntity>();
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        if(product.getProductId() == null) {
            product.setProductId(NEXT_ID++);
            NEXT_ID++;
            this.savedProducts.add(product);
        }
        return product;

    }


}
