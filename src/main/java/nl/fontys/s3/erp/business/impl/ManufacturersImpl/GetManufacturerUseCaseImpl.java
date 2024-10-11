package nl.fontys.s3.erp.business.impl.ManufacturersImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ManufacturerUseCases.GetManufacturerUseCase;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.impl.converters.ManufacturerConverter;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetManufacturerUseCaseImpl implements GetManufacturerUseCase {

    private ManufacturerRepository manufacturerRepository;

    @Override
    public Manufacturer getManufacturer(long manufacturerId) {
        ManufacturerEntity manufacturerEntity = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(ManufacturerDoesNotExist::new);

        return ManufacturerConverter.convert(manufacturerEntity);

    }

}
