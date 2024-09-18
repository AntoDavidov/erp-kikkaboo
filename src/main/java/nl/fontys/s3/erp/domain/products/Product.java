package nl.fontys.s3.erp.domain.products;


import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int productId;
    private int sku;
    private String name;
    private String shortName;
    private String description;
    private double costPrice;
    private double recommendedRetailPrice;
    private double wholeSalePrice;
    private int weight;
    private Manufacturer manufacturer;

}


//possible: if weight > over somthng its hardware...
