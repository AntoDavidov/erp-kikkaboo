package nl.fontys.s3.erp.business.dtos.manufacturerdto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateManufacturerResponse {
    private Long manufacturerId;
}
