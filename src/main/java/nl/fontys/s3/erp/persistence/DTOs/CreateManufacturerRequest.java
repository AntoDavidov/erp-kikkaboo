package nl.fontys.s3.erp.persistence.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.s3.erp.domain.products.Country;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateManufacturerRequest {
    @NotNull
    private Country country;

    @NotBlank
    private String companyName;

    @NotBlank
    private String city;


}
