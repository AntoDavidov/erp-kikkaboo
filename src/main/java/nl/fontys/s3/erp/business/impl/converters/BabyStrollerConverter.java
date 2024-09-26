package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.domain.products.Product;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.domain.products.BabyStrollers;

public class BabyStrollerConverter {

    private BabyStrollerConverter() {

    }
    public static BabyStrollers convert(BabyStrollersEntity babyStrollersEntity) {
        Product product = ProductConverter.convert(babyStrollersEntity);

        return BabyStrollers.builder()
                .productId(product.getProductId())
                .sku(product.getSku())
                .name(product.getName())
                .shortName(product.getShortName())
                .description(product.getDescription())
                .costPrice(product.getCostPrice())
                .recommendedRetailPrice(product.getRecommendedRetailPrice())
                .wholeSalePrice(product.getWholeSalePrice())
                .weight(product.getWeight())
                .imageUrl(product.getImageUrl())
                .manufacturer(product.getManufacturer())
                .weightClassification(product.getWeightClassification())
                //specific fields for baby stroller
                .maxWeightCapacity(babyStrollersEntity.getMaxWeightCapacity())
                .ageLimit(babyStrollersEntity.getAgeLimit())
                .typeOfStroller(babyStrollersEntity.getTypeOfStroller())
                .foldable(babyStrollersEntity.isFoldable())
                .build();
    }
}
