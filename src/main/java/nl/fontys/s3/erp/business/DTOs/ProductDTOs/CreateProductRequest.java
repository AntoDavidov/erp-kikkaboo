package nl.fontys.s3.erp.business.DTOs.ProductDTOs;


import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,  // Use the 'name' property to distinguish types
        include = JsonTypeInfo.As.PROPERTY,  // Look for a property in the JSON to indicate type
        property = "productType"  // The property in the JSON to look at
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateBabyStrollerRequest.class, name = "BABY_STROLLER")
        // Add other subclasses here when needed, e.g., @JsonSubTypes.Type(value = CreateCarSeatRequest.class, name = "CAR_SEAT")
})
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

    private double recommendedRetailPrice;

    private double wholeSalePrice;

    private String imageURL;

    @NotNull
    private int weight;

    @NotNull
    private Long ManufacturerId;

}
