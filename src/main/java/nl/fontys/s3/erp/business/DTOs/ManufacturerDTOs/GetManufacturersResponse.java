package nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.s3.erp.domain.products.Manufacturer;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class GetManufacturersResponse {
    private List<Manufacturer> manufacturers;
}
