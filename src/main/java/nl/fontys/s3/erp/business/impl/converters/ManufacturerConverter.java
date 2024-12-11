package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManufacturerConverter {
    private ManufacturerConverter() { }

    public static Manufacturer convert(ManufacturerEntity manufacturer) {
        return Manufacturer.builder()
                .id(manufacturer.getId())
                .companyName(manufacturer.getCompanyName())
                .city(manufacturer.getCity())
                .country(manufacturer.getCountry())
                .status(manufacturer.getStatus())
//                .products(Optional.ofNullable(manufacturer.getProducts())
//                        .map(products -> products.stream()
//                                .map(ProductConverter::convert)
//                                .collect(Collectors.toList()))
//                        .orElse(Collections.emptyList()))  // Fallback to an empty list
                .build();
    }
}
