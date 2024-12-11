package nl.fontys.s3.erp.business.impl.manufacturersimpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.manufacturerusecases.DeleteManufacturerUseCase;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.ProductRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import nl.fontys.s3.erp.persistence.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class DeleteManufacturerUseCaseImpl implements DeleteManufacturerUseCase {
    public final ManufacturerRepository manufacturerRepository;
    private final AccessToken accessToken;

    @Override
    public void deleteManufacturer(long manufacturerId) {
        if (accessToken.getDepartments() == null || !accessToken.getDepartments().contains("TRADE")) {
            throw new PermissionDenied("delete a manufacturer.");
        }
        if(!manufacturerRepository.existsById(manufacturerId)) {
            throw new ManufacturerDoesNotExist();
        }
        manufacturerRepository.deleteById(manufacturerId);
    }
}
