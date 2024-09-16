package nl.fontys.s3.erp.domain.products;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private long productId;
    private long sku;
    private String name;
    private String shortName;
    private String description;
    private double costPrice;
    private double recommendedRetailPrice;
    private double wholeSalePrice;
    private Manufacturer manufacturer;

}
