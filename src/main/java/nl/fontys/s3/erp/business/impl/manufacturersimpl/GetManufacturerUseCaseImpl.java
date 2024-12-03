package nl.fontys.s3.erp.business.impl.manufacturersimpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.manufacturerusecases.GetManufacturerUseCase;
import nl.fontys.s3.erp.business.exceptions.ManufacturerDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.converters.ManufacturerConverter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.products.Manufacturer;
import nl.fontys.s3.erp.persistence.ManufacturerRepository;
import nl.fontys.s3.erp.persistence.entity.ManufacturerEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetManufacturerUseCaseImpl implements GetManufacturerUseCase {
    private final AccessToken accessToken;
    private ManufacturerRepository manufacturerRepository;

    @Override
    public Manufacturer getManufacturer(long manufacturerId) {
        if(accessToken.getDepartments() == null || !accessToken.getDepartments().contains("TRADE")) {
            throw new PermissionDenied("add a manufacturer.");
        }
        ManufacturerEntity manufacturerEntity = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(ManufacturerDoesNotExist::new);

        return ManufacturerConverter.convert(manufacturerEntity);

    }

}
