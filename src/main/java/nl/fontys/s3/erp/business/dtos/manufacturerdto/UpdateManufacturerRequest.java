package nl.fontys.s3.erp.business.dtos.manufacturerdto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.s3.erp.domain.products.Country;
import nl.fontys.s3.erp.domain.users.Status;

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

    @NotNull
    private Status status;

}
