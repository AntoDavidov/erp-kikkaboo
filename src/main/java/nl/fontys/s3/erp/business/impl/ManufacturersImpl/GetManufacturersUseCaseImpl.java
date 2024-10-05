package nl.fontys.s3.erp.business.impl.ManufacturersImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ManufacturerUseCases.GetManufacturersUseCase;
import nl.fontys.s3.erp.business.impl.converters.ManufacturerConverter;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.business.DTOs.ManufacturerDTOs.GetManufacturersResponse;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetManufacturersUseCaseImpl implements GetManufacturersUseCase {
    private final ManufacturerRepository manufacturerRepository;

    @Override
    public GetManufacturersResponse getManufacturers() {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll()
                .stream()
                .map(ManufacturerConverter::convert)
                .toList();

        return GetManufacturersResponse.builder().manufacturers(manufacturers).build();
    }

}
