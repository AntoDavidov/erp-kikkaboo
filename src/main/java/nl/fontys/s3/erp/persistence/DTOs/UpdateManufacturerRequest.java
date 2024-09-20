package nl.fontys.s3.erp.persistence.DTOs;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.s3.erp.domain.products.Country;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateManufacturerRequest {
    private long manufacturerId;

    @NotBlank
    private String companyName;

    @NotNull
    private Country country;

    @NotBlank
    private String city;


}
