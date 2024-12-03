package nl.fontys.s3.erp.business.dtos.productdto;


import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;

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
    private BigDecimal costPrice;

    private BigDecimal recommendedRetailPrice;

    private BigDecimal wholeSalePrice;

    private String imageURL;

    @NotNull
    private BigDecimal weight;

    @NotNull
    private Long ManufacturerId;

}
