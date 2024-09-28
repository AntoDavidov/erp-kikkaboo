package nl.fontys.s3.erp.business.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private double costPrice;

    private double recommendedRetailPrice;

    private double wholeSalePrice;

    private String imageURL;

    @NotNull
    private int weight;

    @NotNull
    private Long ManufacturerId;
}
