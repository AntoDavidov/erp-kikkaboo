package nl.fontys.s3.erp.domain.products;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {

    private Long id;
    private String companyName;
    private Country country;
    private String city;
//    private List<Product> products;


}
