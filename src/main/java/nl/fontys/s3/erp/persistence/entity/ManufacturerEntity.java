package nl.fontys.s3.erp.persistence.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ManufacturerEntity {
    private Long id;

    private String country;

    private String city;
}
