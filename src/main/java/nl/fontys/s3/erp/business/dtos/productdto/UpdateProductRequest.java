package nl.fontys.s3.erp.business.dtos.productdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UpdateProductRequest {
    private long id;

    @Pattern(regexp = "\\d{8}", message = "The SKU must be exaclty 8 digits. Please try again!")
    private String sku;

    @NotBlank
    private String name;

    @NotBlank
    private String shortName;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal costPrice;

    private BigDecimal recommendedRetailPrice;

    private BigDecimal wholeSalePrice;

    private String imageURL;

    @NotNull
    private BigDecimal weight;

    @NotNull
    private Long ManufacturerId;
}
