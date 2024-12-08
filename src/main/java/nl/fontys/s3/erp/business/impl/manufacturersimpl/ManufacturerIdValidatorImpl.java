package nl.fontys.s3.erp.business.impl.manufacturersimpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.manufacturerusecases.ManufacturerIdValidator;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ManufacturerIdValidatorImpl implements ManufacturerIdValidator {
    private final ManufacturerRepository manufacturerRepository;

    @Override
    public void validateManufacturerId(Long manufacturerId) {
        if(manufacturerId == null || !manufacturerRepository.existsById(manufacturerId)) {
            throw new ManufacturerDoesNotExist();
        }

    }
}
