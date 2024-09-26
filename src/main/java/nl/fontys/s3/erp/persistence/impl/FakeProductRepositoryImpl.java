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

    public FakeProductRepositoryImpl() { this.savedProducts = new ArrayList<ProductEntity>(); }

    @Override
    public ProductEntity save(ProductEntity product) {
        if(product.getProductId() == null) {
            product.setProductId(NEXT_ID++);
            NEXT_ID++;
            this.savedProducts.add(product);
        } else {
            ProductEntity existingProduct = this.findById(product.getProductId());
            if(existingProduct != null) {
                existingProduct.setName(product.getName());
                existingProduct.setShortName(product.getShortName());
                existingProduct.setDescription(product.getDescription());
                existingProduct.setCostPrice(product.getCostPrice());
                existingProduct.setRecommendedRetailPrice(product.getRecommendedRetailPrice());
                existingProduct.setManufacturer(product.getManufacturer());
                existingProduct.setSku(product.getSku());
                existingProduct.setWholeSalePrice(product.getWholeSalePrice());
                existingProduct.setWeight(product.getWeight());
            }
        }
        return product;
    }

    @Override
    public List<ProductEntity> findAllByManufacturerName(String manufacturerName) {
        return this.savedProducts
                .stream()
                .filter(productEntity -> productEntity.getManufacturer().getCompanyName().equals(manufacturerName))
                .toList();
    }

    @Override
    public ProductEntity findById(long productId){
        return this.savedProducts.stream().filter(product -> product.getProductId() == productId).findFirst().orElse(null);
    }

    @Override
    public List<ProductEntity> findAll() {
        return this.savedProducts;
    }

    @Override
    public void deleteById(long productId){
        this.savedProducts.removeIf(p -> p.getProductId() == productId);
    }

    @Override
    public boolean existsById(long productId) {
        return this.savedProducts.stream().anyMatch(product -> product.getProductId() == productId);
    }

    @Override
    public boolean existsBySKU(String productSKU){
        return this.savedProducts.stream().anyMatch(p -> p.getSku() == productSKU);

    }

    @Override
    public int count() {
        return this.savedProducts.size();
    }

}
