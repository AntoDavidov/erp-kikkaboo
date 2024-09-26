package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import nl.fontys.s3.erp.domain.products.Product;

public class ProductConverter {

    private ProductConverter() {
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
                .weight(productEntity.getWeight())
                .imageUrl(productEntity.getImageUrl())
                .manufacturer(ManufacturerConverter.convert(productEntity.getManufacturer()))
                .weightClassification(productEntity.getWeightClassification())
                .build();
    }
}
