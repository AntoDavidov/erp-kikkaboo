package nl.fontys.s3.erp.domain.products;


import lombok.*;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;

import java.math.BigDecimal;


@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Product {
    private Long productId;
    private String sku;
    private String name;
    private String shortName;
    private String description;
    private BigDecimal costPrice;
    private BigDecimal recommendedRetailPrice;//no need for them
    private BigDecimal wholeSalePrice;//no need for them
    private BigDecimal weight; //kg
    private Manufacturer manufacturer;
    private String imageUrl;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!productId.equals(product.productId)) return false;
        if (!sku.equals(product.sku)) return false;
        if (!name.equals(product.name)) return false;
        return manufacturer != null ? manufacturer.equals(product.manufacturer) : product.manufacturer == null;
    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + sku.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        return result;
    }


//    private String weightClassification; computed properties

//    public String getWeightClassification(){
//        if(this.weight >= 5){
//            return "HEAVY".toUpperCase();
//        } else{
//            return "LIGHT".toUpperCase();
//        }
//    }//no need to store it

//    public void classifyWeight() {
//        if(this.weight >= 5){
//            this.weightClassification = "HEAVY".toUpperCase();
//        } else {
//            this.weightClassification = "LIGHT".toUpperCase();
//
//        }
//    }

//    public void calculatePrices(){
//        if(this.wholeSalePrice == 0){
//            this.wholeSalePrice = this.costPrice * 1.10;
//        }
//
//
//        if(this.recommendedRetailPrice == 0){
//            double netRetailPrice = this.wholeSalePrice * 1.50;
//            this.recommendedRetailPrice = applyVATAndRound(netRetailPrice);
//        }
//    }
//
//    private double applyVATAndRound(double price) {
//        double priceWithVAT = price * 1.20;
//
//        return Math.floor(priceWithVAT) + 0.99;
//    }

}
