package nl.fontys.s3.erp.domain.products;


import lombok.*;
import lombok.experimental.SuperBuilder;

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
}
