package nl.fontys.s3.erp.business.impl.ManufacturersImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ManufacturerUseCases.DeleteManufacturerUseCase;
import nl.fontys.s3.erp.business.exceptions.ManufacturerAlreadyExists;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class DeleteManufacturerUseCaseImpl implements DeleteManufacturerUseCase {
    public final ManufacturerRepository manufacturerRepository;

    @Override
    public void deleteManufacturer(long manufacturerId) {
        if(!manufacturerRepository.existsById(manufacturerId)) {
            throw new ManufacturerDoesNotExist();

        }
        manufacturerRepository.deleteById(manufacturerId);


    }



}
