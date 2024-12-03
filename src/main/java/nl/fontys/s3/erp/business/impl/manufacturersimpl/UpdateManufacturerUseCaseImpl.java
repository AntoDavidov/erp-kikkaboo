package nl.fontys.s3.erp.business.impl.manufacturersimpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.manufacturerusecases.UpdateManufacturerUseCase;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.dtos.manufacturerdto.UpdateManufacturerRequest;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateManufacturerUseCaseImpl implements UpdateManufacturerUseCase {
    private final ManufacturerRepository manufacturerRepository;
    private final AccessToken accessToken;

    @Override
    public void updateManufacturer(UpdateManufacturerRequest request) {
        if(accessToken.getDepartments() == null || !accessToken.getDepartments().contains("TRADE")) {
            throw new PermissionDenied("add a manufacturer.");
        }
        ManufacturerEntity manufacturerEntity = manufacturerRepository.findById(request.getManufacturerId())
                .orElseThrow(ManufacturerDoesNotExist::new);

        updateFields(request, manufacturerEntity);
        manufacturerRepository.save(manufacturerEntity);
    }

    private void updateFields(UpdateManufacturerRequest request, ManufacturerEntity manufacturerEntity) {
        manufacturerEntity.setCity(request.getCity());
        manufacturerEntity.setCountry(request.getCountry());
        manufacturerEntity.setCompanyName(request.getCompanyName());
    }
}
