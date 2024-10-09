package nl.fontys.s3.erp.domain.products;


import lombok.*;
import lombok.experimental.SuperBuilder;


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
    private double costPrice;
    private double recommendedRetailPrice;//no need for them
    private double wholeSalePrice;//no need for them
    private double weight; //kg
    private Manufacturer manufacturer;
    private String imageUrl;
//    private String weightClassification; computed properties

    public String getWeightClassification(){
        if(this.weight >= 5){
            return "HEAVY".toUpperCase();
        } else{
            return "LIGHT".toUpperCase();
        }
    }//no need to store it

//    public void classifyWeight() {
//        if(this.weight >= 5){
//            this.weightClassification = "HEAVY".toUpperCase();
//        } else {
//            this.weightClassification = "LIGHT".toUpperCase();
//
//        }
//    }

    public void calculatePrices(){
        if(this.wholeSalePrice == 0){
            this.wholeSalePrice = this.costPrice * 1.10;
        }


        if(this.recommendedRetailPrice == 0){
            double netRetailPrice = this.wholeSalePrice * 1.50;
            this.recommendedRetailPrice = applyVATAndRound(netRetailPrice);
        }
    }

    private double applyVATAndRound(double price) {
        double priceWithVAT = price * 1.20;

        return Math.floor(priceWithVAT) + 0.99;
    }

}
