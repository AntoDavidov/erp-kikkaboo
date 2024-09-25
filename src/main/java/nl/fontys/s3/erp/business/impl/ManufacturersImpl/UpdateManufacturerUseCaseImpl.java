package nl.fontys.s3.erp.business.impl.ManufacturersImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.ManufacturerUseCases.UpdateManufacturerUseCase;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.DTOs.UpdateManufacturerRequest;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateManufacturerUseCaseImpl implements UpdateManufacturerUseCase {
    private final ManufacturerRepository manufacturerRepository;

    @Override
    public void updateManufacturer(UpdateManufacturerRequest request) {
        Optional<ManufacturerEntity> manufacturerEntityOptional = Optional.ofNullable(manufacturerRepository.findById(request.getManufacturerId()));
        if(manufacturerEntityOptional.isEmpty()) {
            throw new ManufacturerDoesNotExist();

        }
        ManufacturerEntity manufacturerEntity = manufacturerEntityOptional.get();

        updateFields(request, manufacturerEntity);
        manufacturerRepository.save(manufacturerEntity);


    }

    private void updateFields(UpdateManufacturerRequest request, ManufacturerEntity manufacturerEntity) {
        manufacturerEntity.setCity(request.getCity());
        manufacturerEntity.setCountry(request.getCountry());
        manufacturerEntity.setCompanyName(request.getCompanyName());

    }

}
