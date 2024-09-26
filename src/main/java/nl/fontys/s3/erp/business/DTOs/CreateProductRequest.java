package nl.fontys.s3.erp.business.DTOs;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.products.Manufacturer;



@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

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

    @NotNull
    private double recommendedRetailPrice;

    @NotNull
    private double wholeSalePrice;

    @NotNull
    private int weight;

    @NotNull
    private Long ManufacturerId;

}
