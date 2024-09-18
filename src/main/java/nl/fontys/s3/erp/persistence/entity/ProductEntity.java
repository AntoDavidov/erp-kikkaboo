package nl.fontys.s3.erp.persistence.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductEntity {
    private Long productId;

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
