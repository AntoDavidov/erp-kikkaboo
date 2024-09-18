package nl.fontys.s3.erp.persistence.DTOs;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateManufacturerResponse {
    private Long manufacturerId;
}
