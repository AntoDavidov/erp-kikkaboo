package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import nl.fontys.s3.erp.domain.products.Product;

public class ProductConverter {
    public ProductConverter(){


    }
    public static Product convert(ProductEntity productEntity) {
        return Product.builder()
                .productId(productEntity.getProductId())
                .sku(productEntity.getSku())
                .name(productEntity.getName())
                .shortName(productEntity.getShortName())
                .description(productEntity.getDescription())
                .costPrice(productEntity.getCostPrice())
                .recommendedRetailPrice(productEntity.getRecommendedRetailPrice())
                .wholeSalePrice(productEntity.getWholeSalePrice())
                .manufacturer(productEntity.getManufacturer())  // Or convert if needed
                .build();
    }

}
