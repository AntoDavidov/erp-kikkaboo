package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.domain.products.BabyStrollers;
import nl.fontys.s3.erp.persistence.entity.BabyStrollersEntity;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import nl.fontys.s3.erp.domain.products.Product;

public class ProductConverter {

    private ProductConverter() {
    }

    public static Product convert(ProductEntity productEntity) {
        if(productEntity instanceof BabyStrollersEntity){
            BabyStrollersEntity babyStrollersEntity = (BabyStrollersEntity) productEntity;
            return BabyStrollers.builder()
                    .productId(babyStrollersEntity.getProductId())
                    .sku(babyStrollersEntity.getSku())
                    .name(babyStrollersEntity.getName())
                    .shortName(babyStrollersEntity.getShortName())
                    .description(babyStrollersEntity.getDescription())
                    .costPrice(babyStrollersEntity.getCostPrice())
                    .recommendedRetailPrice(babyStrollersEntity.getRecommendedRetailPrice())
                    .wholeSalePrice(babyStrollersEntity.getWholeSalePrice())
                    .manufacturer(ManufacturerConverter.convert(productEntity.getManufacturer()))
                    .weight(babyStrollersEntity.getWeight())
                    .imageUrl(babyStrollersEntity.getImageUrl())
                    .maxWeightCapacity(babyStrollersEntity.getMaxWeightCapacity())  // BabyStrollers specific
                    .ageLimit(babyStrollersEntity.getAgeLimit())  // BabyStrollers specific
                    .typeOfStroller(babyStrollersEntity.getTypeOfStroller())  // BabyStrollers specific
                    .foldable(babyStrollersEntity.isFoldable())  // BabyStrollers specific
//                    .weightClassification(babyStrollersEntity.getWeightClassification())
                    .build();

        }
        throw new IllegalArgumentException("Unknown product type");
    }
}
