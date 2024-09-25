package nl.fontys.s3.erp.business.DTOs;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateManufacturerResponse {
    private Long manufacturerId;
}
