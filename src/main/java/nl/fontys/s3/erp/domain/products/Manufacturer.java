package nl.fontys.s3.erp.domain.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {

    private long id;
    private String country;
    private String city;
    private String address;
}
