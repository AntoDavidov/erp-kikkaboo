package nl.fontys.s3.erp.persistence.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.products.Country;

import java.util.List;


//@Entity
//@Table("manufacturers")

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class ManufacturerEntity {
    private Long id;

    private String companyName;

    private Country country;

    private String city;

    private List<ProductEntity> products;

//    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
//    private List<ProductEntity> products = new ArrayList<>();
}
