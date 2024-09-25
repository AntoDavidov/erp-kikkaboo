package nl.fontys.s3.erp.business.DTOs;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.products.Manufacturer;



@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    @NotNull
    private int sku;

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
    private int ManufacturerId;

}
