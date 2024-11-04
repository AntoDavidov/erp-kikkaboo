package nl.fontys.s3.erp.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.products.Product;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Entity
@Table(name = "product")
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @NotBlank
    @Length(max = 8)
    @Column(name = "sku")
    private String sku;

    @NotBlank
    @Length(min = 5, max = 80)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Length(min = 2, max = 20)
    @Column(name = "short_name")
    private String shortName;

    @NotBlank
    @Column(name = "description")
    private String description;

    @Column(name = "cost_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal costPrice;

    @Column(name = "recommended_retail_price", precision = 10, scale = 2)
    private BigDecimal recommendedRetailPrice;

    @Column(name = "wholesale_price", precision = 10, scale = 2)
    private BigDecimal wholeSalePrice;

    @Column(name = "weight", precision = 10, scale = 2)
    private BigDecimal weight;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = true)
    private ManufacturerEntity manufacturer;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Transient
    private String weightClassification;

    public void classifyWeight() {
        this.weightClassification = this.weight.compareTo(BigDecimal.valueOf(5)) >= 0 ? "HEAVY" : "LIGHT";
    }


    public void calculatePrices() {
        if (this.wholeSalePrice == null) {
            this.wholeSalePrice = this.costPrice.multiply(BigDecimal.valueOf(1.10));
        }
        if (this.recommendedRetailPrice == null) {
            BigDecimal netRetailPrice = this.wholeSalePrice.multiply(BigDecimal.valueOf(1.50));
            this.recommendedRetailPrice = applyVATAndRound(netRetailPrice);
        }
    }

    private BigDecimal applyVATAndRound(BigDecimal price) {
        BigDecimal priceWithVAT = price.multiply(BigDecimal.valueOf(1.20));
        return priceWithVAT.setScale(0, RoundingMode.FLOOR).add(BigDecimal.valueOf(0.99));
    }


}
